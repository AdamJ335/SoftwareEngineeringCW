/**
 * A class for holding 2 integers, a x and a y, that represents a vector.
 * @author Josiah Richards
 * @version 1.3
 */
public class Vector2 {
	
	//The current x and y values of this vector.
	protected int x;
	protected int y;
	
	/**
	 * Creates a Vector2 object with a given x and y value.
	 * @param x The x value.
	 * @param y The y value.
	 */
	public Vector2(int x, int y) {
		setXY(x, y);
	}
	
	/**
	 * Creates a Vector2 object with a the same values as another Vector2.
	 * @param reference The Vector2 to copy the values of.
	 */
	public Vector2(Vector2 reference){
		setXY(reference);
	}
	
	/**
	 * Creates a Vector2 object with a default value of (0, 0).
	 */
	public Vector2(){
		setXY(0, 0);
	}
	
	/**
	 * Converts the properties of this class to a string with format x,y.
	 * @return A string with the properties in the format x,y.
	 */
	public String toString() {
		return x + "," + y ;
	}
	
	/**
	 * Checks if this vector2 is equivalent to another vector2
	 * (they have the same x and y).
	 * @param vector2 The vector2 to compare this vector2 to.
	 * @return If these vector2s are equivalent or not.
	 */
	public boolean equals(Vector2 vector2) {
		if(vector2.getX() == x && vector2.getY() == y) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adds an amount to the x and y values.
	 * @param xAmount Amount to add to x.
	 * @param yAmount Amount to add to y.
	 */
	public void addXY(int xAmount, int yAmount) {
		addX(xAmount);
		addY(yAmount);
	}
	
	/**
	 * Adds an amount to the x and y values of a clone of this vector.
	 * @param xAmount Amount to add to x.
	 * @param yAmount Amount to add to y.
	 */
	public Vector2 addXYCreateNew(int xAmount, int yAmount) {
		Vector2 newVector = new Vector2(this);
		newVector.addXY(xAmount, yAmount);
		return newVector;
	}
	
	/**
	 * Adds an amount to the x and y values of a clone of this vector.
	 * @param addVector The vector2 to add to this one.
	 */
	public Vector2 addXYCreateNew(Vector2 addVector) {
		return addXYCreateNew(addVector.getX(), addVector.getY());
	}
	
	
	/**
	 * Adds the x,y values of another vector2 to this vector2
	 * @param addVector The vector2 to add to this one.
	 */
	public void addXY(Vector2 addVector) {
		addXY(addVector.getX(), addVector.getY());
	}
	
	/**
	 * Adds an amount to the x value.
	 * @param xAmount Amount to add.
	 */
	public void addX(int xAmount) {
		setX(x + xAmount);
	}
	
	/**
	 * Adds an amount to the y value.
	 * @param yAmount Amount to add.
	 */
	public void addY(int yAmount) {
		setY(y + yAmount);
	}
	
	/**
	 * Sets the x and y values to that of another vector.
	 * @param vector The vector to copy
	 */
	public void setXY(Vector2 vector) {
		setXY(vector.getX(), vector.getY());
	}
	
	/**
	 * Sets both the x and y values of this vector.
	 * @param x The new x value.
	 * @param y The new y value.
	 */
	public void setXY(int x, int y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Sets the x value of this vector.
	 * @param x The new x value.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Sets the y value of this vector.
	 * @param y The new y value.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Gets the current x values of this vector.
	 * @return The current x value.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the current y values of this vector.
	 * @return The current y value.
	 */
	public int getY() {
		return y;
	}
}
