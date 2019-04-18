import java.util.List;

/**
 * Main Class Of the project, everything runs from here.
 * All the classes of the algorithms used are called
 * and the constants used by them are also set at the top, you are
 * welcomed to change them.
 *
 */
public class Main {

    private static int DLS_MAX_SEARCH_DISTANCE = 20;
    private static int A_VERTICAL_COST = 5;
    private static int A_HORIZONTAL_COST = 10;

    private static String boardName = "board10.txt";

    public static void main(String[] args) throws CloneNotSupportedException {
        runAStar();
        runBFS();
        runDLS();
    }

    /**
     * Runs the A* Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static void runAStar() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               A STAR                ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Board mBoard = new Board();
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        AStar AStar = new AStar(initialNode, finalNode, mBoard, A_VERTICAL_COST, A_HORIZONTAL_COST);
        AStar.setBlocks(mBoard);
        List<Node> path = AStar.findPath();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("NODES CREATED = " + AStar.getMovesMade());
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
            System.out.println(node + " / " +  node.getMoveDirection());
        }
    }

    /**
     * Runs the BFS Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static void runBFS() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("                BFS                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Board mBoard = new Board();
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        BFS BFS = new BFS(initialNode, finalNode, mBoard);
        List<Node> path = BFS.findPath();


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("NODES CREATED = " + BFS.getMovesMade());
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
                System.out.println(node + " / " +  node.getMoveDirection());
        }
    }

    /**
     * Runs the DLS Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static void runDLS() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("                DLS                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Board mBoard = new Board();
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        DLS DLS = new DLS(initialNode, finalNode, mBoard, DLS_MAX_SEARCH_DISTANCE);
        List<Node> path = DLS.findPath();


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("NODES CREATED = " + DLS.getMovesMade());
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        if(path == null){
            System.out.println("ERROR 404: couldn't find path, you need to let me go deeper");
        }else{
            for (Node node : path) {
                if(node.getMoveDirection() != null)
                    System.out.println(node + " / " +  node.getMoveDirection());
            }
        }
    }
}
