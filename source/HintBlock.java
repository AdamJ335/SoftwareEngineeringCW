import java.net.MalformedURLException;

/**
 * An entity that updates the hint in the level UI when stepped on
 * by a player.
 * In retrospect this probably should have been a Cell.
 * @author Josiah Richards
 * @version 1.1
 */
public class HintBlock extends Entity {
	//The name of this object in the meta information.
	public static final String META_KEYWORD = "hintblock";
	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s,%s";
	//The image file name.
	private static final String IMAGE_NAME = "hintblock.png";
	
	//The hint to display.
	private String hint;
	
	/**
	 * Creates a hint block for a map.
	 * Doesn't add the hint block to the map!
	 * @param map The map this hint block is for.
	 * @throws MalformedURLException 
	 */
	public HintBlock(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
	}

	/**
	 * Sets the hint that this hint block displays when stepped on.
	 * @param hint The new hint.
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	@Override
	public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD, hint);
	}
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction fromDir) {
		if (steppedOnBy != null && steppedOnBy instanceof Player) {
			//When stepped on by a player, update the hint text in UI.
			map.updateHintText(hint);
		}
	}
}
