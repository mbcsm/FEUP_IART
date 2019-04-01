import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IDDFS {

    Node startNode;
    Node goalNode;
    Board mBoard;
    int maxSearchDistance = 1;
    int movesMade = 0;
    private Node bestNode;
    boolean pathFound = false;
    int pathFoundLevel = 0;

    public IDDFS(Node start, Node goalNode, Board mBoard, int maxSearchDistance){
        this.startNode = start;
        this.goalNode = goalNode;
        this.mBoard = mBoard;
        this.maxSearchDistance = maxSearchDistance;
    }

    public List<Node> findPath() throws CloneNotSupportedException {

        bestNode = new Node();
        bestNode.setMoves(Integer.MAX_VALUE);


        Stack<Node> openStack = new Stack<Node>();
        openStack.add(startNode);
        startNode.setMoves(0);
        startNode.setVisisted(true);
        while (!openStack.isEmpty()) {
            Node current = openStack.firstElement();
            openStack.remove(current);

            int currentLevel = current.getMoves();
            if(pathFound && current.getMoves() > pathFoundLevel + 1){
                return getPath(bestNode);
            }

            for (int it = 0; it < maxSearchDistance; it++){
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
                    if (!isBlock(node) && !isBlock(nodePlusOne) && !nodePlusOne.equals(startNode) && !nodePlusOne.equals(current.getParent())) {
                        nodePlusOne.setParent(current);
                        nodePlusOne.setMoves(current.getMoves()+1);
                        openStack.add(openStack.size(), nodePlusOne);
                        movesMade++;

                        if (nodePlusOne.getCol() == goalNode.getCol() && nodePlusOne.getRow() == goalNode.getRow() && nodePlusOne.getOrientation() == Node.Orientation.VERTICAL) {
                            if(bestNode.getMoves() > nodePlusOne.getMoves()){
                                bestNode = (Node) nodePlusOne.clone();
                                pathFound = true;
                                pathFoundLevel = currentLevel + maxSearchDistance;
                            }
                        }
                    }
                }
                current = openStack.pop();
            }
        }
        return null;
    }

    private boolean isBlock(Node node) {
        if(mBoard.getBoard()[node.getRow()][node.getCol()].getType() != 0){
            return false;
        }
        return true;
    }

    private List<Node> getPath(Node currentNode) {
        System.out.println("PATH FOUND");
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }

        path.remove(0);

        return path;
    }

    public int getMovesMade() {
        return movesMade;
    }
}

