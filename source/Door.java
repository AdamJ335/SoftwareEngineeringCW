import java.net.MalformedURLException;

/**
 * A class that represents a door that can tried to be opened
 * when stepped on by a player, moving them back if they couldn't
 * open it.
 * @author Ajaya Budhathoki and Josiah Richards
 * @version 3.0
 */
public abstract class Door extends Cell {

	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "door";
	//The symbol of this cell in a map file.
	public static final char SYMBOL = 'd';
	
	/**
	 * Creates a door object for a map.
	 * Doesn't add the door to the map!
	 * @param map The map(grid) this object is on.
	 */
	public Door(Map map) {
		super(map);
	}
	
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
	
	/**
	 * Steps on this door to try and open it.
	 * @param steppedOnBy The moving object that stepped on this object.
	 * @param movedDir The direction the moving object moved to step on this.
	 */
	@Override
	public void stepOn(Moving steppedOnBy, Direction movedDir) {
		//If the door could be opened, open it.
		if(steppedOnBy instanceof Player) {
			//If stepped on by a player, see if they can open this door
			Player player = (Player) steppedOnBy;
			if (tryToOpen(player)) {
				//Open the door if they're allowed to.
				try {
					open();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else {
				//Couldn't open the door,
				//move the player back to where they came from
				movedDir.reverse();
				player.move(movedDir);
			}
		}
	}
	
	/**
	 * Opens the door by replacing the Cell at its coordinates with
	 * a Ground Cell.
	 * @throws MalformedURLException If the ground sprite is invalid.
	 */
	public void open() throws MalformedURLException {
		Ground newGroundCell = new Ground(map);
		map.setCellAt(gridCoords.getX(), gridCoords.getY(), newGroundCell);
	}
	
	/**
	 * Checks to see if a player can open this door.
	 * This is called try to open instead of check because
	 * it may affect the player e.g. consume keys e.c.t. and
	 * so it's not repeatable like a check function.
	 * @param player The player trying to open this door.
	 * @return If the player can open this door(true) or not(false).
	 */
	public abstract boolean tryToOpen(Player player);
}
