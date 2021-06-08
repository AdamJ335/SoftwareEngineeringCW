import java.net.MalformedURLException;

/**
 * A class for the boot collectable which also sets up the two enumerations
 * fire and flipper.
 * @author Tom Cordall
 * @version 1.1
 */
public class Boot extends Collectable {
    
	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "boot";
	//The format for the meta information of this object.
    private static final String META_FORMAT = "%s,%s,%s";
    
    //The image file name format.
    private static final String IMAGE_NAME_FORMAT = "%sBoots.png";
	
    //Lowercase so you can do .toLowerCase() and get value
	public enum BootType{
        flipper("flipper"),
        fire("fire");
		
		private String typeName;
		private BootType(String typeName) {
			this.typeName = typeName;
		}
		
		@Override
		public String toString() {
			return typeName;
		}
    }
	
	//The type of boot this boot is.
	private BootType bootType;

    /**
     * Constructor for the boot collectable, takes a parameter of type BootType.
     * @param type The type of boot.
     * @throws MalformedURLException If the sprite for this boot is invalid.
     */
    public Boot(Map map, BootType type) throws MalformedURLException {
    	super(map);
        bootType = type;
        setImage(String.format(IMAGE_NAME_FORMAT, bootType.toString()));
    }
    
    @Override
    public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD, bootType.toString());
	}

	/**
	 * Get the boot type.
	 * @return The bootType.
	 */
	public BootType getBootType() {
    	return bootType;
    }
    
    @Override
    public void collect(Player player) {
    	if (player != null) {
    		player.getInventory().addItem(this);
    	}
    }
}
