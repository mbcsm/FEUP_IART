import java.util.ArrayList;
import java.util.List;

public class Utils {
    public Node[] checkNode(Node currentNode, String direction, Node[][] searchArea) throws CloneNotSupportedException {
        Node adjacentNode = null;
        Node adjacentPlusOneNode = null;
        if(currentNode.getOrientation() == Node.Orientation.VERTICAL) {
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 2 >= 0) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow() - 2][currentNode.getCol()];
                        adjacentNode = searchArea[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 2 < searchArea.length) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow() + 2][currentNode.getCol()];
                        adjacentNode = searchArea[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = searchArea[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < searchArea.length) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = searchArea[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
                    break;
                default:
                    return null;
            }

        }else if(currentNode.getOrientation() == Node.Orientation.NORTH){
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 1 >= 0) {
                        adjacentNode = searchArea[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 2 < searchArea.length) {
                        adjacentNode = searchArea[currentNode.getRow() + 2][currentNode.getCol()];
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0 && currentNode.getRow() + 1 < searchArea.length) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = searchArea[currentNode.getRow() + 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < searchArea.length && currentNode.getRow() + 1 < searchArea.length) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = searchArea[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
                    break;
                default:
                    return null;
            }
        }
        else if(currentNode.getOrientation() == Node.Orientation.SOUTH){
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 2 >= 0) {
                        adjacentNode = searchArea[currentNode.getRow() - 2][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < searchArea.length) {
                        adjacentNode = searchArea[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0 && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = searchArea[currentNode.getRow() - 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < searchArea.length && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = searchArea[currentNode.getRow() - 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                default:
                    return null;
            }
        }
        else if(currentNode.getOrientation() == Node.Orientation.EAST){
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 1 >= 0 && currentNode.getCol() - 1 >= 0) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentNode = searchArea[currentNode.getRow() - 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < searchArea.length && currentNode.getCol() - 1 >= 0) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = searchArea[currentNode.getRow() + 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentNode = searchArea[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < searchArea.length) {
                        adjacentNode = searchArea[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                default:
                    return null;
            }
        }
        else if (currentNode.getOrientation() == Node.Orientation.WEST) {
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 1 >= 0 && currentNode.getCol() + 1 < searchArea.length) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentNode = searchArea[currentNode.getRow() - 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < searchArea.length && currentNode.getCol() + 1 < searchArea.length) {
                        adjacentPlusOneNode = searchArea[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = searchArea[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0) {
                        adjacentNode = searchArea[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < searchArea.length) {
                        adjacentNode = searchArea[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                default:
                    return null;
            }
        }
        Node[] nodes = new Node[2];

        if(adjacentPlusOneNode==null){return nodes;}


        nodes[0] = (Node) adjacentNode.clone();
        nodes[1] = (Node) adjacentPlusOneNode.clone();

        return nodes;
    }

    /**
     * Goes to the final node and recursevelly calls getParent()
     * by doing this it will build the move needed to get to the final position
     * @param currentNode - End Node
     * @return path - path to the final node
     */
    public List<Node> getPath(Node currentNode) {
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
