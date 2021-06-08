import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

/**
 * Class that parses a text file set out in the layout in our DesignDoc to create a map
 * @author Ryan Smith, Josiah Richards
 * @version 2.0
 */
public class MapReader {

	//The character separating data on the same line in map files
	private static final String DATA_DELIMITER = ",";

	//Error messages
	private static final String DIMENSIONS_ERROR_MSG = "Failed to read map dimensions.";
	private static final String CELLS_ERROR_MSG = "Failed to read map cells.";
	private static final String METADATA_ERROR_MSG = "Failed to read map metadata. Error parsing %s";
	
	
	/**
	 * Parses a cell character and adds the cell to the given map.
	 * @param cellCharacter The cell character to parse.
	 * @param map The map to add the cell that we've parsed to.
	 * @param x The x coordinate of the cell on the map.
	 * @param y The y coordinate of the cell on the map.
	 * @throws ParsingException If the character was unrecognised.
	 */
	private static void parseCellOnToMap(char cellCharacter, int x, int y, Map map) throws ParsingException {
		try {
			//Create the appropriate cell on the map for the given character.
			switch (cellCharacter) {
				case Goal.SYMBOL:
					map.setCellAt(x, y, new Goal(x, y, map));
					break;
				case Ground.SYMBOL:
					map.setCellAt(x, y, new Ground(x, y, map));
					break;
				case PushBlockInWater.SYMBOL:
					map.setCellAt(x, y, new PushBlockInWater(map));
					break;
				case Teleporter.SYMBOL:
					map.setCellAt(x, y, new Teleporter(x, y, map));
					break;
				case Door.SYMBOL:
					//Ignore and create wall placeholder as it will be created specially in the meta data.
					//(as theres 2 types coloured and token doors)
					map.setCellAt(x, y, new Wall(x, y, map));
					break;
				case Water.SYMBOL:
					map.setCellAt(x, y, new Water(x, y, map));
					break;
				case Fire.SYMBOL:
					map.setCellAt(x, y, new Fire(x, y, map));
					break;
				case Wall.SYMBOL:
					map.setCellAt(x, y, new Wall(x, y, map));
					break;
				default:
					throw new ParsingException(CELLS_ERROR_MSG);
			}
		}
		catch (MalformedURLException e) {
			throw new ParsingException(CELLS_ERROR_MSG);
		}
	}
	
