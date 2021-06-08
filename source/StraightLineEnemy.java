import java.net.MalformedURLException;

/**
 * An enemy that walks in a straight line, turning around
 * and moving in the opposite direction when it reaches a wall.
 * @author Joshiah Richards and Ajaya Budhathoki
 * @version 2.0
 */
public class StraightLineEnemy extends Enemy{

	//The keyword used for this object in the meta information.
	public static final String META_MOVEMENT_KEYWORD = "straight";
	
	//The image file name.
	private static final String IMAGE_NAME = "StraightLineEnemy.png";
	
	/**
	 * Create straight line following enemy at a given position and
	 * facing a given direction for a map.
	 * Doesn't add the enemy to the map.
	 * @param x The x coordinate on the grid
	 * @param y The y coordinate on the grid
	 * @param map The map(grid) this object is on.
	 * @param startingDirection The direction this enemy starts off facing.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public StraightLineEnemy(int x, int y, Map map, Direction startingDirection) throws MalformedURLException {
		super(x, y, map, startingDirection);
		setImage(IMAGE_NAME);
	}
	
	@Override
    public String getMetaInfo() {
		return String.format(Enemy.META_FORMAT, gridCoords.toString(), Enemy.META_KEYWORD, META_MOVEMENT_KEYWORD,
				currentDirection.toString());
	}
	
	@Override
	public Direction getNextMove() {
		//If we can't walk forward
		Vector2 checkCoord = gridCoords.addXYCreateNew(currentDirection);
		if (!canWalkOn(checkCoord)){
		    //we need to turn around
		    currentDirection.reverse();
		}

		//Move forward
		return currentDirection;
	}

}
