package ACTV1;

public class Node implements Cloneable{

    private int g;
    private int f;
    private int h;
    private int row;
    private int col;
    private int finalRow;
    private int finalCol;
    private Node parent;
    private Orientation moveDirection;
    int number;
    Node[][] board;


    enum Orientation {
        EAST,
        NORTH,
        SOUTH,
        WEST

    }

    Node(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.g = 1;
        this.h = 1;
        setFinalPos();
    }

    void calculateHeuristic() {
        this.h = Math.abs(finalRow - getRow()) + Math.abs(finalCol - getCol());
        calculateFinalCost();
    }
    void setFinalPos(){
        int number = 1;
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; true; i++){
                if(this.number == number && i != this.board.length-1 && j != this.board.length-1){
                    finalRow = i;
                    finalCol = j;
                }
            }
        }
    }

    void setNodeData(Node currentNode, int cost) {
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
            return this.getRow() == other.getRow() && this.getCol() == other.getCol();
        }
        return false;
    }


    int getH() {
        return h;
    }
    int getG() {
        return g;
    }
    int getF() {
        return f;
    }
    int getRow() {
        return row;
    }
    int getCol() {
        return col;
    }
    Node getParent() {
        return parent;
    }
    Orientation getMoveDirection() {
        return moveDirection;
    }
    public Node[][] getBoard() {
        return board;
    }
    public int getNumber() {
        return number;
    }

    void setG(int g) {
        this.g = g;
    }
    void setParent(Node parent) {
        this.parent = parent;
    }
    void setMoveDirection(Orientation moveDirection) {
        this.moveDirection = moveDirection;
    }
    public void setBoard(Node[][] board) {
        this.board = board;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Node [row=" + row + ", col=" + col + "]";
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
