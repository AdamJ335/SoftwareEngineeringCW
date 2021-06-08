import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 * Controls the level scene.
 * @author Josiah Richards
 * @version 2.0
 */
public class LevelController extends Controller {

	//Sounds to play when the player wins/loses
	private static final String WIN_SOUND = "SantaWins.mp3";
	private static final String LOSE_SOUND = "classic_hurt.mp3";
	//The maximum number of inventory items the UI can handle.
	private static final int MAX_INVENTORY_DISPLAY_SIZE = 9;
	//The format of the fx css ids of the inventory images where
	//%d is 0(inclusive) to MAX_INVENTORY_DISPLAY_SIZE(exclusive)
	private static final String INV_IMAGE__ID_FORMAT = "#imgInv%d";
	//The prefix for displaying hints where %s is the hint.
	private static final String HINT_TEXT_FORMAT = "HINT: %s";
	//The camera to use when rending the map.
	private static final Camera MAP_CAMERA = new Camera(new Vector2(9,9));
	
	//FXML UI elements
	@FXML Button btnBack;
	@FXML Label lblTokensCollected;
	@FXML Label lblTimeTaken;
	@FXML Label lblHint;
	@FXML Canvas gameCanvas;
	
	//The current map instance.
	private Map mapMain;
	//The current graphics context we're rendering the map onto.
	private GraphicsContext graphicsContext;
	//The total number of tokens that can be collected in the map.
	private int numTokens;
	//Stopwatch to keep track of how long the level has taken.
	private Stopwatch timer;
	//The image views for displaying the inventory items.
	private ImageView[] inventoryImages;
	//The current hint text.
	private String curHintText;
	
	@Override
	public void initialize() {
	}

