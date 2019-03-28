import java.util.*;

public class AI {
    private static int DEFAULT_HV_COST = 1; // Horizontal - Vertical Cost
    private int hvCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> openList, openListTemp;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;
    private Block mBlock;

    public AI(int rows, int cols, Node initialNode, Node finalNode, int hvCost) {
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
        this.openListTemp = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closedSet = new HashSet<>();
    }

    public AI(int rows, int cols, Node initialNode, Node finalNode) {
        this(rows, cols, initialNode, finalNode, DEFAULT_HV_COST);
    }

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

    public List<Node> findPath() {
        initialNode.setOrientation(Node.Orientation.VERTICAL);
        openList.add(initialNode);

        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            System.out.println(currentNode + " / " + currentNode.getParent() + " - " + currentNode.getOrientation());
            if (isFinalNode(currentNode) && currentNode.getOrientation() == Node.Orientation.VERTICAL) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
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
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            checkNode(currentNode, getHvCost(), "SOUTH");
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, getHvCost(), "WEST");
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, getHvCost(), "EAST");
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, getHvCost(), "NORTH");
        }
    }

    private void checkNode(Node currentNode, int cost, String direction) {
        Node adjacentNode = null;
        Node adjacentPlusOneNode = null;

        /*if(currentNode.getRow() == 2  && currentNode.getCol() == 3 && currentNode.getParent().getCol() == 4 && currentNode.getParent().getRow() == 2){
            System.out.println("-----");
        }*/

        if(currentNode.getOrientation() == Node.Orientation.VERTICAL) {
            switch (direction) {
                case "NORTH":
                    if(currentNode.getRow() - 2 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() - 2][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol()];
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 2 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 2][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 2];
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
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
                        adjacentPlusOneNode.setOrientation(Node.Orientation.NORTH);
                        adjacentNode.setOrientation(Node.Orientation.NORTH);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length && currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() + 1];
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
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0 && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode.setOrientation(Node.Orientation.SOUTH);
                        adjacentNode.setOrientation(Node.Orientation.SOUTH);
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length && currentNode.getRow() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
                        adjacentNode = getSearchArea()[currentNode.getRow() - 1][currentNode.getCol() + 1];
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
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length && currentNode.getCol() - 1 >= 0) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() - 1];
                        adjacentPlusOneNode.setOrientation(Node.Orientation.EAST);
                        adjacentNode.setOrientation(Node.Orientation.EAST);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 2 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 2];
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 1];
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
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "SOUTH":
                    if(currentNode.getRow() + 1 < getSearchArea().length && currentNode.getCol() + 1 < getSearchArea().length) {
                        adjacentPlusOneNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol()];
                        adjacentNode = getSearchArea()[currentNode.getRow() + 1][currentNode.getCol() + 1];
                        adjacentPlusOneNode.setOrientation(Node.Orientation.WEST);
                        adjacentNode.setOrientation(Node.Orientation.WEST);
                    }
                    break;
                case "WEST":
                    if(currentNode.getCol() - 1 >= 0) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() - 1];
                        adjacentNode.setOrientation(Node.Orientation.VERTICAL);
                        adjacentPlusOneNode = adjacentNode;
                    }
                    break;
                case "EAST":
                    if(currentNode.getCol() + 2 < getSearchArea().length) {
                        adjacentNode = getSearchArea()[currentNode.getRow()][currentNode.getCol() + 2];
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
            if (!getOpenList().contains(adjacentNode)) {

                adjacentPlusOneNode.setNodeData(currentNode, cost);
                adjacentPlusOneNode.setOtherNodeBlockIsOcuppying(adjacentNode);
                getOpenList().add(adjacentPlusOneNode);
            } else {
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

    public Node getInitialNode() {
        return initialNode;
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

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }
}

