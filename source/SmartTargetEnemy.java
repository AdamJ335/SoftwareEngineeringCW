import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * An enemy that uses the A Star Path Finding algorithm to 
 * move towards the player. 
 * Moves in a random direction if no path could be found.
 * @author Josiah Richards
 * @version 1.2
 */
public class SmartTargetEnemy extends Enemy {

	//The keyword used for this object in the meta information.
	public static final String META_MOVEMENT_KEYWORD = "smart";
	
	//The image file name.
	private static final String IMAGE_NAME = "SmartEnemy.png";
	
	//Generates random numbers, used for choosing a random direction.
	private Random randomNumGenerator;
	
	/**
	 * Creates a new smart targeting enemy at a given location for a
	 * given map and facing a given direction.
	 * @param x The starting x coordinate for this enemy.
	 * @param y The starting y coordinate for this enemy.
	 * @param map The map this enemy is on.
	 * @param startingDirection The direction this enemy should face
	 * @throws MalformedURLException If the sprite for this enemy is invalid.
	 */
	public SmartTargetEnemy(int x, int y, Map map, Direction startingDirection) 
														throws MalformedURLException {
		super(x, y, map, startingDirection);
		randomNumGenerator = new Random();
		setImage(IMAGE_NAME);
	}
	
	@Override
    public String getMetaInfo() {
		return String.format(Enemy.META_FORMAT, gridCoords.toString(), 
				Enemy.META_KEYWORD, META_MOVEMENT_KEYWORD,
				currentDirection.toString());
	}
	
	/**
	 * Returns the next direction this enemy should move in to
	 * reach a target.
	 * Uses the A Star Path Finding algorithm and returns a random
	 * valid direction if no path is found.
	 * @param target The grid coordinate we want to get to.
	 * @return The direction we should move to get to the target,
	 * 			this direction is random if no path is found.
	 */
	public Direction aStarPathFindNextMove(Vector2 target) {
		
		//Find a path to the target
		AStarPathFinder pathFinder = new AStarPathFinder(map);
		Vector2[] path = pathFinder.getPath(gridCoords, target, this);
		
		//If a path was found
		if (path.length > 0) {
			try{
				Direction moveDir = new Direction(path[0].getX() - gridCoords.getX(),
												  path[0].getY() - gridCoords.getY());
				return moveDir;
			} catch(IllegalArgumentException e) {
				return new Direction(0, 0);
			}
		} else {
			
			//No path found
			//Get a list of possible directions
			Direction[] directionsArray = new Direction[4];
			directionsArray[0] = new Direction(Direction.DIR_UP);
			directionsArray[1] = new Direction(Direction.DIR_DOWN);
			directionsArray[2] = new Direction(Direction.DIR_LEFT);
			directionsArray[3] = new Direction(Direction.DIR_RIGHT);
			ArrayList<Direction> directionsList = new ArrayList<Direction>();
			for (int i = 0; i < directionsArray.length; i++) {
				if (canMove(directionsArray[i])) {
					directionsList.add(directionsArray[i]);
				}
			}
			
			//If there are possible directions
			if (directionsList.size() > 0) {
				int randNum = randomNumGenerator.nextInt(directionsList.size());
				//Choose a random valid direction
				return directionsList.get(randNum);
			} else {
				//No possible directions
				return new Direction(0, 0);
			}
		}
	}

	@Override
	public Direction getNextMove() {
		//Path find to the player.
		return aStarPathFindNextMove(map.getPlayer().getGridCoords());
	}

	
}
