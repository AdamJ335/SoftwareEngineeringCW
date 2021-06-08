/**
 * An abstract class representing something that goes
 * on top of cells on a map.
 * @author Daniel Miles 973755
 * @version 1.1
 */
public abstract class Entity extends OnGrid{
	/**
	 * Create an entity at given coordinates for a map.
	 * Doesn't add the entity to the map!
	 * @param x The x coordinate on the map.
	 * @param y The y coordinate on the map.
	 * @param map The map this entity is for.
	 */
	public Entity(int x, int y, Map map) {
		super (x, y, map);
	}
	
	/**
	 * Create an entity for a map.
	 * Doesn't add the entity to the map!
	 * @param map The map this entity is for.
	 */
	public Entity(Map map) {
		super(map);
	}
}
