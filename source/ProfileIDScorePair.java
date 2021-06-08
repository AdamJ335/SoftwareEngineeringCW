/**
 * Stores a score and the ID of the profile that
 * scored it together.
 * @author Josiah Richards
 * @version 1.1
 */
public class ProfileIDScorePair {
	
	//The format of this profile as a string where the first
	//%d is the profile ID and the second %d is the score.
	private static final String STRING_FORMAT = "%d,%d";
	
	//ID of the profile that got the score.
	private int profileID;
	//The time taken to complete the map in milliseconds.
	private long score;
	
	/**
	 * Creates a new ProfileIDScorePair with default values.
	 */
	public ProfileIDScorePair() {
		profileID = -1;
		score = 9999999999999L;
	}
	
	/**
	 * Creates a new ProfileIDScorePair with a given
	 * ID and score.
	 * @param profileID The ID of the profile that got the score.
	 * @param score The time taken to complete the map in milliseconds.
	 */
	public ProfileIDScorePair(int profileID, long score) {
		this.profileID = profileID;
		this.score = score;
	}
	
	/**
	 * Formats this pair as a String.
	 * @return This pair as String.
	 */
	public String toString() {
		return String.format(STRING_FORMAT, profileID, score);
	}
	
	/**
	 * Gets the profile ID.
	 * @return The profile ID.
	 */
	public int getProfileID() {
		return profileID;
	}
	
	/**
	 * Gets the score(time taken in milliseconds).
	 * @return The score(time taken in milliseconds).
	 */
	public long getScore() {
		return score;
	}
	
	/**
	 * Sets the profile ID of this pair.
	 * @param profileID The new profile ID of this pair.
	 */
	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}
	
	/**
	 * Sets the score(time taken in milliseconds) of this pair.
	 * @param score The score of this pair.
	 */
	public void setScore(long score) {
		this.score = score;
	}
}
