import javax.rmi.CORBA.Util;
import java.util.*;

/**
 * Implementation of the BFS Algorithm
 */
public class BFS {
    Node startNode;
    Node goalNode;
    Board mBoard;
    int movesMade = 0;
    private ArrayList<Node> closedSet;
    public BFS(Node start, Node goalNode, Board mBoard){
        this.startNode = start;
        this.goalNode = goalNode;
        this.mBoard = mBoard;
    }

    /**
     * Loop that's the bones of the BFS Algorithm, it is what starts the process
     * Whenever the block reaches the final position it returns the path, but while it doesn't find the best
     * path it tries to find nodes to add to the stack.
     * @return
     * @throws CloneNotSupportedException
     */
    public List<Node> findPath() throws CloneNotSupportedException {

        this.closedSet = new ArrayList<>();
        Stack<Node> stack = new Stack<Node>();
        stack.add(startNode);
        while (!stack.isEmpty()) {
            Node current = stack.firstElement();
            stack.remove(current);
            closedSet.add(current);

            if (current.getCol() == goalNode.getCol() && current.getRow() == goalNode.getRow() && current.getOrientation() == Node.Orientation.VERTICAL) {
                return new Utils().getPath(current);
            }
            ArrayList<Node> nodes = new ArrayList<>();
            ArrayList<Node> nodesPlusOne = new ArrayList<>();
            Node[] nodesNorth = new Utils().checkNode(current, "NORTH", mBoard.getBoard());
            Node[] nodesWest = new Utils().checkNode(current, "WEST", mBoard.getBoard());
            Node[] nodesSouth = new Utils().checkNode(current, "SOUTH", mBoard.getBoard());
            Node[] nodesEast = new Utils().checkNode(current, "EAST", mBoard.getBoard());

            if (nodesNorth[1] != null) {
                nodesNorth[1].setMoveDirection(Node.Orientation.NORTH);
                nodes.add(nodesNorth[0]);
                nodesPlusOne.add(nodesNorth[1]);
            }
            if (nodesWest[1] != null) {
                nodesWest[1].setMoveDirection(Node.Orientation.WEST);
                nodes.add(nodesWest[0]);
                nodesPlusOne.add(nodesWest[1]);
            }
            if (nodesSouth[1] != null) {
                nodesSouth[1].setMoveDirection(Node.Orientation.SOUTH);
                nodes.add(nodesSouth[0]);
                nodesPlusOne.add(nodesSouth[1]);
            }
            if (nodesEast[1] != null) {
                nodesEast[1].setMoveDirection(Node.Orientation.EAST);
                nodes.add(nodesEast[0]);
                nodesPlusOne.add(nodesEast[1]);
            }

            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                Node nodePlusOne = nodesPlusOne.get(i);
                if (!this.closedSet.contains(nodePlusOne) && !isBlock(node) && !isBlock(nodePlusOne)) {
                    stack.push(nodePlusOne);
                    movesMade++;
                    nodePlusOne.setParent(current);
                }
            }
        }
        return null;
    }

    /**
     * Checks if a certain node is in a playable area or not
     * @param node node that we want to check
     * @return true if it is a block, false if not
     */
    private boolean isBlock(Node node) {
        if(mBoard.getBoard()[node.getRow()][node.getCol()].getType() != 0){
            return false;
        }
        return true;
    }

    public int getMovesMade() {
        return movesMade;
    }
}
