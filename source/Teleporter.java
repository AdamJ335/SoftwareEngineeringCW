import java.net.MalformedURLException;

/**
 * A cell that teleports you to another teleporter in the
 * direction they were travelling.
 * @author Josiah Richards
 * @version 1.1
 */
public class Teleporter extends Cell{
	
	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "teleporter";
	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s,%s";
	//The image file name.
	private static final String IMAGE_NAME = "teleporter.png";
	//The symbol of this cell in a map file.
	public static final char SYMBOL = 't';
	
	//The teleporter to teleport to
	private Teleporter teleportTo;
	
	/**
	 * Creates a teleporter at a given location for a map.
	 * Doesn't add itself to the map.
	 * @param x The x coordinate on the map.
	 * @param y The y coordinate on the map.
	 * @param map The map this is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Teleporter(int x, int y, Map map) throws MalformedURLException {
		super(x, y, map);
		setImage(IMAGE_NAME);
	}
	
	/**
	 * Creates a teleporter for a map.
	 * Doesn't add itself to the map.
	 * @param map The map this is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Teleporter(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
	}
	
	/**
	 * Creates a teleporter at a given location linked to a 
	 * given teleporter for a map.
	 * Doesn't add itself to the map.
	 * @param teleportTo The teleporter this teleporter teleports you to.
	 * @param x The x coordinate on the map.
	 * @param y The y coordinate on the map.
	 * @param map The map this is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Teleporter(Teleporter teleportTo, int x, int y, Map map) throws MalformedURLException {
		super(x, y, map);
		this.teleportTo = teleportTo;
		setImage(IMAGE_NAME);
	}
	
	/**
	 * Creates a teleporter linked to a 
	 * given teleporter for a map.
	 * Doesn't add itself to the map.
	 * @param teleportTo The teleporter this teleporter teleports you to.
	 * @param map The map this is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Teleporter(Teleporter teleportTo, Map map) throws MalformedURLException {
		super(map);
		this.teleportTo = teleportTo;
		setImage(IMAGE_NAME);
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
	
	@Override
    public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD, 
				teleportTo.getGridCoords().toString());
	}
	
	/**
	 * Sets the teleporter this teleporter is linked to.
	 * @param teleportTo The new teleporter this is linked to.
	 */
	public void setTeleportTo(Teleporter teleportTo) {
		this.teleportTo = teleportTo;
	}
	
	/**
	 * Gets the teleporter this teleporter is linked to.
	 * @return The teleporter this is teleporter is linked to.
	 */
	public Teleporter getTeleportTo() {
		return teleportTo;
	}
	
	/**
	 * Steps on this teleporter, causing a player that steps
	 * on this to teleport.
	 * @param steppedOnBy The moving object that stepped on this object.
	 * @param movedDir The direction the moving object moved to step on this.
	 */
	@Override
	public void stepOn(Moving steppedOnBy, Direction movedDir) {
		//If the door could be opened, open it.
		if(steppedOnBy instanceof Player) {
			Player player = (Player) steppedOnBy;
			if (teleportTo != null) {
				//Teleport them onto the second teleporter
				player.getGridCoords().setXY(teleportTo.getGridCoords());
				//Move them off in the same direction they entered
				player.move(movedDir);
			}
		}
	}
	
}
