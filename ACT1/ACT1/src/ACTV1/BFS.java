package ACTV1;

import javax.rmi.CORBA.Util;
import java.util.*;

/**
 * Implementation of the BFS Algorithm
 */
public class BFS {
    Node startNode;
    int movesMade = 0;
    private ArrayList<Node> closedSet;
    public BFS(Node start, Board mBoard){
        this.startNode = start;
        new Utils().setNodes(mBoard, start);
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

            if (new Utils().checkSolvedPath(current)) {
                return new Utils().getPath(current);
            }
            ArrayList<Node> nodes = new ArrayList<>();

            Node nodesNorth = new Utils().checkNode("NORTH", current);
            Node nodesWest = new Utils().checkNode("WEST", current);
            Node nodesSouth = new Utils().checkNode("SOUTH", current);
            Node nodesEast = new Utils().checkNode("EAST", current);

            if(current.getMoveDirection() != null){
                switch (current.getMoveDirection()){
                    case NORTH:
                        nodesSouth = null;
                        break;
                    case SOUTH:
                        nodesNorth = null;
                        break;
                    case EAST:
                        nodesWest = null;
                        break;
                    case WEST:
                        nodesEast = null;
                        break;
                }
            }


            if (nodesNorth != null) {
                nodesNorth.setMoveDirection(Node.Orientation.NORTH);
                nodes.add(nodesNorth);
            }
            if (nodesWest != null) {
                nodesWest.setMoveDirection(Node.Orientation.WEST);
                nodes.add(nodesWest);
            }
            if (nodesSouth != null) {
                nodesSouth.setMoveDirection(Node.Orientation.SOUTH);
                nodes.add(nodesSouth);
            }
            if (nodesEast != null) {
                nodesEast.setMoveDirection(Node.Orientation.EAST);
                nodes.add(nodesEast);
            }


            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (!this.closedSet.contains(node)) {
                    stack.push(node);
                    node.setParent(current);
                    movesMade++;
                }
            }
        }
        return null;
    }

    int getMovesMade() {
        return movesMade;
    }
}
