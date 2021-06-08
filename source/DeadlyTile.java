/**
 * Stores basic information about a Deadly Tile and
 * kills players that step on this if they don't have the correct boots.
 * @author Adam Jennings 955770
 * @version 1.1
 */
public class DeadlyTile extends Cell {
	
	//The boot type required to step on this without dying.
	protected Boot.BootType requiresBoots;

	/**
	 * Creates a deadly tile object at a given location for a map. 
	 * Doesn't actually add the DeadlyTile to the map.
	 * @param x The x coordinate on the map.
	 * @param y The y coordinate on the map.
	 * @param map The map this DeadlyTile is for.
	 */
	public DeadlyTile(int x, int y, Map map) {
		super(x,y,map);
	}
	
	/**
	 * 
	 * @param map
	 */
	public DeadlyTile(Map map) {
		super(map);
	}
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction fromDir) {
		//If stepped on by a player
		if(steppedOnBy instanceof Player) {
			Player player = (Player) steppedOnBy;
			//If they don't have the required boot type, kill them
			if(!player.getInventory().hasBoots(requiresBoots)) {
				player.die();
			}
		}
	}
}
