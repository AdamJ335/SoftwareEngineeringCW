import java.net.MalformedURLException;

/**
 * A basic cell that doesn't do anything when stepped on.
 * @author Daniel Miles 973755
 * @version 1.1
 */
public class Ground extends Cell{
	//The image file name.
	private static final String IMAGE_NAME = "ground.png";
	//The symbol of this cell in a map file.
	public static final char SYMBOL = ' ';
	
	/**
	 * Creates a Ground at a given coordinate for a map.
	 * Doesn't add this to the map!
	 * @param x The x coordinate on the map.
	 * @param y The x coordinate on the map.
	 * @param map The map this is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Ground (int x, int y, Map map) throws MalformedURLException{
		super(x, y, map);
		setImage(IMAGE_NAME);
	}

	/**
	 * Creates a Ground for a map.
	 * Doesn't add this to the map!
	 * @param map The map this is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Ground(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
