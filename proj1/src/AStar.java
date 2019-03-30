import java.util.*;

public class AStar {
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

    public AStar(int rows, int cols, Node initialNode, Node finalNode, int hvCost, Board mBoard) {
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

    public AStar(int rows, int cols, Node initialNode, Node finalNode,Board mBoard) {
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
            openList.remove(currentNode);
            if (currentNode.getRow() == getFinalNode().getRow() && currentNode.getCol() == getFinalNode().getCol() && currentNode.getOrientation() == Node.Orientation.VERTICAL) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
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

    private void addAdjacentNodes(Node currentNode) throws CloneNotSupportedException {
        checkNode(currentNode, "NORTH");
        checkNode(currentNode, "SOUTH");
        checkNode(currentNode, "EAST");
        checkNode(currentNode, "WEST");
    }


    private void checkNode(Node currentNode, String direction) throws CloneNotSupportedException {


        Node adjacentNode = null;
        Node adjacentPlusOneNode = null;

        int cost = 20;

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
        if(adjacentPlusOneNode.getOrientation()== Node.Orientation.VERTICAL ){
            cost=10;
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
                adjacentPlusOneNode.setId(id);
                adjacentPlusOneNode.setMoves(currentNode.getMoves()+1);
                getOpenList().add(adjacentPlusOneNode);
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

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public int getMovesMade() {
        return movesMade;
    }
}

