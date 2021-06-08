import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Manages the reading and writing of profiles.
 * @author Josiah Richards
 * @version 1.1
 */
public class ProfileManager {
	
	//Error message
	private static final String PROFILE_READ_ERROR_MSG = "Unable to load profile info file.";
	
	/**
	 * Loads all the profiles as a list of Profiles.
	 * @return A list of all the profiles.
	 * @throws ParsingException If there was an error parsing one of the profiles' information.
	 */
	public static ArrayList<Profile> loadAllProfiles() throws ParsingException {
		ArrayList<Profile> loadedProfiles = new ArrayList<Profile>();
		
		//Loop through all the folders in the profiles folder.
		File[] profileFolders = FileManager.getProfileFolders();
		for (int i = 0; i < profileFolders.length; i++) {
			//Try loading the profile information file(info.dat) in this folder.
			try {
				Profile loadedProfile = loadProfile(Integer.parseInt(profileFolders[i].getName()));
				//Add the successfully parsed profile to the list.
				loadedProfiles.add(loadedProfile);
			} catch (Exception e) {
				//Failed to parse the profile.
				throw new ParsingException(PROFILE_READ_ERROR_MSG);
			}
		}
		
		return loadedProfiles;
	}
	
	/**
	 * Loads a profile of a given profile ID.
	 * @param profileID The ID of the profile to load.
	 * @return A Profile object containing the data read.
	 * @throws ParsingException If the profile information file couldn't be parsed.
	 */
	public static Profile loadProfile(int profileID) throws ParsingException {
		
		try {
			String profileInfoPath = FileManager.getProfileInfoFile(profileID);
			File file = new File (profileInfoPath);
			Scanner in = new Scanner(file); 
	
			
			String readName = in.nextLine();
			int readHighestLevel = Integer.parseInt(in.nextLine());
			String readImage = in.nextLine();
			in.close();
			
			Profile readProfile = new Profile(profileID);
			readProfile.setName(readName);
			readProfile.setHighestLevel(readHighestLevel);
			readProfile.setImage(readImage);
			
			return readProfile;
		} catch (Exception e) {
			throw new ParsingException(PROFILE_READ_ERROR_MSG);
		}
	}
	
	/**
	 * Saves a profile object to file.
	 * @param profile The profile to save.
	 * @throws IOException If somehow the profileFile path we generate is a folder.
	 */
	public static void saveProfile(Profile profile) throws IOException {
		//Get the folder for the ID of this profile
		String profileInfoPath = FileManager.getProfileInfoFile(profile.getID());
		//Get the saves folder
		String profileSavesFolder = FileManager.getProfileSavesFolder(profile.getID());
		//Create the profile folder and saves folder if needed
		FileManager.createDirIfNeeded(profileSavesFolder, true);
		
		File profileFile = new File(profileInfoPath);
		//Overwrite existing profile file.
		if (profileFile.exists()) {
			profileFile.delete();
		}
		//Write the profile data
		FileWriter writer = new FileWriter(profileFile);
		writer.write(profile.getName());
		writer.write(GlobalInfo.NEW_LINE);
		writer.write(""+profile.getHighestLevel()); //convert to string
		writer.write(GlobalInfo.NEW_LINE);
		writer.write(profile.getImage());
		writer.write(GlobalInfo.NEW_LINE);
		
		writer.close();
	}
	
	/**
	 * Creates a new default profile with the next available profile ID.
	 * This new profile is automatically saved to file after being created.
	 * @return The newly created profile.
	 * @throws IOException If somehow the profileFile path we generate is a folder.
	 */
	public static Profile createNewProfile() throws IOException {
		int newID = FileManager.getNextAvailableProfileID();
		Profile profile = new Profile(newID);
		saveProfile(profile);
		return profile;
	}
	
}
