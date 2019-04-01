public class Node implements Cloneable{

    private int id;
    private int g;
    private int f;
    private int h;
    private int row;
    private int col;
    private boolean isBlock;
    private Node parent;
    private int type;
    private int moves;
    private Orientation orientation;
    private Orientation moveDirection;
    private Boolean visisted = false;
    enum Orientation{
        VERTICAL,
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public Node(int row, int col, int type) {
        super();
        this.row = row;
        this.col = col;
        this.type = type;
        this.id = -1;
        this.moves = 0;
        this.g = 1;
        this.h = 1;
    }
    public Node(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.type = -1;
        this.g = 1;
        this.h = 1;
    }
    public Node() {
        super();
    }

    public void calculateHeuristic(Node finalNode) {
        this.h = Math.abs(finalNode.getRow() - getRow()) + Math.abs(finalNode.getCol() - getCol());
        calculateFinalCost();
    }

    public void setNodeData(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    private void calculateFinalCost() {
            this.f = getG() + getH();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            Node other = (Node) o;
            return this.getRow() == other.getRow() && this.getCol() == other.getCol() && this.getOrientation() == other.getOrientation();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Node [row=" + row + ", col=" + col + "]";
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getType() {
        return type;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMoveDirection(Orientation moveDirection) {
        this.moveDirection = moveDirection;
    }

    public Orientation getMoveDirection() {
        return moveDirection;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void setVisisted(Boolean visisted) {
        this.visisted = visisted;
    }
}
