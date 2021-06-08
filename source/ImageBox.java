import java.net.MalformedURLException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A rectangular image that can be rendered.
 * @author Josiah Richards
 * @version 1.1
 */
public class ImageBox extends Renderable {
	
	//The image for this image box.
	protected Image myImage;
	
	/**
	 * Creates an ImageBox that has a given image.
	 * The imageName will be looked for in the images folder of resources.
	 * @param imageName The name of the image file(with extension and not the full path).
	 * @throws MalformedURLException If the image is invalid.
	 */
	public ImageBox(String imageName)  throws MalformedURLException {
		super();
		setImage(imageName);
	}
	
	/**
	 * Creates an ImageBox that doesn't have an image yet.
	 */
	public ImageBox() {
		super();
		myImage = null;
	}
	
	/**
	 * Changes the image of this image box.
	 * @param imageName The name of the image file(with extension and not the full path).
	 * @throws MalformedURLException If the image is invalid.
	 */
	public void setImage(String imageName) throws MalformedURLException {
		myImage = new Image(FileManager.filePathToURL(FileManager.getImagePath(imageName)).toString());
	}
	
	/**
	 * Renders the image on the GraphicsContext.
	 */
	@Override
	public void render(GraphicsContext graphicsContext) {
		if (myImage != null) {
			graphicsContext.drawImage(myImage, renderPos.getX(), renderPos.getY());
		}
	}
}
