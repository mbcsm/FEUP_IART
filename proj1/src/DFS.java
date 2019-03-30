import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFS {
    Node startNode;
    Node goalNode;
    Board mBoard;
    public DFS(Node start, Node goalNode, Board mBoard){
        this.startNode = start;
        this.goalNode = goalNode;
        this.mBoard = mBoard;
    }


    public List<Node> findPath() throws CloneNotSupportedException {

        Stack<Node> stack = new Stack<Node>();
        stack.add(startNode);
        startNode.setVisisted(true);
        while (!stack.isEmpty()) {
            Node current = stack.firstElement();
            stack.remove(current);

            if (current.getCol() == goalNode.getCol() && current.getRow() == goalNode.getRow() && current.getOrientation() == Node.Orientation.VERTICAL) {
                return getPath(current);
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
                if (node != null && !nodePlusOne.getVisisted() && !isBlock(node) && !isBlock(nodePlusOne)) {
                    stack.add(nodePlusOne);
                    nodePlusOne.setVisisted(true);
                    nodePlusOne.setParent(current);

                }
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
}