	@Override
	public void manualInitialize() {
		curHintText = "";
		
		//Get the inventory views based on their fx css id
		//and put them in the inventory images array for easy access.
		inventoryImages = new ImageView[MAX_INVENTORY_DISPLAY_SIZE];
		for (int i = 0; i < MAX_INVENTORY_DISPLAY_SIZE; i++) {
			try {
				String viewID = String.format(INV_IMAGE__ID_FORMAT, i);
				inventoryImages[i] = (ImageView) main.getCurRootPane().lookup(viewID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		graphicsContext = gameCanvas.getGraphicsContext2D();
		
		//Back button set up
		btnBack.setOnAction(e -> {
			handleBtnBackAction();
		});
		
		//Set up keyboard input handler.
		main.getCurScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
		restart();
	}
	
	/**
	 * Changes the current hint text.
	 * @param curHintText The new hunt text.
	 */
	public void setCurHintText(String curHintText) {
		this.curHintText = curHintText;
	}
	
	/**
	 * Tries to restarts the level, if the map
	 * couldn't be loaded it returns to the select level scene.
	 */
	public void restart() {
		mapMain = null;
		//Reload the map
		if (loadMap()) {
			//Recount the total number of tokens for this map.
			updateNumTokens();
			//Set the camera for this map.
			mapMain.setCamera(MAP_CAMERA);
			//Spawn the player
			try {
				mapMain.respawnPlayer();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			//Create and start the timer 
			timer = new Stopwatch(mapMain.getLastTime());
			//Redraw the UI.
			redraw();
		}else {
			main.switchToScene(Main.SceneType.SelectLevel);
		}
		
	}
	
	/**
	 * Loads the selected map, continuing with a save if the save
	 * exists and the option is set.
	 * @return If the map loaded successfully or not.
	 */
	public boolean loadMap() {
		//If we succesfully loaded the map or not.
		boolean loadedLevel = false;
		
		//If the save file doesn't exist for this map and profile,
		//disable continuing from a save file.
		if (main.getGlobalInfo().getContinueLevel()) {
			if (!FileManager.fileExists(FileManager.getSavedMapFile(
							main.getGlobalInfo().getSelectedProfileID(), 
							main.getGlobalInfo().getCurrentLevelID()))) {
				main.getGlobalInfo().setContinueLevel(false);
			}
		}
		
		//If continuing from save file
		if (main.getGlobalInfo().getContinueLevel()) { 
			//Set continue to false so when the level restarts it's from the beginning again.
			main.getGlobalInfo().setContinueLevel(false);
			//Try and load the save file.
			try {
				loadSave(main.getGlobalInfo().getSelectedProfileID(), main.getGlobalInfo().getCurrentLevelID());
				loadedLevel = true;
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		} else {
			//Otherwise load the level from the start.
			try {
				loadLevel(main.getGlobalInfo().getCurrentLevelID());
				loadedLevel = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//Update the map's level controller.
		if(loadedLevel) {
			mapMain.setLevelController(this);
		}
		return loadedLevel;
	}
	
	/**
	 * Loads a map with a given ID into mapMain.
	 * @param mapID The map ID to load.
	 * @throws FileNotFoundException If the map file couldn't be found.
	 * @throws ParsingException If the map file couldn't be parsed.
	 */
	public void loadLevel(int mapID) throws FileNotFoundException, ParsingException {
		mapMain = MapReader.readMapFile(FileManager.getMapLayoutFile(mapID));
	}
	
	/**
	 * Loads the save for a given map and profile ID into
	 * map main.
	 * @param profileID The profile ID who's save we want to load.
	 * @param mapID The map ID of the map we want to load.
	 * @throws FileNotFoundException If the save file couldn't be found.
	 * @throws ParsingException If the save file couldn't be parsed.
	 */
	public void loadSave(int profileID, int mapID) throws FileNotFoundException, ParsingException {
		mapMain = MapReader.readMapFile(FileManager.getSavedMapFile(profileID, mapID));
	}
	
	/**
	 * Recounts the total number of tokens available in the map
	 * (and already in the players inventory).
	 */
	private void updateNumTokens() {
		//Reset the token count.
		numTokens = 0;
		//Check for all the tokens in the map and add 
		//the amount in their piles.
		for (Entity entity : mapMain.getEntities()) {
			if (entity instanceof Token) {
				numTokens += ((Token) entity).getAmountInPile();
			}
		}
		//Add any tokens already in the player's inventory.
		if(mapMain.getStartInventory() != null){
			numTokens += mapMain.getStartInventory().getNumTokens();
		}
	}
	
	/**
	 * Handles when the back button is clicked,
	 * saving the game and going back to the level menu.
	 */
	private void handleBtnBackAction() {
		//Update the last time to our current time on the map
		mapMain.setLastTime(timer.getElapsedTime());
		//Save the map
		try {
			MapWriter.saveMapToFile(FileManager.getSavedMapFile(main.getGlobalInfo().getSelectedProfileID(), 
								main.getGlobalInfo().getCurrentLevelID()), mapMain);
		}catch (Exception e){
			e.printStackTrace();
		}
		//Switch to the level menu
		main.switchToScene(Main.SceneType.LevelMenu);
	}
	
	/**
	 * Redraws the UI.
	 */
	public void redraw(){
		//Clear the canvas.
		graphicsContext.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
		
		//Update hint label.
		if (curHintText != null && curHintText != "") {
			lblHint.setText(String.format(HINT_TEXT_FORMAT, curHintText));
		} else {
			lblHint.setText("");
		}
		
		//Render the map and inventory.
		if (mapMain != null) {
			renderMap();
			renderStats();
		}
	}
	
	/**
	 * Displays all the items in the players inventory, the current
	 * tokens collected and the current time taken on the UI.
	 */
	public void renderStats() {
		if (mapMain != null) {
			//Get the players inventory
			Inventory inventory = mapMain.getPlayer().getInventory();
			
			//Update the tokens collected out of the total tokens
			lblTokensCollected.setText(inventory.getNumTokens() + "/" + numTokens);
			//Update the current time taken
			lblTimeTaken.setText(TimerFormatter.formatMilliseconds(timer.getElapsedTime()));
			
			//Display each inventory item in one of the inventory images
			//image views.
			int curInventIndex = 0;
			for (Collectable item : inventory.getItems()) {
				if(curInventIndex < MAX_INVENTORY_DISPLAY_SIZE && item != null) {
					inventoryImages[curInventIndex].setImage(item.myImage);
					curInventIndex++;
				}
			}
			
			//Clear the rest of the inventory images
			for (int i = curInventIndex; i < MAX_INVENTORY_DISPLAY_SIZE; i++) {
				inventoryImages[i].setImage(null);
			}
		}
	}
	
	/**
	 * Renders the mapMain
	 */
	public void renderMap(){
		//Centre map
		double width = graphicsContext.getCanvas().getWidth();
		double height = graphicsContext.getCanvas().getHeight();
		int xOffset = (int) Math.round((width / 2d ) - 
								((MAP_CAMERA.getSize().getX() / 2d) * Map.GRID_DIMENSION));
		int yOffset = (int) Math.round((height / 2d ) - 
								((MAP_CAMERA.getSize().getY() / 2d) * Map.GRID_DIMENSION));
		mapMain.setRenderOffset(new Vector2(xOffset, yOffset));
		
		//Tell the map to render itself on our graphics context.
		mapMain.render(graphicsContext);
	}
	
	/**
	 * Handle the player losing, play a sound
	 * and restart the map.
	 */
	public void lose() {
		try {
			GlobalInfo.playSound(LOSE_SOUND);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		restart();
	}
	
	/**
	 * Handle the player winning.
	 * Updates the highscores and highest level the player has won
	 * before sending them to the next level.
	 */
	public void win() {
		long score = timer.getElapsedTime();
		mapMain = null;
		//Save high score
		try{
			MapInfo mapInfo = MapInfo.loadMapInfo(main.getGlobalInfo().getCurrentLevelID());
			mapInfo.tryAddScore(main.getGlobalInfo().getSelectedProfileID(), score);
			mapInfo.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Update highest level
		try {
			Profile loadedProfile = ProfileManager.loadProfile(main.getGlobalInfo().getSelectedProfileID());
			if (loadedProfile.getHighestLevel() < main.getGlobalInfo().getCurrentLevelID()) {
				loadedProfile.setHighestLevel(main.getGlobalInfo().getCurrentLevelID());
				ProfileManager.saveProfile(loadedProfile);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Delete any map save file as the level has been completed.
		String saveLoc = FileManager.getSavedMapFile(
				main.getGlobalInfo().getSelectedProfileID(), 
				main.getGlobalInfo().getCurrentLevelID());
		File saveFile = new File(saveLoc);
		if(saveFile.exists()) {
			saveFile.delete();
		}
		//Play the win sound.
		try {
			GlobalInfo.playSound(WIN_SOUND);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//Send them to the next level
		main.getGlobalInfo().setCurrentLevelID(main.getGlobalInfo().getCurrentLevelID() + 1);
		restart();
	}
	
	/**
	 * Handle a key press, moving the player.
	 * @param event The key pressed event.
	 */
	public void processKeyEvent(KeyEvent event) {
		//Reset the hint text
		curHintText = "";
		if (mapMain != null) {
			timer.getElapsedTime();
			timer.start();
			
			//Move the player based on the key pressed.
			switch (event.getCode()) {
			    case RIGHT:
			    	mapMain.getPlayer().move(new Direction(Direction.DIR_RIGHT));
		        	break;
			    case LEFT:
			    	mapMain.getPlayer().move(new Direction(Direction.DIR_LEFT));
		        	break;	
			    case UP:
			    	mapMain.getPlayer().move(new Direction(Direction.DIR_UP));
		        	break;	
			    case DOWN:
			    	mapMain.getPlayer().move(new Direction(Direction.DIR_DOWN));
		        	break;	
		        default:
		        	// Do nothing
		        	break;
			}
			if (mapMain != null) {
				mapMain.updateEnemies();
				// Redraw game as the player may have moved.
				redraw();
			}
		}
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
	}
	
}
