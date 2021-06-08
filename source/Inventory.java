import java.util.ArrayList;
import java.awt.Color;

/**
 * This class stores the methods and variables used for a player's inventory.
 * @author Tom Cordall and Josiah Richards
 * @version 2.0
 */
public class Inventory {
	
	//The keyword used for this object in the meta information.
	public static final String META_ITEM_KEYWORD = "invitem";
	public static final String META_TOKEN_KEYWORD = "invtoken";
	
	//The format for the meta information of this object.
    public static final String META_ITEM_FORMAT = "%s,%s,%s";
    private static final String META_TOKEN_FORMAT = "%s,%s,%d";
	
    //The collectables currently stored in this inventory.
    private ArrayList<Collectable> items;
    //The number of tokens stored in this inventory.
    private int numTokens;
    
    /**
     * Creates an empty Inventory.
     */
    public Inventory() {
    	items = new ArrayList<Collectable>();
    	numTokens = 0;
    }
    
    /**
     * Clones a reference inventory.
     * @param referenceInventory The inventory to clone.
     */
    public Inventory(Inventory referenceInventory) {
    	items = new ArrayList<Collectable>();
    	//Clone items list.
    	for (Collectable item : referenceInventory.getItems()) {
    		items.add(item);
    	}
    	//Clone num tokens.
    	numTokens =	referenceInventory.numTokens;
    }

	/**
	 * This method gets the information about each item within the items list.
	 * @return A string.
	 */
	public String getMetaInfo() {
    	String metaInfo = "";
    	//Add items
    	for (Collectable item : items) {
    		if (item != null) {
    			metaInfo += String.format(META_ITEM_FORMAT, item.getGridCoords().toString(),
    								META_ITEM_KEYWORD, item.getMetaInfo());
    			metaInfo += GlobalInfo.NEW_LINE;
    		}
    	}
    	//Add numTokens
    	metaInfo += String.format(META_TOKEN_FORMAT, "0,0", META_TOKEN_KEYWORD, numTokens);
    	return metaInfo;
    }

	/**
	 * Checks that the player has a boot of a specific type.
	 * @param bootType The bootType to be found.
	 * @return A boolean value.
	 */
	public boolean hasBoots(Boot.BootType bootType){
		//Loop through the items in this inventory.
    	for (Collectable item : items)
    	{
    		//If the item is a boot and is the type we're looking for,
    		//return true
    		if (item instanceof Boot) {
    			Boot boot = (Boot) item;
    			if(boot.getBootType() == bootType) {
    				return true;
    			}
    		}
    	}
    	return false;
    }


	/**
	 * Searches the list for a key of a specific colour
	 * if the player has it then it is removed.
	 * @param color The specific color value.
	 * @return A boolean value.
	 */
	public boolean hasAndConsumeKey(Color color){
		//Loop through the items in this inventory.
    	for (Collectable item : items)
    	{
    		//If the item is a key, check it's of the correct color.
    		if (item instanceof Key) {
    			Key key = (Key) item;
    			if(key.getKeyColor() == color) {
    				//'Consume' the key and return true.
    				items.remove(item);
    				return true;
    			}
    		}
    	}
    	return false;
    }

	/**
	 * Gets the list of collectables stored in this inventory.
	 * @return The list of collectables stored in this inventory.
	 */
    public ArrayList<Collectable> getItems(){
       return items;
    }
    
    /**
     * Clears the list of collectables stored in this inventory.
     */
    public void clearItems(){
        items.clear();
    }

    /**
     * Adds a collectable to the items in this inventory.
     * @param item The collectable to add.
     */
    public void addItem(Collectable item){
    	if (!(item instanceof Token)) {
    		items.add(item);
    	}
    }

    /**
     * Adds an amount of tokens to this inventory.
     * @param amount The amount of tokens to add.
     */
    public void addTokens(int amount) {
    	numTokens += amount;
    } 
    
    /**
     * Gets the number of tokens stored in this inventory.
     * @return The number of tokens stored in this inventory.
     */
    public int getNumTokens() {
    	return numTokens;
    }
    
    /**
     * Sets the number of tokens stored in this inventory.
     * @param numTokens The new number of tokens stored in this inventory.
     */
    public void setNumTokens(int numTokens){
        this.numTokens = numTokens;
    }
}
