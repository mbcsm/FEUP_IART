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

    Node[][] board;

    public Board(){}

    /**
     * Parses the Board file into a matrix
     */
    public void buildMatrixFromFile(String file) {
        try {
            Scanner input = new Scanner(new File("src/board/" + file));
            sizeX = input.nextInt();
            sizeY = input.nextInt();
            board = new Node[sizeX][sizeY];
            while (input.hasNextLine()) {
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        try {
                            board[i][j] = new Node(i, j, input.nextInt());

                            if (board[i][j].getType() == 3) {
                                initialCell = board[i][j];
                                initialCell.setOrientation(Node.Orientation.VERTICAL);
                            }
                            if (board[i][j].getType() == 4) { finalCell = board[i][j]; }
                        } catch (java.util.NoSuchElementException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Board Loaded Successfully!");
                printBoard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBoard(){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                System.out.print("|" + board[i][j].getType());
            }
            System.out.println("|");
        }
    }

    Node getFinalCell() {
        return finalCell;
    }
    public Node getInitialCell() {
        return initialCell;
    }
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public Node[][] getBoard() {
        return board;
    }
}
