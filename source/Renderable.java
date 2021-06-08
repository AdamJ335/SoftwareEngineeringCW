import javafx.scene.canvas.GraphicsContext;

/**
 * Something that can be rendered onto a GraphicsContext.
 * @author Josiah Richards
 * @version 1.0
 */
public abstract class Renderable {
	
	//The current position to render this object.
	protected Vector2 renderPos;
	
	/*
	 * Creates a renderable object with the default render position of (0,0).
	 */
	public Renderable() {
		renderPos = new Vector2();
	}
	
	/**
	 * Renders this object on a GraphicsContext.
	 * @param graphicsContext The GraphicsContext to render onto.
	 */
	public abstract void render(GraphicsContext graphicsContext);
	
	/*
	 * Gets the current render position.
	 * @return The current render position of this object.
	 */
	public Vector2 getRenderPos() {
		return renderPos;
	}
}
