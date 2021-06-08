import java.net.MalformedURLException;

/**
 * A collectable pile of tokens that increases the number
 * of tokens in a player's inventory when collected.
 * @author Tom Cordall
 * @version 1.1
 */
public class Token extends Collectable {

	//The keyword used for this object in the meta information.
	public static final String META_KEYWORD = "token";
	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s,%d";
	//The image file name.
	private static final String IMAGE_NAME = "token.png";
	
	//The number of tokens in this pile.
	private int amountInPile;

	/**
	 * Constructs a token pile consisting of numTokens tokens.
	 * @param map The map the token is on.
	 * @param amountInPile The number of tokens in the pile.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public Token(Map map, int amountInPile) throws MalformedURLException {
		super(map);
		this.amountInPile = amountInPile;
		setImage(IMAGE_NAME); 
	}
   
	/**
	 * Gets the number of tokens in this pile.
	 * @return The number of tokens in this pile.
	 */
	public int getAmountInPile() {
			return amountInPile;
	}
   
	@Override
	public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), META_KEYWORD, 
								amountInPile);
	}

	@Override
	public void collect(Player player) {
		if (player != null) {
			player.getInventory().addTokens(amountInPile);
		}
	}
}
