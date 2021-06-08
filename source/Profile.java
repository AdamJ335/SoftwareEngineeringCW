/**
 * Class to store information about a users profile.
 * @Author Daniel Miles 973755
 * @Version 1.0
 */
public class Profile {
	
	public static final String DEFAULT_IMAGE = "DefaultAvatar.png";
	
	//The properties of this profile
	private int ID;
	private String name;
	//The full file path to this profile's image.
	private String image;
	//The highest level this profile has completed.
	private int highestLevel;
	
	/**
	 * Creates a profile with a given profile ID
	 * and default values.
	 * @param ID
	 */
	public Profile(int ID) {
		this.ID = ID;
		name = "Unnamed";
		image = FileManager.getImagePath(DEFAULT_IMAGE);
		highestLevel = 0; 
	}
	
	/**
	 * Gets the ID of this profile.
	 * @return The ID of this profile.
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Gets profile's name.
	 * @return The name of this profile.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets profile's image.
	 * @return The full image path for this profile.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Gets the highest level the user has reached.
	 * @return The highest level the user has reached.
	 */
	public int getHighestLevel() {
		return highestLevel;
	}

	/**
	 * Sets this profile's ID.
	 * @param ID The new ID for this profile.
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Sets the name of this profile.
	 * @param name The new name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the image for this profile.
	 * @param image The full path to the image.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Sets users level reached.
	 * @param highestLevel The highest level this profile has reached.
	 */
	public void setHighestLevel(int highestLevel) {
		this.highestLevel = highestLevel;
	}
}