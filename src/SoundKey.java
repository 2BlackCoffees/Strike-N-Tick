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
import java.net.URL;
import java.util.List;

/**
 * This class is designed to group all data together: URL to the wav file, the
 * list of keys associated to the wav file. The name that will be displayed as a
 * menu item for the group of keys. The filename to find out which wav file is
 * currently selected when displaying the radio buttons.
 * 
 * @author Nino
 * 
 */
class SoundKey {
	private URL url;
	private final List<Integer> listKeys;
	private final String name;
	private String fileName;
	private static String RESOURCE_PATH = null;

	static void setResourcePath(String resourcePath) {
		RESOURCE_PATH = resourcePath;
	}

	SoundKey(String fileName, List<Integer> listKeys, String name) {
		if (RESOURCE_PATH == null) {
			throw new NullPointerException("RESOURCE_PATH is null!");
		}
		setFilename(fileName);
		this.listKeys = listKeys;
		this.name = name;
	}

	String getFileName() {
		return fileName;
	}

	List<Integer> getListKeys() {
		return listKeys;
	}

	String getName() {
		return name;
	}

	final URL getURL() {
		return url;
	}

	void setFilename(String fileName) {
		this.url = getClass().getClassLoader().getResource(
				RESOURCE_PATH + fileName);
		this.fileName = fileName;
	}

}
