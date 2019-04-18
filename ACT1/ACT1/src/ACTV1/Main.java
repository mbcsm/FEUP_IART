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
    private static String boardName = "board4.txt";

    public static void main(String[] args) throws CloneNotSupportedException {
        runAStar();
        runBFS();
    }

    /**
     * Runs the A* Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static void runAStar() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               A STAR                ");
        System.out.println("=====================================");


        Board mBoard = new Board();
        mBoard.buildMatrixFromFile(boardName);
        Node emptyCell = mBoard.getEmptyCell();
        AStar AStar = new AStar(emptyCell, mBoard);
        long startTime = System.currentTimeMillis();
        List<Node> path = AStar.findPath();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("NODES CREATED = " + AStar.getMovesMade());
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
            System.out.println(node);
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
        Node emptyCell = mBoard.getEmptyCell();
        BFS BFS = new BFS(emptyCell, mBoard);
        List<Node> path = BFS.findPath();


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("NODES CREATED = " + BFS.getMovesMade());
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
                System.out.println(node);
        }
    }
}
