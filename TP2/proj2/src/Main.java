import java.util.List;

/**
 * Main Class Of the project, everything runs from here.
 * All the classes of the algorithms used are called
 * and the constants used by them are also set at the top, you are
 * welcomed to change them.
 *
 */
public class Main {

    private static String datasetName = "board10.txt";

    public static void main(String[] args) throws CloneNotSupportedException {
        runC45();
    }

    /**
     * Runs the A* Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static void runC45() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               C4.5                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Board mBoard = new Board();
        mBoard.buildMatrixFromFile(datasetName);

        C45 C45 = new C45();


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("TIME SPENT = " + elapsedTime + "ms");
    }

}
