import java.net.MalformedURLException;

/**
 * A basic cell that doesn't allow moving entities to stay on it.
 * @author Daniel Miles 973755
 * @version 1.0
 */
public class Wall extends Cell{

	//The image file name.
	private static final String IMAGE_NAME = "wall.png";
	//The symbol of this cell in a map file.
	public static final char SYMBOL = '#';
	
	/**
	 * Create a wall at given coordinates for a map.
	 * Doesn't add itself to the map!
	 * @param x The x coordinate of this wall on the map.
	 * @param y The y coordinate of this wall on the map.
	 * @param map The map this Wall is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Wall (int x, int y, Map map) throws MalformedURLException{
		super(x, y, map);
		setImage(IMAGE_NAME);
	}

	public Wall(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
	}

	@Override
	public char getSymbol() {
		return SYMBOL;
	}
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction fromDir) {
		//Send the moving back to where it came from 
		//if it tries stepping on this.
		fromDir.reverse();
		steppedOnBy.move(fromDir);
	}
}
