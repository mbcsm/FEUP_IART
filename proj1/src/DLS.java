import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Implementation of the DLS Algorithm
 */
public class DLS {

    Node startNode;
    Node goalNode;
    Board mBoard;
    int maxSearchDistance;
    int movesMade = 0;
    private Node bestNode;
    boolean pathFound = false;

    public DLS(Node start, Node goalNode, Board mBoard, int maxSearchDistance){
        this.startNode = start;
        this.goalNode = goalNode;
        this.mBoard = mBoard;
        this.maxSearchDistance = maxSearchDistance;
    }

    /**
     * Loop that's the bones of the BFS Algorithm, it is what starts the process
     * Whenever the block reaches the final position it returns the path, but while it doesn't find the best
     * path it tries to find nodes to add to the stack.
     * @return
     * @throws CloneNotSupportedException
     */
    public List<Node> findPath() throws CloneNotSupportedException {

        bestNode = new Node();
        bestNode.setMoves(Integer.MAX_VALUE);

        Stack<Node> openStack = new Stack<Node>();
        openStack.add(startNode);
        startNode.setMoves(0);

        while (!openStack.isEmpty()) {
            Node current = openStack.pop();

            if (current.getCol() == goalNode.getCol() && current.getRow() == goalNode.getRow() && current.getOrientation() == Node.Orientation.VERTICAL) {
                if(bestNode.getMoves() > current.getMoves()){
                    bestNode = (Node) current.clone();
                    pathFound = true;
                }
            }

            if(current.getMoves() >= maxSearchDistance){
                continue;
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
                if (!isBlock(node) && !isBlock(nodePlusOne) && !nodePlusOne.equals(current.getParent())) {
                    nodePlusOne.setParent(current);
                    nodePlusOne.setMoves(current.getMoves()+1);
                    openStack.push(nodePlusOne);
                    movesMade++;
                }
            }
        }

        if(pathFound){
            return new Utils().getPath(bestNode);
        }else{
            return null;
        }

    }

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

