package ACTV1;

import java.util.List;

/**
 * Main Class Of the project, everything runs from here.
 * All the classes of the algorithms used are called
 * and the constants used by them are also set at the top, you are
 * welcomed to change them.
 *
 */
public class Main {
    private static int A_VERTICAL_COST = 10;
    private static int A_HORIZONTAL_COST = 10;

    private static String boardName = "board1.txt";

    public static void main(String[] args) throws CloneNotSupportedException {
        runAStar();
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
        Node emptyCell = mBoard.getEmptyCell();
        AStar AStar = new AStar(emptyCell, mBoard);
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
}
