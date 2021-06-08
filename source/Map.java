import java.net.MalformedURLException;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 * Stores the state of a map.
 * x, y coordinates go from the top left of the map to the bottom right of the map.
 * @author Ryan Smith, Josiah Richards
 * @version 2.1
 */
public class Map {
	
	//Meta keywords for reading/writing files.
	public static final String START_META_KEYWORD = "start";
	public static final String LAST_TIME_META_KEYWORD = "time";
	
	//The x/y size in pixels of each square on the grid.
	public static final int GRID_DIMENSION = 50;
	
	//-Map Contents-
	//The cells making up the basis of the map,
	//1 cell per grid coordinate.
	private Cell[][] grid;
	//The entities on the map(including the player!)
	//There can be multiple entities at 1 grid coordinate.
	private ArrayList<Entity> entities;
	//Quick access reference to the player
	private Player player;
	//Spawn location of the player.
	private Vector2 spawnLocation;
	//-
	
	//-Render info-
	//The camera to use to render the map.
	private Camera camera;
	//Amount of pixels to offset the rendering of the map by
	private Vector2 renderOffset;
	//-
	
	//The last time(in milliseconds) of current time on this map
	//(the time to start the timer at when continuing from a save)
	private long lastTime;
	//The starting inventory of the player
	private Inventory startInventory;
	
	//The level controller that's using this map.
	//(Used for calling win and lose)
	private LevelController levelController;
	
	/**
	 * Creates a map of a set width and height and uses a camera.
	 * @param width How many cells across the map should be.
	 * @param height How many cells down the map should be.
	 * @param camera The camera to use for rendering.
	 */
	public Map(int width, int height, Camera camera) {
		grid = new Cell[width][height];
		entities = new ArrayList<Entity>();
		spawnLocation = new Vector2();
		player = null;
		this.camera = camera;
		renderOffset = new Vector2();
		startInventory = new Inventory();
		levelController = null;
	}
	
	/**
	 * Creates a map of a set width and height with no camera assigned yet.
	 * @param width How many cells across the map should be.
	 * @param height How many cells down the map should be.
	 */
	public Map(int width, int height) {
		grid = new Cell[width][height];
		entities = new ArrayList<Entity>();
		spawnLocation = new Vector2();
		player = null;
		lastTime = 0L;
		camera = null;
		renderOffset = new Vector2();
		startInventory = new Inventory();
		levelController = null;
	}
	
	/**
	 * (re)spawns the player at the spawnLocation
	 * @throws MalformedURLException If the player sprite is invalid.
	 */
	public void respawnPlayer() throws MalformedURLException {
		//Remove any existing player
		if (player != null) {
			removeEntity(player);
		}
		//Spawn the new player
		Player newPlayer = new Player(this);
		newPlayer.getGridCoords().setXY(spawnLocation);
		newPlayer.setInventory(new Inventory(startInventory));
		addEntity(newPlayer);
		player = newPlayer;
		
		//Trigger any step ons where the player spawned.
		triggerStepOns(player, new Direction(0, 0));
	}
	
	/**
	 * Update the camera to centre on the player.
	 */
	public void centreCamOnPlayer() {
		camera.centreOnPoint(player.getGridCoords());
	}
	
	/**
	 * End the game as a win.
	 */
	public void win() {
		levelController.win();
	}
	
	/**
	 * End the game as a loss.
	 */
	public void lose() {
		levelController.lose();
	}
	
