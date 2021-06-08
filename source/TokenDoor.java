import java.net.MalformedURLException;

/**
 * A door that requires a certain amount of tokens to open.
 * Only the player can open it and it doesn't 'consume' the tokens.
 * @author Ajaya Budhathoki and Josiah Richards
 * @version 2.0
 */
public class TokenDoor extends Door{

	//The format for the meta information of this object.
    public static final String META_FORMAT = "%s,%s,%s,%d";
	//The image file name.
	private static final String IMAGE_NAME = "tokenDoor.png";
	
	//The number of tokens required to open this door.
	private int numTokensRequired;
	
	/**
	 * Creates a TokenDoor that requires a given number of tokens
	 * to open for a map.
	 * Doesn't add itself to the map.
	 * @param numTokensRequired The number of tokens required to open this door.
	 * @param map This map this TokenDoor is for.
	 * @throws MalformedURLException If the sprite is invalid.
	 */
	public TokenDoor(int numTokensRequired, Map map) throws MalformedURLException {
		super(map);
		this.numTokensRequired = numTokensRequired;
		setImage(IMAGE_NAME);
	}
	
   @Override
   public String getMetaInfo() {
		return String.format(META_FORMAT, gridCoords.toString(), Door.META_KEYWORD,Token.META_KEYWORD, 
				numTokensRequired);
   }

	@Override
	public boolean tryToOpen(Player player) {
		if (player != null) {
			//If the player has enough tokens they can open this door.
			if (player.getInventory().getNumTokens() >= numTokensRequired) {
				return true;
			}
		}
		return false;
	}

}
