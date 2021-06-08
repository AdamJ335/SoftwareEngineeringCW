import java.net.MalformedURLException;

/**
 * Wall follow
 * @author Josiah Richards
 * @version 1.5
 */
public class WallFollowEnemy extends Enemy {

	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s,%s,%s,%s";

	//The keyword used for this object in the meta information.
	public static final String META_MOVEMENT_KEYWORD = "follow";
	
	//The image file name.
	private static final String IMAGE_NAME = "WallFollowingEnemy.png";
	
	//If we should follow the right wall instead.
	private boolean followRight = false;
	
	/**
	 * Creates a WallFollowEnemy that follows either the left or right wall for a map.
	 * Doesn't add itself to the map.
	 * @param followRight True if this should follow the right wall, false to follow the left wall.
	 * @param x The x coordinate for this enemy on the map.
	 * @param y The y coordinate for this enemy on the map.
	 * @param map The map this enemy is for.
	 * @param startingDirection The direction this enemy starts off facing.
	 * @throws MalformedURLException If the sprite is invalid
	 */
	public WallFollowEnemy(boolean followRight, int x, int y, Map map, Direction startingDirection) 
																		throws MalformedURLException {
		super(x, y, map, startingDirection);
		this.followRight = followRight;
		setImage(IMAGE_NAME);
	}
	
	@Override
    public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), Enemy.META_KEYWORD, META_MOVEMENT_KEYWORD,
				currentDirection.toString(), (followRight ? Direction.RIGHT_WORD : Direction.LEFT_WORD));
	}

	@Override
	public Direction getNextMove() {
		
		//Start off wanting to go forward
		Direction targetDir = currentDirection;
		Direction startingDir = currentDirection;
		
		//Protection against infinite loop
		//(which in theory shouldn't be able to happen as
		//we check if we're at our starting direction again)
		//but might as well just in case.
		int numTimesLooped = 0;
		//Follow left wall
		if (!followRight) {
			do {
				//If there is wall to the left
				if (checkForUnWalkable(currentDirection.getLeft())) {
					//See if we can go forward
					if(!checkForUnWalkable(targetDir)) {
						//We should go forward
						return targetDir;
					} else {
						//Turn right and try it all again
						targetDir = targetDir.getRight();
						turnRight();
					}
				} else {
					targetDir = targetDir.getLeft();
					turnLeft();
					return targetDir;
				}
				numTimesLooped++;
			} while (!startingDir.equals(targetDir) && numTimesLooped < 5);
		} else {//Follow right wall
			do {
				//If there is wall to the right
				if (checkForUnWalkable(currentDirection.getRight())) {
					//See if we can go forward
					if (!checkForUnWalkable(targetDir)) {
						//We should go forward
						return targetDir;
					} else {
						//Turn left and try it all again
						targetDir = targetDir.getLeft();
						turnLeft();
					}
				} else {
					targetDir = targetDir.getRight();
					turnRight();
					return targetDir;
				}
				numTimesLooped++;
			} while (!startingDir.equals(targetDir) && numTimesLooped < 5);
		}
		//We can't move
		return new Direction(0,0);
	}
	
}
