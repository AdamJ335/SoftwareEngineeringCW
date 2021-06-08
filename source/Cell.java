/**
 * Stores basic information about a cell.
 * @author Daniel Miles 973755
 * @version 1.2
 */
public abstract class Cell extends OnGrid{
	
	/**
	 * Creates a new cell object at a given coordinate
	 * for a map.
	 * Doesn't actually add the cell to the map!
	 * @param x The x coordinate of this cell.
	 * @param y The y coordinate of this cell.
	 * @param map The map this cell is for.
	 */
	public Cell(int x, int y, Map map) {
		super(x, y, map);
	}
	
	/**
	 * Creates a new cell object for a map.
	 * Doesn't actually add the cell to the map!
	 * @param map The map this cell is for.
	 */
	public Cell(Map map) {
		super(map);
	}
	
	/**
	 * Gets the character, if any, thats used to represent this cell in a file.
	 * @return The character that represents this cell in a file,
	 * 		   defaults to a space.
	 */
	public char getSymbol() {
		return ' ';
	}
}
