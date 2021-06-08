import java.awt.Color;
import java.net.MalformedURLException;

/**
 * Class that adds coloured doors.
 * @author Ajaya Budhathoki and Josiah Richards
 * @version 2.0
 */
public class ColouredDoor extends Door {
	
	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "door";
	//The format for the meta information of this object.
    private static final String META_FORMAT = "%s,%s,%s";
	//The image file name format.
	private static final String IMAGE_NAME_FORMAT = "%sDoor.png";
	
	//The colour of this coloured door
	private Color colour;

	/**
	 * Constructor to create a coloured door for a map.
	 * Doesn't actually add the coloured door to the map!
	 * @param colour The colour of the door.
	 * @param map The map this coloured door is for.
	 * @throws MalformedURLException If the sprite for this door is invalid.
	 */
	public ColouredDoor(Color colour, Map map) throws MalformedURLException {
		super(map);
		this.colour = colour;
		setImage(String.format(IMAGE_NAME_FORMAT, ColourUtils.colourToString(colour)));
	}
	
	@Override
    public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD, 
				ColourUtils.colourToString(colour));
	}
	
	@Override
	public boolean tryToOpen(Player player) {
		if(player != null) {
			return player.getInventory().hasAndConsumeKey(colour);
		}
		return false;
	}
}