	/**
	 * Renders this map to a graphics context.
	 * @param graphicsContext The graphics context to render to.
	 */
	public void render(GraphicsContext graphicsContext) {
		centreCamOnPlayer();
		
		//Render all the cells that are in view of the camera.
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (grid[x][y] != null) {
					if (camera.isPointInView(grid[x][y].getGridCoords())) {
						grid[x][y].render(graphicsContext);
					}
				}
			}
		}
		
		//Render all the entities in view of the camera on top.
		for (Entity entity : entities) {
			if (camera.isPointInView(entity.getGridCoords())) {
				entity.render(graphicsContext);
			}
		}
	}
	
	/**
	 * Steps on all the entities and the cell at the position of steppedOnBy.
	 * @param steppedOnBy The moving entity that's stepping on the entities/cell.
	 * @param movedDir The direction the moving entity moved.
	 */
	public void triggerStepOns(Moving steppedOnBy, Direction movedDir) {
		//Get the cell and entities at the location of the moving entity.
		//Ignore steppedOnBy when checking.
		Cell steppedOnCell = getCellAt(steppedOnBy.getGridCoords());
		ArrayList<Entity> steppedOnEntities = getEntitiesAt(steppedOnBy.getGridCoords(), steppedOnBy);
		
		//Step on the cell if there was one.
		if (steppedOnCell != null) {
			steppedOnCell.stepOn(steppedOnBy, movedDir);
		}
		//Step on all the entities if any.
		if (steppedOnEntities != null) {
			for (Entity entity : steppedOnEntities) {
				entity.stepOn(steppedOnBy, movedDir);
			}
		}
	}
	
	/**
	 * Updates the hint text in the level controller.
	 * @param hintText The new hint text.
	 */
	public void updateHintText(String hintText) {
		levelController.setCurHintText(hintText);
	}
	
	/**
	 * Makes all the enemies on the map take their next move.
	 */
	public void updateEnemies() {
		//Loop through all entities
		for (Entity entity : entities) {
			//If the entity is an enemy
			if (entity instanceof Enemy) {
				Enemy enemy = (Enemy) entity;
				enemy.preformNextMove();
			}
		}
	}
	
	/**
	 * Adds an entity to the map.
	 * @param entity The entity to add.
	 */
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	/**
	 * Removes an entity from the map.
	 * @param entity The entity to remove.
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	/**
	 * Gets a list of all the entities on this map.
	 * @return A list of all the entities on this map.
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Gets a list of entities that are at the grid position (x, y).
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return A list of entities at the grid position (x, y).
	 */
	public ArrayList<Entity> getEntitiesAt(int x, int y) {
		return getEntitiesAt(new Vector2(x, y));
	}
	
	/**
	 * Gets a list of entities that are at a given grid position.
	 * @param position The grid position to check.
	 * @return A list of entities at the grid position.
	 */
	public ArrayList<Entity> getEntitiesAt(Vector2 position) {
		return getEntitiesAt(position, null);
	}

	/**
	 * Gets a list of entities that are at a given grid position apart
	 * from the entity, ignore.
	 * @param position The grid position to check.
	 * @param ignore The entity to exclude from the list.
	 * @return A list of entities at the grid position(excluding ignore).
	 */
	public ArrayList<Entity> getEntitiesAt(Vector2 position, Entity ignore) {
		ArrayList<Entity> foundEntities = new ArrayList<Entity>();
		//Loop through all entities
		for (Entity entity : entities) {
			//If we find an entity at the position that shouldn't be ignored,
			//add it to our found list.
			if (entity != ignore) {
				if (entity.getGridCoords().equals(position)) {
					foundEntities.add(entity);
				}
			}
		}
		return foundEntities;
	}

	/**
	 * Gets the cell at a given grid position.
	 * @param position The grid position.
	 * @return The cell at the position, null if out of bounds.
	 */
	public Cell getCellAt(Vector2 position) {
		return getCellAt(position.getX(), position.getY());
	}
	/**
	 * Gets the cell at a given grid position.
	 * @param x The x grid coordinate.
	 * @param y The y grid coordinate.
	 * @return The cell at the position, null if out of bounds.
	 */
	public Cell getCellAt(int x, int y) {
		if (grid != null) {
			if(grid.length > x) {
				if (grid[x].length > y) {
					return grid[x][y];
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the camera used to render this map.
	 * @return The camera used to render this map.
	 */
	public Camera getCamera() {
		return camera;
	}
	
	/**
	 * Gets the camera used to render this map.
	 * @param camera The new camera to use to render this map.
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	/**
	 * Sets the last time for this map.
	 * @param lastTime The last time for this map.
	 */
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	
	/**
	 * Gets the last time for this map.
	 * @return lastTime The last time for this map.
	 */
	public long getLastTime() {
		return lastTime;
	}
	
	/**
	 * Gets the grid coordinates the player spawns at.
	 * @return The grid coordinates the player spawns at.
	 */
	public Vector2 getSpawnLocation() {
		return spawnLocation;
	}
	
	/**
	 * Sets the grid coordinates the player should spawn at.
	 * @param spawnLocation The new grid coordinates the player should spawn at.
	 */
	public void setSpawnLocation(Vector2 spawnLocation) {
		this.spawnLocation = spawnLocation;
	}
	
	/**
	 * Sets the level controller that will be controlling this map.
	 * @param levelController The new level controller.
	 */
	public void setLevelController(LevelController levelController) {
		this.levelController = levelController;
	}
	
	/**
	 * Gets the starting inventory for the player.
	 * @return The starting inventory for the player.
	 */
	public Inventory getStartInventory() {
		return startInventory;
	}
	
	/**
	 * Sets the starting inventory for the player.
	 * @param startInventory The new starting inventory for the player.
	 */
	public void setStartInventory(Inventory startInventory) {
		this.startInventory = startInventory;
	}
	
	/**
	 * Gets the pixels to offset the rendering of this map by.
	 * @return The pixels to offset the rendering of this map by.
	 */
	public Vector2 getRenderOffset() {
		return renderOffset;
	}
	
	/**
	 * Gets the player on this map.
	 * @return The player on this map.
	 */
	public Player getPlayer() {
		return player;
	}
	
	public Vector2 getGridDimensions() {
		return new Vector2(grid.length, grid[0].length);
	}
	
	/**
	 * Sets the amount of pixels to offset the rendering of this map by.
	 * @param renderOffset The amount of pixels to offset the rendering of this map by.
	 */
	public void setRenderOffset(Vector2 renderOffset) {
		this.renderOffset = renderOffset;
	}
	
	/**
	 * Sets the cell at a given grid position.
	 * @param position The grid position.
	 * @param cell The cell to put at the position.
	 */
	public void setCellAt(Vector2 position, Cell cell) {
		setCellAt(position.getX(), position.getY(), cell);
	}
	
	/**
	 * Sets the cell at a given grid position.
	 * @param x The x grid coordinate.
	 * @param y The y grid coordinate.
	 * @param cell The cell to put at the position.
	 */
	public void setCellAt(int x, int y, Cell cell) {
		if (grid != null) {
			if(grid.length > x) {
				if (grid[x].length > y) {
					cell.gridCoords = new Vector2(x, y);
					grid[x][y] = cell;
				}
			}
		}
	}
}