/**
 * Essentially a rectangle with a top left position and size that represents
 * a view frustum.
 * @author Josiah Richards
 * @version 1.2
 */
public class Camera {
	
	//Top left coords of this camera
	private Vector2 topLeft;
	//How many grid cells this camera sees in the x and y dimensions
	//I.E the width and height of the camera.
	//Use odd x and y values to allow for something to be central.
	private Vector2 size;
	
	/**
	 * Creates a new camera of a size.
	 * @param size The number of grid cells the camera can see in each dimension.
	 */
	public Camera(Vector2 size) {
		topLeft = new Vector2();
		this.size = size;
	}
	
	/**
	 * Moves the camera so that its centre is at a point.
	 * Only properly works if the camera size is odd.
	 * @param point The point to try and make the centre of the camera.
	 */
	public void centreOnPoint(Vector2 point) {
		topLeft.setX(point.getX() - (size.getX()/2));
		topLeft.setY(point.getY() - (size.getY()/2));
	}
	
	/**
	 * Moves the camera so that its centre is at a point.
	 * Only properly works if the camera size is odd.
	 * @param point The point to try and make the centre of the camera.
	 */
	public boolean isPointInView(Vector2 point) {
		//Check the point is within the x size
		if(point.getX() >= topLeft.getX() && point.getX() < (topLeft.getX() + size.getX())) {
			//Check the point is within the y size
			if(point.getY() >= topLeft.getY() && point.getY() < (topLeft.getY() + size.getY())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get's the size of this camera.
	 * @return The size of this camera.
	 */
	public Vector2 getSize() {
		return size;
	}
	
	/**
	 * Get's the top left position of this camera.
	 * @return The top left position of this camera.
	 */
	public Vector2 getTopLeft() {
		return topLeft;
	}
}
