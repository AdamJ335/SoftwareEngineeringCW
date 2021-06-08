import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Stores basic information about a map(its name and highscores).
 * @author Josiah Richards
 * @version 1.0
 */
public class MapInfo {
	//How many highscores to store.
	public static final int NUM_HIGHSCORES = 3;
	
	private static final String INFO_FILE_DELIMITER = ",";
	private static final String LOAD_ERROR_MSG = "Failed to load map info.";
	
	//The name of this map.
	private String mapName;
	//The ID of this map.
	private int mapID;
	//The highscores for this map.
	private ProfileIDScorePair[] highScores;
	
	/**
	 * Creates a new MapInfo with a given Map ID
	 * @param mapID The ID of the map this information is about.
	 */
	public MapInfo(int mapID) {
		this.mapID = mapID;
		highScores = new ProfileIDScorePair[NUM_HIGHSCORES];
	}
	
	/**
	 * Loads a map with a given map ID.
	 * @param mapID The map ID of the map to load the information of.
	 * @return A new MapInfo object with information about the map.
	 * @throws ParsingException if the information about the map couldn't be loaded.
	 */
	public static MapInfo loadMapInfo(int mapID) throws ParsingException {
		
		try {
			//Open the map info file
			File infoFile = new File(FileManager.getMapInfoFile(mapID));
			Scanner in = new Scanner(infoFile);
			
			MapInfo newInfo = new MapInfo(mapID);
			//Read map name
			newInfo.setMapName(in.nextLine());
			//Read highscores
			while (in.hasNextLine()) {
				String line = in.nextLine();
				//Parse this score
				String[] args = line.split(INFO_FILE_DELIMITER);
				if (args.length == 2){
					//Parse profile ID
					int id = Integer.parseInt(args[0]);
					
					//Check profile exists
					boolean profileExists = false;
					try {
						ProfileManager.loadProfile(id);
						profileExists = true;
					} catch (ParsingException e) {
						profileExists = false;
					}
					//If the profile no longer exists, ignore this score.
					if (profileExists) {
						//Parse score(time taken in milliseconds)
						long score = Long.parseLong(args[1]);
						//Add this score to the correct place in the highscores array.
						newInfo.tryAddScore(id, score);
					}
				}
			}
			in.close();
			return newInfo;
		} catch (Exception e) {
			throw new ParsingException(LOAD_ERROR_MSG);
		}
	}
	
	/**
	 * Saves this map info to the correct info file.
	 */
	public void save() {
		//Create the map directory if needed(shouldn't ever happen really)
		String mapFolder = FileManager.getMapFolderPath(mapID);
		FileManager.createDirIfNeeded(mapFolder, true);
		
		File infoFile = new File(FileManager.getMapInfoFile(mapID));
		//Overwrite existing by deleting an existing infoFile.
		if (infoFile.exists()) {
			infoFile.delete();
		}
		FileWriter writer;
		try {
			writer = new FileWriter(infoFile);
			//Write map name
			writer.write(mapName);
			writer.write(GlobalInfo.NEW_LINE);
			//Write highscores
			for (int i = 0; i < highScores.length; i++){
				if (highScores[i] != null) {
					//Check profile exists
					boolean profileExists = false;
					try {
						ProfileManager.loadProfile(highScores[i].getProfileID());
						profileExists = true;
					} catch (ParsingException e) {
						profileExists = false;
					}
					//If the profile no longer exists, ignore this score.
					if (profileExists) {
						writer.write(highScores[i].toString());
						writer.write(GlobalInfo.NEW_LINE);
					}
				}
			}
			writer.close();
		} catch (IOException e) {
			//If somehow infoFile is a folder
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Inserts a high score into the high scores array at a given index.
	 * @param position The index in highScores to insert at.
	 * @param highScore The high score to insert.
	 */
	private void insertHighScore(int position, ProfileIDScorePair highScore) {
		if (position < highScores.length) {
			//Free a space by moving all the scores down starting at position.
			ProfileIDScorePair lastPair = highScores[position];
			for (int i = position+1; i < highScores.length; i++) {
				ProfileIDScorePair tmp = highScores[i];
				highScores[i] = lastPair;
				lastPair = tmp;
			}
			
			//Insert the profile into the free space.
			highScores[position] = highScore;
		}
	}
	
	/**
	 * Adds a score to the high scores if it's a new high score.
	 * Lower score = better.
	 * @param profileID The profile that got this score.
	 * @param score The score this profile got(time in milliseconds to complete level).
	 */
	public void tryAddScore(int profileID, long score) {
		for (int i = 0; i < highScores.length; i++) {
			//If our score is lower than this high score, insert it.
			if (highScores[i] == null || score < highScores[i].getScore()) {
				ProfileIDScorePair newHighScore = new ProfileIDScorePair(profileID, score);
				insertHighScore(i, newHighScore);
				//Stop trying to add the score.
				break; //Please forgive me for this loop is short <3
			}
		}
		
	}
	
	/**
	 * Gets the map ID this info is for.
	 * @return The map ID this info is for.
	 */
	public int getMapID() {
		return mapID;
	}
	
	/**
	 * Gets the map name.
	 * @return The map name.
	 */
	public String getMapName() {
		return mapName;
	}
	
	/**
	 * Gets the high scores array.
	 * @return The high scores array.
	 */
	public ProfileIDScorePair[] getHighScores() {
		return highScores;
	}
	
	/**
	 * Sets the name of this map.
	 * @param mapName The new name.
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	/**
	 * Sets the map ID of this info.
	 * @param mapID The map ID this info is for.
	 */
	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
	
	/**
	 * Sets the high scores array.
	 * @param highScores The new high scores array.
	 */
	public void setHighScores(ProfileIDScorePair[] highScores) {
		this.highScores = highScores;
	}
	
}
