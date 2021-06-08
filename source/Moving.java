import java.util.ArrayList;

/**
 * An entity that can move.
 * @author Josiah Richards
 * @version 1.2
 */
public class Moving extends Entity {

	//The direction this entity is currently facing.
	protected Direction currentDirection;
	
	/**
	 * Creating a Moving Entity at a given coordinate and facing a given direction
	 * for a map.
	 * Doesn't create add itself to the map!
	 * @param x The x coordinate of this Moving Entity on the map.
	 * @param y The y coordinate of this Moving Entity on the map.
	 * @param map The map this is for.
	 * @param startingDirection The direction this starts facing.
	 */
	public Moving(int x, int y, Map map, Direction startingDirection) {
		super (x, y, map);
		currentDirection = startingDirection;
	}
	
	/**
	 * Creating a Moving Entity at a given coordinate for a map.
	 * Doesn't create add itself to the map!
	 * Starts facing right by default.
	 * @param x The x coordinate of this Moving Entity on the map.
	 * @param y The y coordinate of this Moving Entity on the map.
	 * @param map The map this is for.
	 */
	public Moving(int x, int y, Map map) {
		//Start facing right.
		this(x, y, map, new Direction(Direction.DIR_RIGHT));
	}

	/**
	 * Creating a Moving Entity at 0,0 for a map.
	 * Doesn't create add itself to the map!
	 * Starts facing right by default.
	 * @param map The map this is for.
	 */
	public Moving(Map map) {
		this(0, 0, map);
	}
	
	/**
	 * Checks if this is allowed to walk on an object on the grid.
	 * @param gridObject The OnGrid object in question.
	 * @return If this is allowed to walk on an object on the grid.
	 */
	public boolean canWalkOn(OnGrid gridObject) {
		
		if(gridObject instanceof Wall) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if this can walk on a given coordinate.
	 * That is if it can walk on the cell and all the entities
	 * at that coordinate.
	 * @param point
	 * @return True if this is allowed to walk on the coordinate, false otherwise.
	 */
	public boolean canWalkOn(Vector2 point) {
		//If the point is outside the map, return false
		if (point.getX() < 0 || point.getX() >= map.getGridDimensions().getX()) {
			return false;
		}
		if (point.getY() < 0 || point.getY() >= map.getGridDimensions().getY()) {
			return false;
		}
		
		//If we can't walk on the cell at the new position, return false.
		Cell obstacleCell = map.getCellAt(point);
		if(obstacleCell != null) {
			if(!canWalkOn(obstacleCell)) {
				return false;
			}
		}
		
		//Get all the entities at the new position(excluding this),
		//if we can't walk on ANY of those entities, return false.
		ArrayList<Entity> obstacleEntities = map.getEntitiesAt(point, this);
		if(obstacleEntities != null) {
			for (Entity entity : obstacleEntities) {
				if (!canWalkOn(entity)) {
					return false;
				}
			}
		}
		
		//Nothing left to check we can't walk on hence we can move
		return true;
	}
	
	
	/**
	 * Checks if this is allowed to move in a direction.
	 * @param direction The direction this wants to move.
	 * @return If this is allowed to move in a direction.
	 */
	public boolean canMove(Direction direction) {
		//Get the theoretical new position if we moved in the direction.
		Vector2 newPos = new Vector2(gridCoords);
		newPos.addXY(direction);
		
		return canWalkOn(newPos);
	}
	
	/**
	 * Turns this entity left(doesn't actually move)
	 */
	public void turnLeft() {
		currentDirection = currentDirection.getLeft();
	}
	
	/**
	 * Turns this entity right(doesn't actually move)
	 */
	public void turnRight() {
		currentDirection = currentDirection.getRight();
	}
	
	/**
	 * Moves in a direction if its able to, triggering anything
	 * this steps on in the process.
	 * @param direction The direction to move.
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			gridCoords.addXY(direction);
			
			map.triggerStepOns(this, direction);
		}
	}
}
