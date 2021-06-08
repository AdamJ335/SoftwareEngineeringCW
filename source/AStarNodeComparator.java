import java.util.Comparator;

/**
 * Compares the traversal cost of 2 nodes.
 * @author Josiah Richards
 * @version 1.1
 */
public class AStarNodeComparator implements Comparator<AStarNode>{
	@Override
    public int compare(AStarNode node1, AStarNode node2) {
		if (node1.getFCost() == node1.getFCost()){
			//If f cost is the same, sort by heuristic cost next.
			if (node1.getHeuristicCost() == node2.getHeuristicCost()) {
				return 0; //These 2 nodes cost exactly the same
			} else if (node1.getHeuristicCost() > node2.getHeuristicCost()) {
				return 1; //node 1 costs more
			} else {
				return -1; //node 2 costs more
			}
		} else if (node1.getFCost() > node2.getFCost()) {
			return 1; //node 1 costs more
		} else {
			return -1; //node 2 costs more
		}
    }
}