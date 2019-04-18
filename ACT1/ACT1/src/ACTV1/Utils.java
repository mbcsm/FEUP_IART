package ACTV1;

import java.util.ArrayList;
import java.util.List;

class Utils {
    /**
     * Goes to the final node and recursevelly calls getParent()
     * by doing this it will build the move needed to get to the final position
     * @param currentNode - End Node
     * @return path - path to the final node
     */
    List<Node> getPath(Node currentNode) {
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


    public boolean checkSolvedPath(Node currentNode) {
        int number = 1;
        for(int i = 0; i < currentNode.getBoard().length; i++){
            for(int j = 0; j < currentNode.getBoard().length; j++){
                if(i == currentNode.getBoard().length-1 && j == currentNode.getBoard().length-1){
                    number = 0;
                }
                if(currentNode.getBoard()[i][j].getNumber() != number){
                    return false;
                }
                number++;
            }
        }
        return true;
    }

    public Node checkNode(String direction, Node currentNode) throws CloneNotSupportedException {
        int number, x, y, originalX = currentNode.getRow(), originalY = currentNode.getCol();
        switch (direction) {
            case "NORTH":
                if(currentNode.getRow() - 1 >= 0) {
                    x = currentNode.getRow() - 1;
                    y = currentNode.getCol();
                }else{return null;}
                break;
            case "SOUTH":
                if(currentNode.getRow() + 1 < currentNode.getBoard().length) {
                    x = currentNode.getRow() + 1;
                    y = currentNode.getCol();
                }else{return null;}
                break;
            case "WEST":
                if(currentNode.getCol() - 1 >= 0) {
                    x = currentNode.getRow();
                    y = currentNode.getCol() - 1;
                }else{return null;}
                break;
            case "EAST":
                if(currentNode.getCol() + 1 < currentNode.getBoard().length) {
                    x = currentNode.getRow();
                    y = currentNode.getCol() + 1;
                }else{return null;}
                break;
            default:
                return null;
        }
        if(x == -1 || y == -1){return null;}

        Node adjacentNode = new Node(currentNode.getBoard().length, x, y);
        for(int i=0; i < currentNode.getBoard().length; i++)
            for(int j=0; j<currentNode.getBoard().length; j++)
                adjacentNode.getBoard()[i][j]= (Node) currentNode.getBoard()[i][j].clone();

        number = adjacentNode.getBoard()[x][y].getNumber();
        adjacentNode.getBoard()[x][y].setNumber(0);
        adjacentNode.getBoard()[originalX][originalY].setNumber(number);

        return adjacentNode;
    }

    public void setNodes(Board mBoard, Node emptyCell) {
        for (int i = 0; i < mBoard.getBoard().length; i++) {
            for (int j = 0; j < mBoard.getBoard().length; j++) {
                Node node = new Node(mBoard.size, i, j);
                node.setBoard(mBoard.getBoard().clone());
                node.setNumber(mBoard.getBoard()[i][j].getNumber());
                node.calculateHeuristic();
                emptyCell.getBoard()[i][j] = node;
            }
        }
    }

}
