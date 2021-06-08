import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A class for managing the location of files and folders.
 * @author Josiah Richards
 * @version 1.1
 */
public class FileManager {
	
	//File extensions
	private static final String MAP_EXTENSION = "map";
	private static final String DATA_EXTENSION = "dat";
	private static final String UI_EXTENSION = "fxml";
	
	//Preset file names
	private static final String MAP_LAYOUT_FILE_NAME = "layout." + MAP_EXTENSION;
	private static final String INFO_FILE_NAME = "info." + DATA_EXTENSION;
	private static final String RELATIVE_SAVES_PATH = "/saves";
	
	//The paths relative to the application path.
	private static final String RELATIVE_DATA_PATH = "/data";
	private static final String RELATIVE_PROFILES_PATH = RELATIVE_DATA_PATH + "/profiles";
	private static final String RELATIVE_RESOURCES_PATH = RELATIVE_DATA_PATH + "/resources";
	private static final String RELATIVE_MAPS_PATH = RELATIVE_RESOURCES_PATH + "/maps";
	private static final String RELATIVE_IMAGES_PATH = RELATIVE_RESOURCES_PATH + "/images";
	private static final String RELATIVE_SOUNDS_PATH = RELATIVE_RESOURCES_PATH + "/sounds";
	private static final String RELATIVE_UI_PATH = RELATIVE_RESOURCES_PATH + "/UI";
	
	//The current full root path of this application.
	private static String appPath;
	//The current full paths of different folders.
	private static String dataPath;
	private static String profilesPath;
	private static String mapsPath;
	private static String imagesPath;
	private static String soundsPath;
	private static String UIPath; //exception to camel case rule.
	
	//Not sure what the rules are regarding static constructors
	//but heres one that sets up the folders.
	static {
		initialisePaths();
	}
	
	/*
	 * Gets the current path of this application and updates our
	 * other path variables to match, creating any missing directories.
	 */
	public static void initialisePaths() {
		appPath = new File("").getAbsolutePath();
		
		dataPath = appPath + RELATIVE_DATA_PATH;
		createDirIfNeeded(dataPath, true);
		
		profilesPath = appPath + RELATIVE_PROFILES_PATH;
		createDirIfNeeded(profilesPath, true);
		
		mapsPath = appPath + RELATIVE_MAPS_PATH;
		createDirIfNeeded(mapsPath, true);

		
		imagesPath = appPath + RELATIVE_IMAGES_PATH;
		createDirIfNeeded(imagesPath, true);
		
		soundsPath = appPath + RELATIVE_SOUNDS_PATH;
		createDirIfNeeded(soundsPath, true);
		
		UIPath = appPath + RELATIVE_UI_PATH;
		createDirIfNeeded(UIPath, true);	
	}
	
	
	/**
	 * Converts a file path into a URL.
	 * (javafx requires resources to be in a URL format)
	 * @param filePath The string full file path to convert to a URL.
	 * @return The file path as a URL.
	 * @throws MalformedURLException If the file path couldn't be converted to a URL.
	 */
	public static URL filePathToURL(String filePath) throws MalformedURLException{
		return new File(filePath).toURI().toURL();
	}
	
	/**
	 * Gets the path of an image file.
	 * @param imageFileNameWithExt The image file name with its extension.
	 * @return The full path to the image file file.
	 */
	public static String getImagePath(String imageFileNameWithExt) {
		return imagesPath + "/" + imageFileNameWithExt;
	}
	
	/**
	 * Gets the path of an image file.
	 * @param soundFileNameWithExt The sound file name with its extension.
	 * @return The full path to the image file file.
	 */
	public static String getSoundPath(String soundFileNameWithExt) {
		return soundsPath + "/" + soundFileNameWithExt;
	}
	
	
	/**
	 * Gets the path of a UI file.
	 * @param UIFileName The UI file name (without its extension).
	 * @return The full path to the UI file.
	 */
	public static String getUIPath(String UIFileName) {
		return UIPath + "/" + UIFileName + "." + UI_EXTENSION;
	}
	
