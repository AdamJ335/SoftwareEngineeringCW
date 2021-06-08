import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Gets a path from a start coordinate to a target coordinate 
 * on a given map.
 * @author Josiah Richards
 * @version 1.2
 */
public class AStarPathFinder {
	
	//The map we're path finding on.
	private Map map;
	//The list of open nodes, ordered by the cheapest traversal cost
	//(best path).
	private PriorityQueue<AStarNode> openList;
	//Current array of nodes(both open an closed) created where each index
	//matches a index in the grid of the map.
	private AStarNode[][] nodes;
	
	/**
	 * Creates a new path finder object for a given map.
	 * @param map The map to find paths on.
	 */
	public AStarPathFinder(Map map) {
		this.map = map;
	}
	
	/**
	 * Gets the path(if any) from a start point on the map to an
	 * end point on the map for a given moving entity.
	 * @param startPoint The grid coordinates to start on.
	 * @param endPoint The grid coordinates to find a path to.
	 * @param moving The moving entity that must be able to traverse the path.
	 * @return An array of coordinates leading from(but not including) the start
	 * 			point to (including) the end point.
	 */
	public Vector2[] getPath(Vector2 startPoint, Vector2 endPoint, Moving moving) {
		
		//Initialise the all and open node lists.
		nodes = new AStarNode[map.getGridDimensions().getX()][map.getGridDimensions().getY()];
		openList = new PriorityQueue<AStarNode>(new AStarNodeComparator());
		
		//Create a closed node for the start point.
		AStarNode currentNode = new AStarNode(null, startPoint, endPoint);
		currentNode.setIsClosed(true);
		nodes[startPoint.getX()][startPoint.getY()] = currentNode;
		
		//While there's still more nodes to check or we've found the end point
		while (currentNode != null && !currentNode.getGridCoord().equals(endPoint)) {
			
			//Check the current node's neighbours
			processNeighbour(currentNode.getGridCoord().addXYCreateNew(0, -1),
					endPoint, currentNode, moving); // Up
			processNeighbour(currentNode.getGridCoord().addXYCreateNew(1, 0),
					endPoint, currentNode, moving); // Right
			processNeighbour(currentNode.getGridCoord().addXYCreateNew(0, 1),
					endPoint, currentNode, moving);// Down
			processNeighbour(currentNode.getGridCoord().addXYCreateNew(-1, 0),
					endPoint, currentNode, moving);// Left
			
			
			//Get lowest f cost(lowest heuristic if multiple) and update current accordingly
			currentNode = openList.poll();
			if (currentNode != null) {
				currentNode.setIsClosed(true);
			}
		}
		
		//Start at the last(end point) node and go backwards adding all the
		//nodes onto a stack to get the path but backwards.
		Stack<Vector2> pathStack = new Stack<Vector2>();
		int pathLength = 0;
		while (currentNode != null && currentNode.getParent() != null) {
			pathLength++;
			pathStack.add(currentNode.getGridCoord());
			currentNode = currentNode.getParent();
		}
		
		//Pop all the nodes off the stack to reverse the backwards path we found
		//to get the final path.
		Vector2[] finalPath = new Vector2[pathLength];
		for (int i = 0; i < pathLength; i++) {
			finalPath[i] = pathStack.pop();
		}
		
		return finalPath;
	}
	
	/**
	 * Checks if a coordinate shouldn't be considered when
	 * finding a path.
	 * @param coord The coordinate to check.
	 * @param moving The moving entity that must be able to traverse the node.
	 * @return True if this coordinate should be ignored, false otherwise.
	 */
	public boolean shouldIgnore(Vector2 coord, Moving moving) {
		//Check that the coordinate is on the map.
		if (nodes.length > coord.getX() && nodes[0].length > coord.getY()) {
			//Check that the node isn't closed(if it exists)
			if (nodes[coord.getX()][coord.getY()] == null || 
					!nodes[coord.getX()][coord.getY()].getIsClosed()) {
				//Check that the moving entity traverse the coordinate.
				if (moving.canWalkOn(coord)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Updates the traversal cost of a neighbouring coordinate
	 * and adds its node to the open list.
	 * The end point node costs 0.
	 * @param neighbourCoord The coordinate of this neighbour.
	 * @param endPoint The coordinate we're finding a path to
	 * @param currentNode The node we came from to get to this neighbour.
	 * @param moving The moving entity that must be able to traverse the path.
	 */
	public void processNeighbour(Vector2 neighbourCoord, Vector2 endPoint, 
									AStarNode currentNode, Moving moving) {
		//If this neighbour is our end point
		if (neighbourCoord.equals(endPoint)) {
			//Create a 0 cost node in the open list for
			//the end point.
			AStarNode neighbour = new AStarNode(currentNode, neighbourCoord, endPoint);
			neighbour.setFCost(0);
			nodes[neighbourCoord.getX()][neighbourCoord.getY()] = neighbour;
			openList.add(neighbour);
		//Otherwise check that we shouldn't ignore this neighbour
		} else if(!shouldIgnore(neighbourCoord, moving)) {
			
			//Check if a node already exists for this
			//neighbour coordinate.
			//Skip F check means skip checking if we should update
			//the f cost of this node(as if it's a new node this isn't necessary).
			boolean skipFCheck = false;
			AStarNode neighbourNode = nodes[neighbourCoord.getX()][neighbourCoord.getY()];
			if (neighbourNode == null) {
				//If a node doesn't already exist for this neighbour,
				//create it
				neighbourNode = new AStarNode(currentNode, neighbourCoord, endPoint);
				neighbourNode.updateFCost();
				nodes[neighbourCoord.getX()][neighbourCoord.getY()] = neighbourNode;
				//We'll always have to update the f cost of a new node.
				skipFCheck = true;
			}
			
			//Check if we should update the f cost of this neighbour node,
			//for a pre-existing node we check if coming to this neighbour node
			//from our new current node is cheaper than the previous f cost.
			if(skipFCheck || neighbourNode.calcFCost(currentNode) < neighbourNode.getFCost()) {
				//Make the parent of this neighbour node the current node
				//we came from as this is a cheaper path.
				neighbourNode.setParent(currentNode);
				neighbourNode.updateFCost();
				
				//Remove this node from the open list priority queue
				//and re-add it so it's positioned correctly in the queue.
				if (openList.contains(neighbourNode)) {
					
					openList.remove(neighbourNode);
				}
				openList.add(neighbourNode);
			}
			
		}
	}
	
}
