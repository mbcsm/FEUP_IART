package ACTV1;

import java.io.File;
import java.util.Scanner;

/**
 * The Board class is responsible from both parsing the board file
 * into a usable matrix and printing it onto the console
 */
public class Board {

    int size;

    Node emptyCell;

    Node[][] board;

    Board(){}

    /**
     * Parses the Board file into a matrix
     */
    void buildMatrixFromFile(String file) {
        try {
            Scanner input = new Scanner(new File("src/board/" + file));
            size = input.nextInt();
            board = new Node[size][size];
            while (input.hasNextLine()) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        try {
                            board[i][j] = new Node(size, i, j);
                            board[i][j].setNumber(input.nextInt());
                            if (board[i][j].getNumber() == 0) { emptyCell = board[i][j]; }
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("|" + board[i][j].getNumber());
            }
            System.out.println("|");
        }
    }

    Node getEmptyCell() {
        return emptyCell;
    }
    Node[][] getBoard() {
        return board;
    }
}
