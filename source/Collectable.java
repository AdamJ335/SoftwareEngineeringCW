/**
 *This class represents the generic behaviours of all the collectables
 * in the game.
 * @author Tom Cordall
 * @version 1.0
 */
public abstract class Collectable extends Entity {
    
	/**
	 * Creates a new collectable object at a given coordinate
	 * for a map.
	 * Doesn't actually add the collectable to the map!
	 * @param x The x coordinate of this cell.
	 * @param y The y coordinate of this cell.
	 * @param map The map this collectable is for.
	 */
	public Collectable(int gridX, int gridY, Map map) {
		super(gridX, gridY, map);
	}
	
	/**
	 * Creates a new collectable object for a map.
	 * Doesn't actually add the collectable to the map!
	 * @param map The map this collectable is for.
	 */
	public Collectable(Map map) {
		super(map);
	}

	/**
	 * This overridden version of stepOn checks if a collectable has been
	 * stepped on by the player, if this is the case it collects the item
	 * and removes it from the map.
	 * @param steppedOnBy The moving object that stepped on this object.
	 * @param movedDir The direction the moving object moved to step on this.
	 */
	@Override
	public void stepOn(Moving steppedOnBy, Direction movedDir) {
		if(steppedOnBy instanceof Player) {
			Player player = (Player) steppedOnBy;
			collect(player);
			map.removeEntity(this);
		}
	}
	/**
	 * This method is used for all of the collectables to add the collectable to the
	 * player.
	 * @param player The player that the collectable is added to.
	 */
    public abstract void collect(Player player);
}
