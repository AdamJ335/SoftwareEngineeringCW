import java.net.MalformedURLException;

/**
 * A cell that causes the player to win when they step on it.
 * @author Joshiah Richards and Ajaya Budhathoki
 * @version 2.0
 */
public class Goal extends Cell {

	//The image file name.
	private static final String IMAGE_NAME = "goal.png";
	//The symbol of this cell in a map file.
	public static final char SYMBOL = 'g';
	
	/**
	 * Creates a Goal at a given coordinate for a map.
	 * Doesn't add the Goal to the map!
	 * @param x The x coordinate on the map.
	 * @param y The y coordinate on the map.
	 * @param map The map this Goal is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Goal(int x, int y, Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
	
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction movedDir) {
		if (steppedOnBy instanceof Player) {
			Player player = (Player) steppedOnBy;
			player.win();
		}
	}
}
