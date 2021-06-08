/**
 * Stores information about the costs of traversing 
 * coordinate.
 * @author Josiah Richards
 * @version 1.2
 */
public class AStarNode {
	//If this node has already been visited
	private boolean isClosed;
	//The cheapest neighbouring node that you can come from
	//to get to this node.
	private AStarNode parent;
	//The grid coordinate this node represents.
	private Vector2 gridCoord;
	//The heuristic(h) cost of going from this node's grid coordinate
	//to the target grid coordinate using the Manhattan heuristic
	//(the x difference + y difference between the 2 coordinates).
	private int heuristicCost;
	//The total cost of traversing this node
	//(heuristicCost + the f cost of the parent node)
	private int fCost;
	
	/**
	 * Creates an A Star Node and calculates its initial traversal costs.
	 * @param parent The node you came from to get to this node.
	 * @param gridCoord The grid coordinate this node represents.
	 * @param targetGridCoord The target grid coordinate(for calculating h cost).
	 */
	public AStarNode(AStarNode parent, Vector2 gridCoord, Vector2 targetGridCoord) {
		this.gridCoord = gridCoord;
		this.parent = parent;
		isClosed = false;
		updateHeuristicCost(targetGridCoord);
		updateFCost();
	}
	
	/**
	 * Updates the heuristic cost for a new target grid coordinate.
	 * @param targetGridCoord The new target grid coordinate.
	 */
	public void updateHeuristicCost(Vector2 targetGridCoord) {
		//Cost = x difference + y difference
		heuristicCost = Math.abs(gridCoord.getX() - targetGridCoord.getX()) 
					  + Math.abs(gridCoord.getY() - targetGridCoord.getY());
	}
	
	/**
	 * Calculates the 'theoretical' f cost of this node if
	 * you came from a given node.
	 * @param fromParent The node you came from to get to this node.
	 * @return The total traversal cost of this node from a given parent node.
	 */
	public int calcFCost(AStarNode fromParent) {
		return heuristicCost + (fromParent == null ? 0 : fromParent.getFCost());
	}
	
	/**
	 * Updates the f cost of this node based on its current parent.
	 */
	public void updateFCost() {
		fCost = calcFCost(parent);
	}
	
	/**
	 * Get's the total traversal cost of this node.
	 * @return the total traversal cost(f cost) of this node.
	 */
	public int getFCost() {
		return fCost;
	}
	
	/**
	 * Forces the f cost of this node to be a given value.
	 * (E.g. forcing the goal to cost 0).
	 * @param fCost The new f cost.
	 */
	public void setFCost(int fCost) {
		this.fCost = fCost;
	}
	
	/**
	 * Sets the parent(node came from to get to this node)
	 * of this node.
	 * @param parent The new parent node.
	 */
	public void setParent(AStarNode parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the parent(node came from to get to this node)
	 * of this node.
	 * @return The parent node of this node.
	 */
	public AStarNode getParent() {
		return parent;
	}
	
	/**
	 * Gets the (Manhattan)heuristic cost of this node.
	 * @return The heuristic cost of this node.
	 */
	public int getHeuristicCost() {
		return heuristicCost;
	}
	
	/**
	 * Gets the grid coordinate this node represents.
	 * @return The grid coordinate this node represents.
	 */
	public Vector2 getGridCoord() {
		return gridCoord;
	}
	
	/**
	 * Sets if this node is closed(already visited)
	 * or not.
	 * @param isClosed The new is closed value.
	 */
	public void setIsClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	/**
	 * Gets if this node is closed(already visited)
	 * or not.
	 * @return If this node is closed(true) or not(false).
	 */
	public boolean getIsClosed() {
		return isClosed;
	}
}
