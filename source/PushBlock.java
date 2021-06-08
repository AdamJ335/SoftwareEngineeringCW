import java.net.MalformedURLException;

/**
 * A block that can be pushed by a player over ground and can turn water
 * into ground(PushBlockInWater).
 * @author Josiah Richards
 * @version 1.1
 */
public class PushBlock extends Entity {
	//The name of this object in the meta information.
	public static final String META_KEYWORD = "pushblock";
	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s";
	//The image file name.
	private static final String IMAGE_NAME = "pushblock.png";
	
	/**
	 * Creates a PushBlock for a map.
	 * Doesn't add itself to the map.
	 * @param map The map this PushBlock is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public PushBlock(Map map) throws MalformedURLException {
		super(map);
		setImage(IMAGE_NAME);
	}

	@Override
	public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD);
	}
	
	@Override
	public void stepOn(Moving steppedOnBy, Direction fromDir) {
		//If stepped on by a player
		if (steppedOnBy != null && steppedOnBy instanceof Player) {
			boolean sendPlayerBack = true;
			//Potenital position is our position + direction player pushed us.
			Vector2 potentialPos = gridCoords.addXYCreateNew(fromDir);
			//Can't push over entities
			if (map.getEntitiesAt(potentialPos).size() == 0) {
				//If pushed over ground, just get pushed.
				if (map.getCellAt(potentialPos) instanceof Ground) {
					sendPlayerBack = false;
					gridCoords = potentialPos;
				//If pushed into water, transfom.
				} else if (map.getCellAt(potentialPos) instanceof Water) {
					sendPlayerBack = false;
					//Turn water into ground(PushBlockInWater) and discard self
					try {
						map.setCellAt(potentialPos, new PushBlockInWater(map));
					} catch (MalformedURLException e) {
					}
					map.removeEntity(this);
				}
			}
			//If this push block was blocked and couldnt be moved then
			//send the player back to where they came from.
			if (sendPlayerBack) {
				fromDir.reverse();
				steppedOnBy.move(fromDir);
			}
		}
	}
	
}
