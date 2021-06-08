import java.net.MalformedURLException;

/**
 * An enemy that tries to walk in a straight line towards the player.
 * @author Josiah Richards
 * @version 1.0
 */
public class DumbTargetEnemy extends Enemy {
	
	//The keyword used for this object in the meta information.
	public static final String META_MOVEMENT_KEYWORD = "dumb";
	
	//The image file name.
	private static final String IMAGE_NAME = "DumbEnemy.png";
	
	/**
	 * Creates a DumbTargetEnemy at a given location and facing a given direction
	 * for a map.
	 * Doesn't add itself to the map!
	 * @param x The x grid coordinate.
	 * @param y The y grid coordinate.
	 * @param map The map this enemy is for.
	 * @param startingDirection The direction this enemy starts off facing.
	 * @throws MalformedURLException If the sprite couldn't be found.
	 */
	public DumbTargetEnemy(int x, int y, Map map, Direction startingDirection) throws MalformedURLException {
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
		
		//Get the difference between the player's position and
		//this enemy's position.
		Vector2 playerPos = map.getPlayer().getGridCoords();
		Vector2 difference = new Vector2(playerPos.getX() - gridCoords.getX(), playerPos.getY() - gridCoords.getY());
		
		//If we're ontop of them(0 difference), don't move
		if (difference.getX() == 0 && difference.getY() == 0) {
			return new Direction(0, 0);
		}
		
		//Get what the target x and y directions would be
		//by converting their differences to a value of 1 or -1
		Direction targetXDir = new Direction((difference.getX() > 0 ? 1 : -1), 0);
		Direction targetYDir = new Direction(0, (difference.getY() > 0 ? 1 : -1));
		
		//Move in whatever direction has the greatest distance to travel
		//(making it move 'diagonally' instead of in an L)
		
		//If there's a greater distance to travel in the x direction
		//or we can't move in the target Y direction
		if (Math.abs(difference.getX()) >= Math.abs(difference.getY())
				|| !canMove(targetYDir)) {
			if (difference.getX() != 0 && canMove(targetXDir)) {
				return targetXDir;
			}
		}
		
		//If we didn't move in the x direction then let's check
		//if we can in the y.
		if (difference.getY() != 0 && canMove(targetYDir)) {
			return targetYDir;
		}
		
		//We can't move :c
		return new Direction(0, 0);
	}
}
