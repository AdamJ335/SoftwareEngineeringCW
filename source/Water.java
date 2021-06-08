import java.net.MalformedURLException;

/**
 * A DeadlyTile that requires flippers to cross.
 * @author Adam Jennings 955770
 * @version 1.1
 */
public class Water extends DeadlyTile {
	//The symbol of this cell in a map file.
	public static final char SYMBOL = 'w';
	//The image file name.
	private static final String IMAGE_NAME = "water.png";
	
	/**
	 * Creates a Water Cell at given coordinates for a map that 
	 * requires flippers to cross.
	 * Doesn't add itself to the map.
	 * @param x The x coorindate of this Water on the map.
	 * @param y The y coorindate of this Water on the map.
	 * @param map The map this Water is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Water(int x, int y, Map map) throws MalformedURLException {
		super(x, y, map);
		setImage(IMAGE_NAME);
		requiresBoots = Boot.BootType.flipper;
	}
	
	/**
	 * Creates a Water Cell for a map that requires flippers to cross.
	 * Doesn't add itself to the map.
	 * @param map The map this Water is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Water(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
		requiresBoots = Boot.BootType.flipper;
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
