/*
 *  This file is part of Strick'N'Tick.
 *
 *  Strick'N'Tick is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Strick'N'Tick is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with EasyTimeTracker.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Strick'N'Tick: First creation by 2BlackCoffees: http://www.twoblackcoffees.com/
 *
 **/
import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

/**
 * Create Icon tray and associate a menu to it.
 * 
 * @author Nino
 * 
 */
class TrayIconManager {
	private static String RESOURCE_PATH = null;
	private TrayIcon trayIcon = null;
	private final List<SoundKey> listSoundKeys;
	private final TrayToStrike trayToStrike;
	private JCheckBoxMenuItem muteItem;
	private List<String> listSoundFiles = new ArrayList<String>();

	static void setResourcePath(String resourcePath) {
		RESOURCE_PATH = resourcePath;
	}

	TrayIconManager(List<SoundKey> listSoundKeys, TrayToStrike trayToStrike) {
		/*
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL urlImage = getClass().getResource(RESOURCE_PATH + "MusicGrey.png");
		Image img = tk.createImage(urlImage);
		tk.prepareImage(img, -1, -1, null);
		 */
		if (RESOURCE_PATH == null) {
			throw new NullPointerException("RESOURCE_PATH is null!");
		}
		this.listSoundKeys = listSoundKeys;
		this.trayToStrike = trayToStrike;
		try {
			trayIcon();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void setMuteEnabled(boolean enabled) {
		muteItem.setEnabled(enabled);
	}

	/**
	 * When exporting to an executable jar file pay attention to use the option                   
	 * 'Extract required libraries into generated JAR' to have a jar protocol access.             
	 * If the option 'Package required libraries into generated JAR' is selected instead, Eclipse 
	 * will generate its own protocol rsrc preventing the use of the default jar protocol and     
	 * thus preventing this function to work correctly                                            
	 * 
	 * @param clazz
	 * @param path
	 * @param extension
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	List<String> getResourceListing(Class<?> clazz, String path, String extension) throws URISyntaxException, IOException {
		URL dirURL = clazz.getClassLoader().getResource(path);
		String[] arrayFileNames = null;

		if (dirURL != null && dirURL.getProtocol().equals("file")) {
			// A file path: easy enough 
			arrayFileNames = new File(dirURL.toURI()).list();
		} else {

			if (dirURL == null) {
				// In case of a jar file, we can't actually find a directory.
				// Have to assume the same jar as clazz.
				String me = clazz.getName().replace(".", "/")+".class";
				dirURL = clazz.getClassLoader().getResource(me);
			}

			// Make sure we are running the right protocol 
			if (dirURL.getProtocol().equals("jar")) {
				// A JAR path
				String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
				JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
				// Gives ALL entries in jar
				Enumeration<JarEntry> entries = jar.entries(); 
				// Avoid duplicates in case it is a subdirectory
				Set<String> result = new HashSet<String>(); 
				while(entries.hasMoreElements()) {
					String name = entries.nextElement().getName();
					if (name.startsWith(path)) { //filter according to the path
						String entry = name.substring(path.length());
						int checkSubdir = entry.indexOf("/");
						if (checkSubdir >= 0) {
							// if it is a subdirectory, we just return the directory name
							entry = entry.substring(0, checkSubdir);
						}
						result.add(entry);
					}
				}
				jar.close();
				arrayFileNames = result.toArray(new String[result.size()]);
			} else {
				// Nothing found, throw an exception
				throw new UnsupportedOperationException("Cannot list files for URL " + dirURL + " for protocol " + dirURL.getProtocol());

			}
		}

		// Sort out all filenames whose extension is not matching
		if(arrayFileNames != null) {
			if(extension == null) return Arrays.asList(arrayFileNames);
			List<String> listFileNames = new ArrayList<String>();
			for(String fileName: arrayFileNames) {
				if(fileName.endsWith(extension)) listFileNames.add(fileName);
			}
			return listFileNames;
		}
		// Nothing found
		return null;

	}
	/**
	 * Create the tray icon and associate the menu to it
	 * 
	 * @throws IOException
	 */
	void trayIcon() throws IOException {
		if (SystemTray.isSupported()) {
			try {
				listSoundFiles = getResourceListing(TrayIconManager.class, RESOURCE_PATH, "wav");
			} catch (URISyntaxException e2) {
				e2.printStackTrace();
			}

			ActionListener exitListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					trayToStrike.exitRequired();
				}
			};
			ItemListener muteListener = new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					trayToStrike.toggleMute();
				}
			};
			ActionListener aboutListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] options = {"OK"};
					JOptionPane.showOptionDialog(null,
							"This program is open source software.\n" +
									"Its only purpose is to emit beeps when keyboard is stroke.\n" +
									"Find more information here http://twoblackcoffees.com/category/own-programs/strikentick.",
									"About Strik'N'Tick",
									JOptionPane.PLAIN_MESSAGE,
									JOptionPane.PLAIN_MESSAGE,
									null,
									options,
									options[0]);				
				}
			};

			final JPopupMenu popupMenu = new JPopupMenu("StricknTick");
			JLabel label = new JLabel(
					"<html><b>Strike</b>'N'<b>Tick</b></html>");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			popupMenu.add(label);
			popupMenu.addSeparator();
			for (JMenu menu : createMenuList()) {
				popupMenu.add(menu);
			}
			popupMenu.addSeparator();

			muteItem = new JCheckBoxMenuItem("Mute");
			muteItem.addItemListener(muteListener);
			popupMenu.add(muteItem);
			popupMenu.addSeparator();

			JMenuItem aboutItem = new JMenuItem("About");
			aboutItem.addActionListener(aboutListener);
			popupMenu.add(aboutItem);
			popupMenu.add(new JMenuItem("Close menu"));
			JMenuItem exitItem = new JMenuItem("Exit");
			exitItem.addActionListener(exitListener);
			popupMenu.add(exitItem);

			Image image = ImageIO.read(getClass().getClassLoader().getResource(
					RESOURCE_PATH + "MusicGrey.png"));
			trayIcon = new TrayIcon(image, "Strike'n'Tick");
			trayIcon.addActionListener(exitListener);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger()) {
						popupMenu.setLocation(e.getX(), e.getY());
						popupMenu.setInvoker(popupMenu);
						popupMenu.setVisible(true);
					}
				}
			});

			SystemTray tray = SystemTray.getSystemTray();
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
		}
	}

	/**
	 * Create the menu list: based on the list of all possible group of keys
	 * create a menu for each group and associate as sub menu all filenames that
	 * could be selected. Select the currently selected filename.
	 * 
	 * @return
	 */
	private List<JMenu> createMenuList() {
		List<JMenu> listMenu = new ArrayList<JMenu>();
		for (final SoundKey soundKey : listSoundKeys) {
			JMenu menu = new JMenu(soundKey.getName());
			listMenu.add(menu);
			ButtonGroup buttonGroup = new ButtonGroup();
			for (final String fileName : listSoundFiles) {
				JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem(
						fileName, fileName.equals(soundKey.getFileName()));
				ItemListener soundFileListener = new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						soundKey.setFilename(fileName);
					}
				};
				radioButtonMenuItem.addItemListener(soundFileListener);
				menu.add(radioButtonMenuItem);
				buttonGroup.add(radioButtonMenuItem);

			}
		}
		return listMenu;
	}

}
