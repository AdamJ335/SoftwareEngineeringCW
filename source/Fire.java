import java.net.MalformedURLException;

/**
 * Stores basic information about a Fire Tile
 * Kills entities that step on this specific tile
 * @author Adam Jennings 955770
 * @version 1.0
 */
public class Fire extends DeadlyTile {
	
	//The image file name.
	private static final String IMAGE_NAME = "fire.png";
	//The symbol of this cell in a map file.
	public static final char SYMBOL = 'f';
	
	/**
	 * Creates a Fire object at a given coordinate for a map.
	 * Doesn't add the Fire to the map!
	 * @param x The x grid coordinate.
	 * @param y The y grid coordinate.
	 * @param map The map the Fire is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Fire(int x, int y, Map map) throws MalformedURLException {
		super(x,y,map);
		setImage(IMAGE_NAME);
		requiresBoots = Boot.BootType.fire;
	}
	
	/**
	 * Creates a Fire object for a map.
	 * Doesn't add the Fire to the map!
	 * @param map The map the Fire is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Fire(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
		requiresBoots = Boot.BootType.fire;
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
