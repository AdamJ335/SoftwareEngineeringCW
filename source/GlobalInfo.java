import java.net.MalformedURLException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Information that's constant between scenes.
 * @author Josiah Richards
 * @version 1.1
 */
public class GlobalInfo {
	//The new line character for writing files.
	public static final String NEW_LINE = String.format("%n");
	
	//The profile ID of the current profile we're using
	private int selectedProfileID;
	//The level/map ID of the current level we're playing.
	private int currentLevelID;
	//The profile ID of the profile we're editing.
	private int editingProfileID;
	//If we're continuing from a save file or not.
	private boolean continueLevel;
	
	/**
	 * Creates a new GlobalInfo with default values.
	 */
	public GlobalInfo() {
		selectedProfileID = -1;
		currentLevelID = -1;
		editingProfileID = -1;
		continueLevel = false;
	}
	
	/**
	 * Plays a sound.
	 * @param soundName The name of the sound file with its extension
	 * @throws MalformedURLException 
	 */
	public static void playSound(String soundName) throws MalformedURLException {
		Media sound = new Media(FileManager.filePathToURL(FileManager.getSoundPath(soundName)).toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
	
	/**
	 * Sets the selected profile ID.
	 * @param selectedProfileID The new selected profile ID.
	 */
	public void setSelectedProfileID(int selectedProfileID) {
		this.selectedProfileID = selectedProfileID;
	}
	
	/**
	 * Sets the current level ID.
	 * @param currentLevelID The new current level ID.
	 */
	public void setCurrentLevelID(int currentLevelID) {
		this.currentLevelID = currentLevelID;
	}
	
	/**
	 * Sets the profile ID of the profile we want to edit.
	 * @param editingProfileID The new profile ID of the profile we want to edit.
	 */
	public void setEditingProfileID(int editingProfileID) {
		this.editingProfileID = editingProfileID;
	}
	
	/**
	 * Sets if we're continuing from a save or not.
	 * @param continueLevel The new continueLevel value.
	 */
	public void setContinueLevel(boolean continueLevel) {
		this.continueLevel = continueLevel;
	}
	
	/**
	 * Gets the value of selectedProfileID.
	 * @return The value of selectedProfileID.
	 */
	public int getSelectedProfileID() {
		return selectedProfileID;
	}
	
	/**
	 * Gets the value of editingProfileID.
	 * @return The value of editingProfileID.
	 */
	public int getEditingProfileID() {
		return editingProfileID;
	}
	
	/**
	 * Gets the value of currentLevelID.
	 * @return The value of currentLevelID.
	 */
	public int getCurrentLevelID() {
		return currentLevelID;
	}
	
	/**
	 * Gets the value of continueLevel.
	 * @return The value of continueLevel.
	 */
	public boolean getContinueLevel() {
		return continueLevel;
	}
	
}