	/**
	 * Gets the path to the map folder of a given map ID.
	 * @param mapFileName The id of the map.
	 * @return The full path to the map folder.
	 */
	public static String getMapFolderPath(int mapID) {
		return mapsPath + "/" + mapID;
	}
	
	/**
	 * Gets the path to the profile folder of a given profile ID.
	 * @param profileID The ID of the profile.
	 * @return The full path to the profile folder.
	 */
	public static String getProfileFolderPath(int profileID) {
		return profilesPath + "/" + profileID;
	}
	
	/**
	 * Gets the path to the profile folder of a given profile ID.
	 * @param profileID The ID of the profile.
	 * @return The full path to the profile folder.
	 */
	public static File[] getProfileFolders() {
		File profilesFolder = new File(profilesPath);
		return profilesFolder.listFiles();
	}
	
	/**
	 * Deletes a folder and all of it's sub directories.
	 * @param folder The folder to delete.
	 */
	public static void deleteFolder(File folder) {
		//If it's a directory, delete everything inside
		//.delete() doesn't work on non empty folders.
	    if (folder.isDirectory()) {
	        for (File subFile : folder.listFiles()) {
	        	deleteFolder(subFile);
	        }
	    }
	    folder.delete();
	}
	
	/**
	 * Gets the next available profile ID.
	 * @return The next available profile ID.
	 */
	public static int getNextAvailableProfileID() {
		int curProfileID = 1;
		//Keep incrementing curProfileID until the profile folder already doesn't exist.
		while (fileExists(getProfileFolderPath(curProfileID))) {
			curProfileID++;
		}
		return curProfileID;
	}
	
	/**
	 * Gets the path to the layout file of a map.
	 * @param mapID The map ID to get the layout file of.
	 * @return The full path to the map's layout file.
	 */
	public static String getMapLayoutFile(int mapID) {
		String mapFolder = getMapFolderPath(mapID);
		return mapFolder + "/" + MAP_LAYOUT_FILE_NAME;
	}
	
	/**
	 * Gets the path to the layout file of a map.
	 * @param mapID The map ID to get the layout file of.
	 * @return The full path to the map's layout file.
	 */
	public static String getMapInfoFile(int mapID) {
		String mapFolder = getMapFolderPath(mapID);
		return mapFolder + "/" + INFO_FILE_NAME;
	}
	
	/**
	 * Gets the path to the layout file of a map.
	 * @param mapID The map ID to get the layout file of.
	 * @return The full path to the map's layout file.
	 */
	public static String getProfileInfoFile(int profileID) {
		String profileFolder = getProfileFolderPath(profileID);
		return profileFolder + "/" + INFO_FILE_NAME;
	}
	
	/**
	 * Gets the path to the saves folder of a profile.
	 * @param mapID The profile ID to get the saves folder of.
	 * @return The full path to the profile's saves folder.
	 */
	public static String getProfileSavesFolder(int profileID) {
		String profileFolder = getProfileFolderPath(profileID);
		return profileFolder + RELATIVE_SAVES_PATH;
	}
	
	/**
	 * Gets the path to the saves folder of a profile.
	 * @param mapID The profile ID to get the saves folder of.
	 * @return The full path to the profile's saves folder.
	 */
	public static String getSavedMapFile(int profileID, int mapID) {
		String savesFolder = getProfileSavesFolder(profileID);
		return savesFolder + "/" + mapID + "." + MAP_EXTENSION;
	}
	
	/**
	 * Creates a directory if it doesn't already exist.
	 * @param dirPath The path of directory you want to create.
	 * @param createSubDirs Create all missing sub directories in the path or just the final folder.
	 */
	public static void createDirIfNeeded(String dirPath, boolean createSubDirs) {
		File dirFile = new File(dirPath);
		if (createSubDirs) {
			dirFile.mkdirs();
		} else {
			dirFile.mkdir();
		}
	}
	
	/**
	 * Checks if a file/folder exists
	 * @param filePath The full file/folder path to check.
	 * @return True if the file/folder does exist, false otherwise
	 */
	public static boolean fileExists(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
}
