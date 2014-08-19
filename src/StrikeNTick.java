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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.alee.laf.WebLookAndFeel;

/**
 * This interface serves as communication from the class {@link TrayIconManager}
 * and the class {@link StrikeNTick}
 */
interface TrayToStrike {
	/**
	 * The user requires to exit the program
	 */
	void exitRequired();

	/**
	 * The user requires to toggle mute
	 */
	void toggleMute();
}

/**
 * This class is designed to be able to generate sounds on any pressed key. Key
 * families can be associated with various different sounds. All sounds must be
 * in wav format located in the resource directory.
 * 
 * Family of keys are defined by their code and stored in the interface
 * {@link KeyFamilies}
 * 
 * @author Nino
 * 
 */
public class StrikeNTick implements NativeKeyListener, TrayToStrike {
	private final TrayIconManager trayIconManager;

	private static final String RESOURCE_PATH = "resources/";

	private static final long TIMER_DELAY = 10000;
	private Clip clip;
	private boolean mute = false;
	private final List<Clip> clipToClose = Collections
			.synchronizedList(new ArrayList<Clip>());
	private TimerTask cleanTimerTask;
	private Timer cleanTimer;

	private static StrikeNTick keyListener;//= new StrikeNTick();
	private final List<SoundKey> listSoundKeys;

	StrikeNTick() {
		SoundKey.setResourcePath(RESOURCE_PATH);
		TrayIconManager.setResourcePath(RESOURCE_PATH);
		listSoundKeys = new ArrayList<SoundKey>(Arrays.asList(new SoundKey(
				"TickPhone1.wav", KeyFamilies.functionKeys, "Function keys"),
				new SoundKey("Tick1.wav", KeyFamilies.controlKeys,
						"Control keys"), new SoundKey("TickPhone3.wav",
								KeyFamilies.signKeys, "Sign keys"), new SoundKey(
										"TickMetal.wav", KeyFamilies.alphaKeys, "Alpha keys"),
										new SoundKey("TickPhone4.wav", KeyFamilies.numericalKeys,
												"Numerical keys"), new SoundKey("TickPhone5.wav",
														null, "Other keys")));
		trayIconManager = new TrayIconManager(listSoundKeys, this);

	}

	/**
	 * When a key is pressed, if we are not in mute mode, first check whether
	 * memory should be triggered to be cleaned.
	 * 
	 * Then search for the right sound fo the pressed key and play it.
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent event) {

		if (!mute) {
			triggerCleanMemory();
			AudioInputStream stream = null;
			AudioFormat format = null;

			try {
				// By default use the last element of the array {@link
				// listSoundKeys}
				SoundKey selectedSoundKey = listSoundKeys.get(listSoundKeys
						.size() - 1);
				for (SoundKey soundKey : listSoundKeys) {
					if (soundKey.getListKeys().contains(event.getKeyCode())) {
						selectedSoundKey = soundKey;
						break;
					}
				}

				stream = AudioSystem.getAudioInputStream(selectedSoundKey
						.getURL());
				clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,
						format));
				clip.open(stream);
			} catch (LineUnavailableException | IOException
					| UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}

			clip.start();
			try {
				stream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			clipToClose.add(clip);

		}
	}

	/**
	 * This is a kind of watch dog: As long as it is called (which means a key
	 * is pressed) in less than TIMER_DELAY ms, nothing happens If no key is
	 * pressed, after TIMER_DELAY ms all wave files read will be deleted from
	 * the memory.
	 */
	private void triggerCleanMemory() {
		try {
			if (cleanTimer != null) {
				cleanTimer.cancel();
			}
			cleanTimer = new Timer(true);
			cleanTimerTask = new CleanTimerTask();
			cleanTimer.schedule(cleanTimerTask, TIMER_DELAY);
		} catch (IllegalStateException e) {
			System.out.println("Cancelled timer already cancelled!");
		}
	}

	@Override
	public void toggleMute() {
		mute = true ^ mute;
	}

	@Override
	public void exitRequired() {
		GlobalScreen.getInstance().removeNativeKeyListener(keyListener);
		System.exit(0);
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	public static void main(String[] args) {

		if (args.length > 0 && args[0].equalsIgnoreCase("-service")) {
			String currentFile = new java.io.File(StrikeNTick.class
					.getProtectionDomain().getCodeSource().getLocation()
					.getPath()).getName();
			try {
				System.out.println("Starting service " + currentFile);
				Runtime.getRuntime().exec("java -jar " + currentFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err
			.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		// You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater ( new Runnable ()
		{
			public void run ()
			{
				// Install WebLaF as application L&F
				WebLookAndFeel.install ();

				keyListener = new StrikeNTick();
				GlobalScreen.getInstance().addNativeKeyListener(keyListener);
			}
		} );
	}

	/**
	 * Inner class designed to clean all clips still resident in memory
	 * 
	 * @author Nino
	 * 
	 */
	class CleanTimerTask extends TimerTask {
		List<Clip> clipToCloseTmp;

		@Override
		public void run() {
			// Make sure we are muted and the user cannot change the status
			// through the menu. This ensures no new clip will be started while
			// deleting old ones. The drawback is that nothing plays in
			// the meantime
			trayIconManager.setMuteEnabled(false);
			boolean prevMute = mute;
			mute = true;

			clipToCloseTmp = new ArrayList<Clip>(clipToClose);
			clipToClose.clear();
			for (Clip clip : clipToCloseTmp) {
				clip.stop();
				clip.close();
			}
			clipToCloseTmp.clear();
			System.gc();

			mute = prevMute;
			trayIconManager.setMuteEnabled(true);

		}
	}

}
