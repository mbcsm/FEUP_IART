package ACTV1;

public class Node implements Cloneable{

    private int g;
    private int f;
    private int h;
    private int row;
    private int col;
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

    Node(int size, int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.g = 1;
        this.h = 1;
        this.board = new Node[size][size];
    }



    void setNodeData(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    void calculateHeuristic() {
        this.h = Math.abs(board.length - 1 - getRow()) + Math.abs(board.length - 1 - getCol());
        calculateFinalCost();
    }
    void calculateG(int x, int y, int originalX, int originalY) {
        g = 5;
        int it = 1;
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; i < this.board.length; i++) {
                if(this.board[i][j].getNumber() == it){
                    if(x == i && y == j){g = 0;}
                    if(originalX == i && originalY == j){g = 10;}
                }
                it++;
            }
        }
    }
    public void calculateFinalCost() {
            this.f = getG() + getH();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            Node other = (Node) o;
            for(int i = 0; i < this.board.length; i++){
                for(int j = 0; i < this.board.length; i++) {
                    if(this.getBoard()[i][j] != other.getBoard()[i][j]){
                        return false;
                    }
                }
            }
        }
        return true;
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
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                System.out.print("|" + board[i][j].getNumber());
            }
            System.out.println("|");
        }
        return "Node [MOVE=" + moveDirection + "]";
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