	/**
	 * 
	 * @param metadataStr The single line of metadata to parse.
	 * @param map The map the metadata is for.
	 * @return The new entity that was created, if any.
	 * @throws ParsingException If this line of metadata couldn't be parsed.
	 */
	private static Entity parseMapMetadataStr(String metadataStr, Map map) throws ParsingException {
		String[] metadataArgs = metadataStr.split(DATA_DELIMITER);
		//If we create a new object, store it here to return
		Entity newEntity = null;
		try {
			if (metadataArgs.length > 2) {
				int curArgsBase = 0;
				
				//All metadata starts with coords
				//(can use coords 0,0 for extra non grid related data in the future)
				Vector2 coords = new Vector2(Integer.parseInt(metadataArgs[curArgsBase]), 
	    					             Integer.parseInt(metadataArgs[curArgsBase + 1]));

	    		curArgsBase = 2;
	    		switch (metadataArgs[curArgsBase].toLowerCase()) {
	    			case Map.START_META_KEYWORD: //Set Spawn location
	    				map.setSpawnLocation(coords);
	    				break;
	    			case Token.META_KEYWORD: //Spawn token entity
	    				int tokenAmount = Integer.parseInt(metadataArgs[curArgsBase + 1]);
	    				
	    				Token newToken = new Token(map, tokenAmount);
	    				newToken.getGridCoords().setXY(coords);
	    				newEntity = newToken;
	    				break;
	    			case Teleporter.META_KEYWORD: //Link 2 teleporters
	    				//Coords of 2nd teleporter
	    				Vector2 tp2Coords = new Vector2(Integer.parseInt(metadataArgs[curArgsBase + 1]), 
	    	    									  Integer.parseInt(metadataArgs[curArgsBase + 2]));
	
	    				//Check there is a teleport at both coords and the 
	    				//tp2 coords
	    	    		if (map.getCellAt(coords) != null && map.getCellAt(coords) instanceof Teleporter) {
	    	    			if (map.getCellAt(tp2Coords) != null && map.getCellAt(tp2Coords) instanceof Teleporter) {
		    	    			Teleporter tp1 = (Teleporter) map.getCellAt(coords);
		    	    			tp1.setTeleportTo((Teleporter) map.getCellAt(tp2Coords));
		    	    		} else {
		    	    			throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
		    	    		}
	    	    		} else {
	    	    			throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
	    	    		}
	    	    		break;
	    			case Door.META_KEYWORD: //Create door cell
    					String doorTypeStr = metadataArgs[curArgsBase + 1].toLowerCase();
    					
    					if (doorTypeStr.equals(Token.META_KEYWORD)) { //Token door
    						int tokensRequired = Integer.parseInt(metadataArgs[curArgsBase + 2]);
    						TokenDoor newDoor = new TokenDoor(tokensRequired, map);
    						map.setCellAt(coords, newDoor);
    						
    					} else { //Coloured door
    						Color colour = ColourUtils.stringToColour(doorTypeStr);
	    					if (colour != null) {
	    						ColouredDoor newDoor = new ColouredDoor(colour, map);
	    						map.setCellAt(coords, newDoor);
	    					} else {
		    					throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
		    				}
    					}
	    				break;
	    			case Key.META_KEYWORD: //Spawn key entity
    					Color colour = ColourUtils.stringToColour(metadataArgs[curArgsBase + 1]);
    					if (colour != null) {
    						Key newKey = new Key(map, colour);
    						newKey.getGridCoords().setXY(coords);
    						newEntity = newKey;
    					} else {
	    					throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
	    				}
	    				break;
	    			case Map.LAST_TIME_META_KEYWORD: //Sets the last time for this map
	    				long lastTime;
	    	    		lastTime = Long.parseLong(metadataArgs[curArgsBase + 1]);
	    	    		map.setLastTime(lastTime);
	    				break;
	    			case Boot.META_KEYWORD: //Spawn boot entity
    					String bootTypeString = metadataArgs[curArgsBase + 1].toLowerCase();
    					Boot.BootType bootType = Boot.BootType.valueOf(bootTypeString);
    					
    					Boot newBoot = new Boot(map, bootType);
    					newBoot.getGridCoords().setXY(coords);
    					newEntity = newBoot;
	    				break;
	    			case PushBlock.META_KEYWORD:
	    				PushBlock newPushBlock = new PushBlock(map);
	    				newPushBlock.getGridCoords().setXY(coords);
    					newEntity = newPushBlock;
    					break;
	    			case HintBlock.META_KEYWORD:
	    				HintBlock newHintBlock = new HintBlock(map);
	    				newHintBlock.getGridCoords().setXY(coords);
	    				newHintBlock.setHint(metadataArgs[curArgsBase + 1]);
    					newEntity = newHintBlock;
    					break;
	    			case Inventory.META_ITEM_KEYWORD: //Add item to starting inventory
	    				//Get the item meta information on its own by taking away 0,0,invitem,
	    				int itemMetaLength = String.format(Inventory.META_ITEM_FORMAT, coords.toString(),
	    										Inventory.META_ITEM_KEYWORD,"").length();
	    				String itemMeta = metadataStr.substring(itemMetaLength);
	    				Entity itemEntity = parseMapMetadataStr(itemMeta, map);
	    				if (itemEntity != null && itemEntity instanceof Collectable) {
	    					map.getStartInventory().addItem((Collectable) itemEntity);
	    				}
	    				break;
	    			case Inventory.META_TOKEN_KEYWORD: //Set the num tokens in the starting inventory
	    				int numTokens = Integer.parseInt(metadataArgs[curArgsBase + 1]);
	    				map.getStartInventory().setNumTokens(numTokens);
	    				break;
	    			case Enemy.META_KEYWORD: //Spawn enemy entity
	    					Direction facingDir = null;
	    					//Check if a facing direction is given,
	    					//Otherwise default to facing right
	    					if (metadataArgs.length > curArgsBase + 2) {
	    						facingDir = Direction.createFromString(metadataArgs[curArgsBase + 2].toLowerCase());
	    					} else {
	    						facingDir = new Direction(Direction.DIR_RIGHT);
	    					}
	    					if (facingDir != null) {
	    						//Get the type of enemy
	    						switch (metadataArgs[curArgsBase + 1].toLowerCase()) {
	    							case StraightLineEnemy.META_MOVEMENT_KEYWORD://Straight line enemy
	    								StraightLineEnemy lineEnemy = new StraightLineEnemy(0, 0, map, facingDir);
	    								lineEnemy.getGridCoords().setXY(coords);
	    								newEntity = lineEnemy;
	    								break;
	    							case WallFollowEnemy.META_MOVEMENT_KEYWORD://Wall follow enemy
	    								boolean followRight = false;
	    								//Check if the side to follow left or right is specified, 
	    								//defaulting to left if not
	    								if (metadataArgs.length > curArgsBase + 3) {
	    									if (metadataArgs[curArgsBase + 3].toLowerCase().equals(Direction.RIGHT_WORD)) {
	    										followRight = true;
	    									} else {
	    										followRight = false;
	    									}
	    								}
	    								WallFollowEnemy followEnemy = new WallFollowEnemy(followRight, 0, 0, map, facingDir);
	    								followEnemy.getGridCoords().setXY(coords);
	    								newEntity = followEnemy;
	    								break;
	    							case SmartTargetEnemy.META_MOVEMENT_KEYWORD:
	    								SmartTargetEnemy smartEnemy = new SmartTargetEnemy(0, 0, map, facingDir);
	    								smartEnemy.getGridCoords().setXY(coords);
	    								newEntity = smartEnemy;
	    			    				break;
	    							case DumbTargetEnemy.META_MOVEMENT_KEYWORD:
	    								DumbTargetEnemy dumbEnemy = new DumbTargetEnemy(0, 0, map, facingDir);
	    								dumbEnemy.getGridCoords().setXY(coords);
	    								newEntity = dumbEnemy;
	    								break;
	    							default:
	    								throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
	    						}
	    						break;
	    					}else {
	    						throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
	    					}
				default:
	    				throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
	    		}
	    		
			}
		} catch (Exception e) {
			throw new ParsingException(String.format(METADATA_ERROR_MSG, metadataStr));
		}
		
		return newEntity;
	}
	
