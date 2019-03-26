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
            System.out.println(currentNode);
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private List<Node> getPath(Node currentNode) {
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
        Node lastNode = null;
        if(openList.size() > 0){
            lastNode = openList.poll();
            openList.add(lastNode);
        }
        addAdjacentUpperRow(currentNode, lastNode);
        addAdjacentMiddleRow(currentNode, lastNode);
        addAdjacentLowerRow(currentNode, lastNode);
    }

    private void addAdjacentLowerRow(Node currentNode, Node lastNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            checkNode(currentNode, lastNode, col, lowerRow, getHvCost(), "down");
        }
    }

    private void addAdjacentMiddleRow(Node currentNode, Node lastNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, lastNode, col - 1, middleRow, getHvCost(), "left");
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, lastNode, col + 1, middleRow, getHvCost(), "right");
        }
    }

    private void addAdjacentUpperRow(Node currentNode, Node lastNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, lastNode, col, row - 1, getHvCost(), "up");
        }
    }

    private void checkNode(Node currentNode, Node lastNode, int col, int row, int cost, String direction) {
        if(lastNode==null){
            lastNode = new Node(0, 0);
            lastNode.setOrientation(Node.Orientation.VERTICAL);
        }
        if(lastNode.getOrientation() == Node.Orientation.VERTICAL) {
            Node adjacentNode = getSearchArea()[row][col];
            Node adjacentPlusOneNode = null;
            switch (direction) {
                case "up":
                    if(row - 1 >= 0) {adjacentPlusOneNode = getSearchArea()[row - 1][col];}
                    break;
                case "down":
                    if(row + 1 < getSearchArea().length) {adjacentPlusOneNode = getSearchArea()[row + 1][col];}
                    break;
                case "left":
                    if(col - 1 >= 0) {adjacentPlusOneNode = getSearchArea()[row][col - 1];}
                    break;
                case "right":
                    if(col + 1 < getSearchArea()[0].length) {adjacentPlusOneNode = getSearchArea()[row][col + 1];}
                    break;
                default:
                    return;
            }
            if(adjacentPlusOneNode == null){return;}

            if (!adjacentNode.isBlock() && !adjacentPlusOneNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
                if (!getOpenList().contains(adjacentNode)) {
                    adjacentNode.setNodeData(currentNode, cost);
                    getOpenList().add(adjacentNode);
                } else {
                    boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                    if (changed) {
                        // Remove and Add the changed node, so that the PriorityQueue can sort again its
                        // contents with the modified "finalCost" value of the modified node
                        getOpenList().remove(adjacentNode);
                        getOpenList().add(adjacentNode);
                    }
                }
            }
        }else{
            Node adjacentNode = getSearchArea()[row][col];
            if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
                if (!getOpenList().contains(adjacentNode)) {
                    adjacentNode.setNodeData(currentNode, cost);
                    getOpenList().add(adjacentNode);
                } else {
                    boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                    if (changed) {
                        // Remove and Add the changed node, so that the PriorityQueue can sort again its
                        // contents with the modified "finalCost" value of the modified node
                        getOpenList().remove(adjacentNode);
                        getOpenList().add(adjacentNode);
                    }
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

