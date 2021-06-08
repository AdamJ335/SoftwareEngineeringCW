import java.awt.Color;

/**
 * Converts Colors to Strings and Strings to Colors
 * @author Josiah Richards
 * @version 1.0
 */
public class ColourUtils {
	
	//The string representation of different colours.
	private static final String RED_NAME = "red";
	private static final String GREEN_NAME = "green";
	private static final String BLUE_NAME = "blue";
	private static final String YELLOW_NAME = "yellow";
	
	/**
	 * Get the string representation of a colour object.
	 * Allows for RED, GREEN, BLUE and YELLOW to be converted.
	 * @param colour The colour to convert to a string.
	 * @return The colour as a string e.g. red,
	 * 			null if the Color object isn't recognised.
	 */
	public static String colourToString(Color colour){
		if(colour == Color.RED){
			return RED_NAME;
		}
		if(colour == Color.GREEN){
			return GREEN_NAME;
		}
		if(colour == Color.BLUE){
			return BLUE_NAME;
		}
		if(colour == Color.YELLOW){
			return YELLOW_NAME;
		}
		return null;
	}
	
	/**
	 * Converts a string to a Color.
	 * Allows for red, green, blue and yellow to be converted.
	 * @param colourStr The string to convert.
	 * @return The Color the string represents,
	 * 			null if the string is unrecognised.
	 */
	public static Color stringToColour(String colourStr) {
		switch (colourStr.toLowerCase()) {
			case RED_NAME:
				return Color.RED;
			case BLUE_NAME:
				return Color.BLUE;
			case GREEN_NAME:
				return Color.GREEN;
			case YELLOW_NAME:
				return Color.YELLOW;
			default:
				return null;
		}
	}
}