	/**
	 * Parses a map file from a given scanner and creates
	 * a new Map object set up with the information read.
	 * @param in The scanner containing the map file data.
	 * @return A new Map object set up with the information read.
	 * @throws ParsingException If the map data couldn't be parsed.
	 */
    public static Map readMapFile(Scanner in) throws ParsingException {
        
    	//Check there is a line for the map dimensions
    	if (in.hasNextLine()) {
    		
    		//Parse the map dimensions
    		String[] dimStrs = in.nextLine().split(DATA_DELIMITER);
    		//Check there are exactly 2 arguments(x and y)
    		if (dimStrs.length != 2) {
    			throw new ParsingException(DIMENSIONS_ERROR_MSG);
    		}
    		int xDim = 0;
    		int yDim = 0;
    		try {
	    		xDim = Integer.parseInt(dimStrs[0]);
	    		yDim = Integer.parseInt(dimStrs[1]);
    		} catch (NumberFormatException e) {
    			throw new ParsingException(DIMENSIONS_ERROR_MSG);
    		}
    		//Create a new map with the read dimensions.
    		Map newMap = new Map(xDim, yDim);
    		
    		//Parse the cells.
    		for (int y = 0; y < yDim; y++) {
    			if (in.hasNextLine()) {
    				//Read a line of cells
    				String cellLineStr = in.nextLine().toLowerCase();
    				for (int x = 0; x < cellLineStr.length(); x++) {
    					parseCellOnToMap(cellLineStr.charAt(x), x, y, newMap);
    				}
    				
    			} else { //There's a row of cells missing from the file
    				throw new ParsingException(CELLS_ERROR_MSG);
    			}
    		}
    		
    		//Parse the metadata/entities
    		while (in.hasNextLine()) {
    			String metadataStr = in.nextLine();
    			Entity newEntity = parseMapMetadataStr(metadataStr, newMap);
    			if (newEntity != null) {
    				newMap.addEntity(newEntity);
    			}
    		}
    		
    		in.close();
    		
    		//We've reached here without throwing an error,
    		//return our new map!
    		return newMap;
    		
    	} else {
    		in.close();
    		throw new ParsingException(DIMENSIONS_ERROR_MSG);
    	}
    }
    
    /**
     * Parses a map file from a given scanner and creates
	 * a new Map object set up with the information read.
     * @param filename The full path to the map file.
     * @return The newly created Map object set up with the information read.
     * @throws FileNotFoundException If the map file couldn't be found.
     * @throws ParsingException If the map file couldn't be parsed.
     */
    public static Map readMapFile(String filename) throws FileNotFoundException, ParsingException {
		
		File file = new File (filename);
		Scanner in = new Scanner(file); 
		
		return readMapFile(in);
	}


}