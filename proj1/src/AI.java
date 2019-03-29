import java.util.*;

public class AI {
    private static int DEFAULT_HV_COST = 1; // Horizontal - Vertical Cost
    private int hvCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;
    private Block mBlock;
    Board mBoard;

    private int id = 0;
    private int movesMade;

    public AI(int rows, int cols, Node initialNode, Node finalNode, int hvCost, Board mBoard) {
        mBlock = new Block(initialNode.getCol(), initialNode.getRow());
        this.hvCost = hvCost;
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closedSet = new HashSet<>();
        this.mBoard = mBoard;
    }

    public AI(int rows, int cols, Node initialNode, Node finalNode,Board mBoard) {
        this(rows, cols, initialNode, finalNode, DEFAULT_HV_COST, mBoard);
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.setId(-1);
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

    public List<Node> findPath() throws CloneNotSupportedException {
        initialNode.setOrientation(Node.Orientation.VERTICAL);
        initialNode.setId(0);
        openList.add(initialNode);

        while (!isEmpty(openList)) {
            movesMade++;
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            /*if(currentNode.getParent()!=null)
            System.out.println(currentNode + " / " + currentNode.getParent() + " - " + currentNode.getOrientation()+ " - " + currentNode.getParent().getOrientation());
            printBoard(currentNode);*/
            if (isFinalNode(currentNode) && currentNode.getOrientation() == Node.Orientation.VERTICAL) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }
    public void printBoard(Node currentNode){
        for (int i = 0; i < getSearchArea().length; i++) {
            for (int j = 0; j < getSearchArea().length; j++) {

                if(currentNode.getParent ()== null){return;}
                if(currentNode.getParent().getCol() == j && currentNode.getParent().getRow() == i){
                    System.out.print("|*");
                }else if(currentNode.getCol() == j && currentNode.getRow() == i){
                    System.out.print("|+");
                }else{
                    System.out.print("|" + mBoard.board[i][j].getType());
                }
            }
            System.out.println("|");
        }
    }

    private List<Node> getPath(Node currentNode) {
        System.out.println("A* - Path Found");
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }


        path.remove(0);
        boolean ended = false;
        while(!ended){
            for(int i = 0; i < path.size() - 2; i++){
                switch(path.get(i).getMoveDirection()){
                    case NORTH:
                        if(path.get(i + 1).getMoveDirection() == Node.Orientation.SOUTH){
                            path.remove(i);
                            path.remove(i);
                            i = path.size();
                        }
                        break;
                    case SOUTH:
                        if(path.get(i + 1).getMoveDirection() == Node.Orientation.NORTH){
                            path.remove(i);
                            path.remove(i);
                            i = path.size();
                        }
                        break;
                    case EAST:
                        if(path.get(i + 1).getMoveDirection() == Node.Orientation.WEST){
                            path.remove(i);
                            path.remove(i);
                            i = path.size();
                        }
                        break;
                    case WEST:
                        if(path.get(i + 1).getMoveDirection() == Node.Orientation.EAST){
                            path.remove(i);
                            path.remove(i);
                            i = path.size();
                        }
                        break;
                }
                if(i == path.size() - 3){ended=true;}
            }
        }

        return path;
    }

    private void addAdjacentNodes(Node currentNode) throws CloneNotSupportedException {
        checkNode(currentNode, "NORTH");
        checkNode(currentNode, "SOUTH");
        checkNode(currentNode, "EAST");
        checkNode(currentNode, "WEST");
    }


    private void checkNode(Node currentNode, String direction) throws CloneNotSupportedException {
        Node adjacentNode = null;
        Node adjacentPlusOneNode = null;
        int cost = 100;

        //heuristicFunc(goalState.getCor(), x) + 1 + curr.getGcost(), 1 + curr.getGcost()

        if(currentNode.getOrientation() == Node.Orientation.VERTICAL) {
            //cost = 10;
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 2 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() - 2][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 2 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 2][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
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
                    }
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
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length && currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
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
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0 && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
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
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length && currentNode.getCol() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
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
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length && currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode = (Node)  adjacentPlusOneNode.clone();
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = (Node)  adjacentNode.clone();
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                default:
                    return;
            }
        }
        if(adjacentPlusOneNode == null){return;}


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

                adjacentPlusOneNode.setNodeData(currentNode, cost);
                adjacentPlusOneNode.setId(id);
                getOpenList().add(adjacentPlusOneNode);
            }
            else {
                boolean changed = adjacentPlusOneNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    getOpenList().remove(adjacentPlusOneNode);
                    getOpenList().add(adjacentPlusOneNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
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

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public int getMovesMade() {
        return movesMade;
    }
}

