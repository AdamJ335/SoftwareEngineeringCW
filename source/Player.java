import java.net.MalformedURLException;

/**
 * A player that can move about the map
 * @author Nasim Ahmed
 * @version 2.0
 */
public class Player extends Moving {
	//The format for the meta information of this object.
	public static final String META_FORMAT1 = "%s,%s";
	//The image file name.
	private static final String IMAGE_NAME = "player.png";
	
	//Stores everything this player has collected
	private Inventory inventory;
	
	/**
	 * Creates a player for a map.
	 * Doesn't add the player to the map!
	 * @param map The map this player is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Player(Map map) throws MalformedURLException {
		super(map);
		inventory = new Inventory();
		setImage(IMAGE_NAME); 
	}
		
	/**
	 * Gets this player's inventory.
	 * @return This player's inventory.
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Sets this player's inventory.
	 * @param inventory The new inventory for this player.
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	@Override
	public String getMetaInfo() {
		//The basic meta info about this player
		String metaInfo = String.format(META_FORMAT1, gridCoords.toString(), Map.START_META_KEYWORD);
		//Add the meta info of this player's inventory
		if (inventory != null) {
			metaInfo += GlobalInfo.NEW_LINE;
			metaInfo += inventory.getMetaInfo();;
		}
		return metaInfo;
	}
	
	/**
	 * Kills the player and so they lose.
	 */
	public void die() {
		map.lose();
	}
	
	/**
	 * Makes the player win
	 */
	public void win() {
		map.win();
	}
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction movedDir) {
		//if an enemy steps on the player, the player dies.
		if(steppedOnBy instanceof Enemy) {
			die();
		}
	}
}
