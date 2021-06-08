import java.net.MalformedURLException;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class represents anything that is on the map's grid
 * and can hence be stepped on.
 * @author Josiah Richards
 * @version 1.6
 */
public class OnGrid extends ImageBox {
	//The coordinate of this object on this grid.
	protected Vector2 gridCoords;
	//The map(grid) this object is on
	protected Map map;
	
	/**
	 * Creates an OnGrid object with a given x and y position
	 * and an image on the map.
	 * @param gridX The x position on the grid.
	 * @param gridY The y position on the grid.
	 * @param Map The map(grid) this object is on.
	 * @param imageName The name of the image file(with extension and not the full path).
	 * @throws MalformedURLException If the image couldn't be found.
	 */
	OnGrid(int gridX, int gridY, Map map, String imageName) throws MalformedURLException {
		super(imageName);
		this.map = map;
		gridCoords = new Vector2(gridX, gridY);
	}
	
	/**
	 * Creates an OnGrid object with a given x and y position
	 * on the map.
	 * @param gridX The x position on the grid.
	 * @param gridY The y position on the grid.
	 * @param Map The map(grid) this object is on.
	 */
	OnGrid(int gridX, int gridY, Map map) {
		super();
		this.map = map;
		gridCoords = new Vector2(gridX, gridY);
	}
	
	/**
	 * Creates an OnGrid object at a default position of (0, 0)
	 * and an image on the map.
	 * @param Map The map(grid) this object is on.
	 * @param imageName The name of the image file(with extension and not the full path).
	 * @throws MalformedURLException If the image couldn't be found.
	 */
	OnGrid(Map map, String imageName) throws MalformedURLException {
		super(imageName);
		this.map = map;
		gridCoords = new Vector2(0, 0);
	}
	
	/**
	 * Creates an OnGrid object at a default position of (0, 0) on the map.
	 * @param Map The map(grid) this object is on.
	 */
	OnGrid(Map map) {
		super();
		this.map = map;
		gridCoords = new Vector2(0, 0);
	}
	
	/**
	 * Steps on this object to cause a reaction.
	 * The default behaviour is to do nothing in reaction.
	 * @param steppedOnBy The moving object that stepped on this object.
	 * @param movedDir The direction the moving object moved to step on this.
	 */
	public void stepOn(Moving steppedOnBy, Direction movedDir) {
		//Default behaviour is to do nothing when stepped on
	}
	
	/**
	 * Gets the current grid coordinates of this object.
	 * @return The current gridCoords of this object.
	 */
	public Vector2 getGridCoords() {
		return gridCoords;
	}
	
	/**
	 * Gets the meta info, if any, that should be saved to file for this
	 * object.
	 * @return The string line of meta info for this object,
	 * 		   null if there is none.
	 */
	public String getMetaInfo() {
		return null;
	}
	
	
	@Override
	public void render(GraphicsContext graphicsContext) {
		//Calculate the x position to render at.
		//This is the x offset  + ((x - camera top left x) * the size of 1 cell)
		renderPos.setX(map.getRenderOffset().getX() 
						+ (
							(gridCoords.getX() - map.getCamera().getTopLeft().getX())
								* Map.GRID_DIMENSION
						  )
		);
		//Calculate the y position to render at.
		//This is the y offset  + ((y - camera top left y) * the size of 1 cell)
		renderPos.setY(map.getRenderOffset().getY() 
				+ (
					(gridCoords.getY() - map.getCamera().getTopLeft().getY())
						* Map.GRID_DIMENSION
				  )
		);
		super.render(graphicsContext);
	}
	
}
