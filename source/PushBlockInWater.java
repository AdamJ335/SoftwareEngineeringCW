import java.net.MalformedURLException;

/**
 * A ground cell but has the image of a push block in water.
 * This and ground could have been 1 class in retrospect but would have
 * required a bit more planning on diffentiating the 2 symbols and sprites. 
 * @author Josiah Richards
 * @version 1.0
 */
public class PushBlockInWater extends Ground {
		//The image file name.
		private static final String IMAGE_NAME = "PushBlockOnWater.png";
		//The symbol of this cell in a map file.
		public static final char SYMBOL = 'p';
		
		/**
		 * Creates a new PushBlockInWater for a map.
		 * @param map The map this is for.
		 * @throws MalformedURLException If the sprite is invalid.
		 */
		public PushBlockInWater(Map map) throws MalformedURLException {
			super(map);
			setImage(IMAGE_NAME);
		}
		
		@Override
		public char getSymbol() {
			return SYMBOL;
		}
}
