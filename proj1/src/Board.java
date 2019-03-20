import java.io.File;
import java.util.Scanner;

public class Board {

    Block mBlock;

    public Board(Block mBlock){
        this.mBlock = mBlock;
    }
    /**
     * Parses the Board file into a matrix
     */
    public void buildMatrixFromFile(String file) {
        try {
            Scanner input = new Scanner(new File("src/board/" + file));
            int m = input.nextInt();
            int n = input.nextInt();
            int[][] a = new int[m][n];
            while (input.hasNextLine()) {
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        try {
                            a[i][j] = input.nextInt();
                            if (a[i][j] == 3) {
                                mBlock.setPosX(i);
                            }
                        } catch (java.util.NoSuchElementException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Board Loaded Successfully!");
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print("|" + a[i][j]);
                    }
                    System.out.println("|");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
