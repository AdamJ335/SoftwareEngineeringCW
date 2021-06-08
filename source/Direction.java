/**
 * A class for representing a direction along the grid.
 * Directions shouldn't have magnitude(only have values between -1 and 1)
 * and shouldn't be diagonal(either x or y should be 0).
 * (0, 0) is allowed as it can be used to say no direction.
 * @author Josiah Richards
 * @version 1.2
 */
public class Direction extends Vector2 {

	//Direction words(for createFromString)
	public static final String LEFT_WORD = "left";
	public static final String RIGHT_WORD = "right";
	public static final String UP_WORD = "up";
	public static final String DOWN_WORD = "down";
	public static final String NONE_WORD = "none";
	//Premade Directions
	public static final Direction DIR_LEFT = new Direction(-1, 0);
	public static final Direction DIR_RIGHT = new Direction(1, 0);
	public static final Direction DIR_UP = new Direction(0, -1);
	public static final Direction DIR_DOWN = new Direction(0, 1);
	public static final Direction DIR_NONE = new Direction(0, 0);

	//Maximum magnitude of a direction vector i.e. x and y 
	//must be between negative MAX_MAGNITUDE and MAX_MAGNITUDE
	private static final int MAX_MAGNITUDE = 1;
	//Exception messages
	private static final String XY_RANGE_EXCEPTION_MSG = 
			"x and y values must be between -" + MAX_MAGNITUDE + " and " + MAX_MAGNITUDE;
	private static final String DIAGONAL_EXCEPTION_MSG = 
			"Either x or y must be 0(no diagonal direction)";
	
	/**
	 * Creates a Direction object with a given x and y value.
	 * @param x The x value.
	 * @param x The y value.
	 */
	public Direction(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Creates a Direction object with a the same values as another Direction.
	 */
	public Direction(Direction reference) {
		super(reference);
	}
	
	/**
	 * Creates a Direction object with a default direction of (0, 0)(no direction).
	 */
	public Direction() {
		super();
	}
	
	/**
	 * Creates a new direction from a string describing it
	 * (up, down, left, right).
	 * @param str The string representing the direction.
	 * @return A new direction of either up, down, left or right,
	 * 		   returns null if the string is unknown.
	 */
	public static Direction createFromString(String str) {
		str = str.toLowerCase();
		switch (str) {
			case LEFT_WORD:
				return new Direction(DIR_LEFT);
			case RIGHT_WORD:
				return new Direction(DIR_RIGHT);
			case UP_WORD:
				//Grid goes from top left to bottom right
				//so 'up' is -1 y
				return new Direction(DIR_UP);
			case DOWN_WORD:
				return new Direction(DIR_DOWN);
			case NONE_WORD:
				return new Direction(DIR_NONE);
			default:
				return null;
		}
	}
	

	/**
	 * Converts this direction to a string.
	 * @returns The name of the direction e.g. "left", "none",
	 * 		   returns null if it doesn't have a name.
	 */
	public String toString() {
		if (this.equals(DIR_LEFT)) {
			return LEFT_WORD;
		} else if (this.equals(DIR_RIGHT)) {
			return RIGHT_WORD;
		} else if (this.equals(DIR_UP)) {
			return UP_WORD;
		} else if (this.equals(DIR_DOWN)) {
			return DOWN_WORD;
		} else if (this.equals(DIR_NONE)) {
			return NONE_WORD;
		}
		return null;
	}
	
	/**
	 * Reverses this direction
	 */
	public void reverse() {
		setXY(x * -1, y * -1);
	}
	
	/**
	 * Get's the left direction if this direction is forward.
	 * @return The direction to the left of this direction.
	 */
	public Direction getLeft() {
		Direction leftDir = new Direction(this);
		
		/*
		 * (1,0) -> (0,-1)
		 * (0,-1) -> (-1,0)
		 * (-1,0) -> (0,1)
		 * (0,1) -> (1,0)
		 * Hence we can just reverse x then
		 * swap the x and y values.
		 */
		
		//Reverse x.
		leftDir.setX(leftDir.getX() * -1);
		//Swap x and y values.
		leftDir.setXY(leftDir.getY(), leftDir.getX());
		
		return leftDir;
	}
	
	/**
	 * Get's the right direction if this direction is forward.
	 * @return The direction to the right of this direction.
	 */
	public Direction getRight() {
		//Just get the left direction and reverse it.
		Direction rightDir = getLeft();
		rightDir.reverse();
		return rightDir;
	}
	
	
	/**
	 * Sets both the x and y values of this direction.
	 * @param x The new x value.
	 * @param y The new y value.
	 * @throws IllegalArgumentException If new values doesn't satisfy the constraints of a direction.
	 */
	public void setXY(int x, int y) {
		//Reset to 0 first to stop an exception occurring for being diagonal
		//sometimes due to us updating x first then y
		//e.g. changing from (0, 1) to (1, 0)
		this.x = 0;
		this.y = 0;
		setX(x);
		setY(y);
	}
	
	/**
	 * Sets the x value of this direction.
	 * @param x The new x value(must be within the MAX_MAGNITUDE).
	 * @throws IllegalArgumentException If new x value doesn't satisfy the constraints of a direction.
	 */
	public void setX(int x) {
		//Check it's within the max magnitude
		if (x < -MAX_MAGNITUDE || x > MAX_MAGNITUDE) {
			throw new IllegalArgumentException(XY_RANGE_EXCEPTION_MSG);
		}
		//Check it's not creating a diagonal direction
		if (x != 0 && this.y != 0) {
			throw new IllegalArgumentException(DIAGONAL_EXCEPTION_MSG);
		}
		
		this.x = x;
	}
	
	/**
	 * Sets the y value of this direction.
	 * @param y The new y value(must be within the MAX_MAGNITUDE).
	 * @throws IllegalArgumentException If new y value doesn't satisfy the constraints of a direction.
	 */
	public void setY(int y) {
		//Check it's within the max magnitude
		if (y < -MAX_MAGNITUDE || y > MAX_MAGNITUDE) {
			throw new IllegalArgumentException(XY_RANGE_EXCEPTION_MSG);
		}
		//Check it's not creating a diagonal direction
		if (this.x != 0 && y != 0) {
			throw new IllegalArgumentException(DIAGONAL_EXCEPTION_MSG);
		}
		
		this.y = y;
	}
}
