import java.io.File;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);

        mBoard.buildMatrixFromFile("board.txt");
    }
}
