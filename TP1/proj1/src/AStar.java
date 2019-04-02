import java.util.*;


/**
 * Implementation of the A* Algorithm
 */
public class AStar {
    int verticalCost;
    int horizontalCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private ArrayList<Node> closetList;
    private Node initialNode;
    private Node finalNode;

    private int movesMade;

    public AStar(Node initialNode, Node finalNode, Board mBoard, int verticalCost, int horizontalCost) {

        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[mBoard.getBoard().length][mBoard.getBoard().length];
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closetList = new ArrayList<>();
        this.verticalCost = verticalCost;
        this.horizontalCost = horizontalCost;
    }

    /**
     * Calculates the heuristics of all the playable nodes
     * also populates the matrix searchArea
     */
    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }


    public void setBlocks(Board mBoard) {
        for (int i = 0; i < mBoard.getSizeX(); i++) {
            for(int j = 0; j < mBoard.getSizeY(); j++){
                if(mBoard.getBoard()[i][j].getType() == 0){
                    setBlock(i, j);
                }

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
        initialNode.setOrientation(Node.Orientation.VERTICAL);
        openList.add(initialNode);

        while (!isEmpty(openList)) {
            movesMade++;
            Node currentNode = openList.poll();
            closetList.add(currentNode);
            openList.remove(currentNode);
            if (currentNode.getRow() == getFinalNode().getRow() && currentNode.getCol() == getFinalNode().getCol() && currentNode.getOrientation() == Node.Orientation.VERTICAL) {
                return new Utils().getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
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

        Node adjacentNode = null;
        Node adjacentPlusOneNode = null;

        int cost = this.horizontalCost;

        if(currentNode.getOrientation() == Node.Orientation.VERTICAL) {
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 2 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() - 2][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }else{return;}
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 2 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 2][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }else{return;}
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }else{return;}
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }else{return;}
                    break;
                default:
                    return;
            }

        }else if(currentNode.getOrientation() == Node.Orientation.NORTH){
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 1 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 2 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow() + 2][currentNode.getCol()];
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0 && currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }else{return;}
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length && currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }else{return;}
                    break;
                default:
                    return;
            }
        }
        else if(currentNode.getOrientation() == Node.Orientation.SOUTH){
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 2 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow() - 2][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0 && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }else{return;}
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }else{return;}
                    break;
                default:
                    return;
            }
        }
        else if(currentNode.getOrientation() == Node.Orientation.EAST){
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 1 >= 0 && currentNode.getCol() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }else{return;}
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length && currentNode.getCol() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }else{return;}
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                default:
                    return;
            }
        }
        else if (currentNode.getOrientation() == Node.Orientation.WEST) {
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 1 >= 0 && currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }else{return;}
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length && currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }else{return;}
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }else{return;}
                    break;
                default:
                    return;
            }
        }
        if(adjacentPlusOneNode == null){return;}
        if(adjacentPlusOneNode.getOrientation()== Node.Orientation.VERTICAL ){
            cost = this.verticalCost;
        }

        if (!adjacentNode.isBlock() && !adjacentPlusOneNode.isBlock() && !getClosedSet().contains(adjacentPlusOneNode)) {
            if (!getOpenList().contains(adjacentPlusOneNode)) {

                switch (direction){
                    case "NORTH":
                        adjacentPlusOneNode.setMoveDirection(Node.Orientation.NORTH);
                        break;
                    case "SOUTH":
                        adjacentPlusOneNode.setMoveDirection(Node.Orientation.SOUTH);
                        break;
                    case "EAST":
                        adjacentPlusOneNode.setMoveDirection(Node.Orientation.EAST);
                        break;
                    case "WEST":
                        adjacentPlusOneNode.setMoveDirection(Node.Orientation.WEST);
                        break;
                }


                adjacentPlusOneNode.calculateHeuristic(finalNode);
                adjacentPlusOneNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentPlusOneNode);
                movesMade++;
            }else{
                Node adjacentPlusOneUpdated = (Node) adjacentPlusOneNode.clone();
                adjacentPlusOneUpdated.setNodeData(currentNode, cost);
                if (adjacentPlusOneUpdated.getF() > adjacentPlusOneNode.getF()) { // costs from current node are cheaper than previous costs
                    adjacentPlusOneNode.setParent(currentNode);
                    adjacentPlusOneNode.setNodeData(currentNode, cost);// set current node as previous for this node
                }
            }
        }
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
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

