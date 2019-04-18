package ACTV1;

import java.util.*;

/**
 * Implementation of the A* Algorithm
 */
public class AStar {
    int moveCost = 1;
    private PriorityQueue<Node> openList;
    private ArrayList<Node> closetList;
    Node emptyCell;
    Board mBoard;
    private int movesMade;

    public AStar(Node emptyCell, Board mBoard) {

        this.mBoard = mBoard;
        setEmptyCell(emptyCell);
        emptyCell.setBoard(mBoard.getBoard());
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closetList = new ArrayList<>();
    }

    /**
     * Calculates the heuristics of all the playable nodes
     * also populates the matrix searchArea
     */
    private void setNodes() {
        for (int i = 0; i < mBoard.getBoard().length; i++) {
            for (int j = 0; j < mBoard.getBoard().length; j++) {
                Node node = new Node(i, j);
                node.setBoard(mBoard.getBoard().clone());
                node.setNumber(mBoard.getBoard()[i][j].getNumber());
                node.setFinalPos();
                node.calculateHeuristic();
                emptyCell.getBoard()[i][j] = node;
            }
        }
    }

    /**
     * Loop that's the bones of the A* Algorithm, it is what starts the process
     * Whenever the block reaches the final position it returns the path, but while it doesn't find the best
     * path it calls addAdjacentNodes() and tries to find nodes to add to the priorityQueue.
     * @return
     * @throws CloneNotSupportedException
     */
    public List<Node> findPath() throws CloneNotSupportedException {
        openList.add(emptyCell);

        while (!isEmpty(openList)) {
            movesMade++;
            Node currentNode = openList.poll();
            printBoard(currentNode.getBoard());
            closetList.add(currentNode);
            openList.remove(currentNode);
            if (checkSolvedPath(currentNode)) {
                return new Utils().getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
    }
    public void printBoard(Node[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print("|" + board[i][j].getNumber());
            }
            System.out.println("|");
        }
    }

    private boolean checkSolvedPath(Node currentNode) {
        int number = 1;
        for(int i = 0; i < currentNode.getBoard().length; i++){
            for(int j = 0; j < currentNode.getBoard().length; j++){
                if(currentNode.getBoard()[i][j].getNumber() != number && i != currentNode.getBoard().length-1 && j != currentNode.getBoard().length-1){
                    return false;
                }
                number++;
            }
        }
        return true;
    }

    /**
     * Simply calls checkNode() for every movement possible
     * @param currentNode
     * @throws CloneNotSupportedException
     */
    private void addAdjacentNodes(Node currentNode) throws CloneNotSupportedException {
        checkNode(currentNode, "NORTH");
        checkNode(currentNode, "SOUTH");
        checkNode(currentNode, "EAST");
        checkNode(currentNode, "WEST");
    }


    /**
     * Checks if the movement is possible or not.
     * For a move to be possible the block has to fall within the are of the board
     * and not fall within an empty field, all of this while taking into
     * consideration the fact the block's 1x1x2 form factor.
     *
     * If it is valid calculate the cost and add it to the priorityQueue
     * @param currentNode
     * @param direction
     * @throws CloneNotSupportedException
     */
    private void checkNode(Node currentNode, String direction) throws CloneNotSupportedException {


        switch (direction){
            case "NORTH":
                if(currentNode.getMoveDirection() == Node.Orientation.SOUTH){return;}
                break;
            case "SOUTH":
                if(currentNode.getMoveDirection() == Node.Orientation.NORTH){return;}
                break;
            case "EAST":
                if(currentNode.getMoveDirection() == Node.Orientation.WEST){return;}
                break;
            case "WEST":
                if(currentNode.getMoveDirection() == Node.Orientation.EAST){return;}
                break;
        }

        Node adjacentNode = (Node) currentNode.clone();
        int number, x, y, originalX = currentNode.getRow(), originalY = currentNode.getCol();

        switch (direction) {
            case "NORTH":
                if(currentNode.getRow() - 2 >= 0) {
                    x = currentNode.getRow() - 1;
                    y = currentNode.getCol();
                }else{return;}
                break;
            case "SOUTH":
                if(currentNode.getRow() + 2 < currentNode.getBoard().length) {
                    x = currentNode.getRow() + 1;
                    y = currentNode.getCol();
                }else{return;}
                break;
            case "WEST":
                if(currentNode.getCol() - 2 >= 0) {
                    x = currentNode.getRow();
                    y = currentNode.getCol() - 1;
                }else{return;}
                break;
            case "EAST":
                if(currentNode.getCol() + 2 < currentNode.getBoard().length) {
                    x = currentNode.getRow();
                    y = currentNode.getCol() + 1;
                }else{return;}
                break;
            default:
                return;
        }
        if(x == -1 || y == -1)return;

        number = adjacentNode.getBoard()[x][y].getNumber();
        adjacentNode.getBoard()[x][y].setNumber(0);
        adjacentNode.getBoard()[originalX][originalY].setNumber(number);

        if (!getClosedSet().contains(adjacentNode) && !getOpenList().contains(adjacentNode)) {
            switch (direction){
                case "NORTH":
                    adjacentNode.setMoveDirection(Node.Orientation.NORTH);
                    break;
                case "SOUTH":
                    adjacentNode.setMoveDirection(Node.Orientation.SOUTH);
                    break;
                case "EAST":
                    adjacentNode.setMoveDirection(Node.Orientation.EAST);
                    break;
                case "WEST":
                    adjacentNode.setMoveDirection(Node.Orientation.WEST);
                    break;
            }

            //adjacentNode.calculateHeuristic(finalNode);
            adjacentNode.setNodeData(currentNode, this.moveCost);
            getOpenList().add(adjacentNode);
            movesMade++;
        }else{
            Node adjacentUpdated = (Node) adjacentNode.clone();
            adjacentUpdated.setNodeData(currentNode, this.moveCost);
            if (adjacentUpdated.getF() > adjacentNode.getF()) { // costs from current node are cheaper than previous costs
                adjacentNode.setParent(currentNode);
                adjacentNode.setNodeData(currentNode, this.moveCost);// set current node as previous for this node
            }
        }

    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    public void setEmptyCell(Node emptyCell) {
        this.emptyCell = emptyCell;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public ArrayList<Node> getClosedSet() {
        return closetList;
    }

    public int getMovesMade() {
        return movesMade;
    }
}

