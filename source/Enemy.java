/**
 * An moving entity that calculates its own next move.
 * @author Adam Jennings 955770
 * @version 2.0
 */
public abstract class Enemy extends Moving {
	
	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "enemy";
	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s,%s,%s";

	/**
	 * Creates an enemy at a given location and facing a given direction
	 * for a map.
	 * Doesn't add the enemy to the map!
	 * @param x The starting x coordinate for this enemy on the map.
	 * @param y The starting y coordinate for this enemy on the map.
	 * @param map The map this enemy is for.
	 * @param startingDirection The direction this enemy starts facing.
	 */
	public Enemy(int x, int y, Map map, Direction startingDirection) {
		super (x, y, map, startingDirection);
	}

	
	/**
	 * Gets the next move and moves in that direction.
	 */
	public void preformNextMove() {
		move(getNextMove());
	}
	
	/**
	 * Checks if there's an unwalkable(wall) in a direction
	 * @param dir The direction to check
	 * @return True if there is an unwalkable(Wall), false otherwise.
	 */
	public boolean checkForUnWalkable(Direction checkDirection) {
		Vector2 checkPos = new Vector2(gridCoords);
		checkPos.addXY(checkDirection);
		return !canWalkOn(checkPos);
	}
	
	@Override
	public boolean canWalkOn(OnGrid gridObject) {
		//Enemies can walk on ground, collectables, hint blocks or movings only
		if (gridObject instanceof Ground || gridObject instanceof Collectable 
					|| gridObject instanceof Moving || gridObject instanceof HintBlock) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction fromDir) {
		//Kill the player if they step on this enemy.
		if(steppedOnBy instanceof Player) {
			Player player = (Player) steppedOnBy;
			player.die();
		}
	}
	
	/**
	 * Calculates the next direction this enemy should move in.
	 * @return The next direction this enemy should move in.
	 */
	public abstract Direction getNextMove();
	
	
}
