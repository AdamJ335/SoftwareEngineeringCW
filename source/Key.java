import java.awt.Color;
import java.net.MalformedURLException;

/**
 * A collectable key that has a Color.
 * @author Tom Cordall
 * @version 1.1
 */
public class Key extends Collectable {
    
	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "key";
	//The image file name format.
    private static final String IMAGE_NAME_FORMAT = "%sKey.png";
    //The format for the meta information of this object.
    private static final String META_FORMAT = "%s,%s,%s";
    
    //The color of this key.
    private Color keyColor;
    
    /**
     * Constructs a key with a specific color value.
     * @param color The color of the key.
     * @throws MalformedURLException If the sprite is invalid.
     */
    public Key(Map map, Color color) throws MalformedURLException {
    	super(map);
        this.keyColor = color;
        setImage(String.format(IMAGE_NAME_FORMAT, ColourUtils.colourToString(keyColor)));
    }
    
    @Override
    public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD, ColourUtils.colourToString(keyColor));
	}
    
    /**
     * Returns the color of the key.
     * @return
     */
    public Color getKeyColor() {
    	return keyColor;
    }
    
    @Override
    public void collect(Player player) {
    	if (player != null) {
    		player.getInventory().addItem(this);
    	}
    } 
}
