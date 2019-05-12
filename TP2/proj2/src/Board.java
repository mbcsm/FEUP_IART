import java.io.File;
import java.util.Scanner;

/**
 * The Board class is responsible from both parsing the board file
 * into a usable matrix and printing it onto the console
 */
public class Board {

    int sizeX,
        sizeY;

    Node    finalCell,
            initialCell;

    public List<Values> values = new ArrayList<Values>();

    public Board(){}

    /**
     * Parses the Board file into a matrix
     */
    public void buildMatrixFromFile(String file) {
        try {
            Scanner input = new Scanner(new File("src/board/" + file));
            sizeX = input.nextInt();
            sizeY = input.nextInt();
            while (input.hasNextLine()) {
                System.out.println("Dataset Loaded Successfully!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
