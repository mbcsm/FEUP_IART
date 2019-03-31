import java.util.List;

public class Main {

    static String boardName = "board10.txt";
    public static void main(String[] args) throws CloneNotSupportedException {

        runAStar();
        runDFS();
    }

    private static void runAStar() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               A STAR                ");
        System.out.println("=====================================");

        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        AStar AStar = new AStar(mBoard.getSizeX(), mBoard.getSizeY(), initialNode, finalNode, mBoard);
        AStar.setBlocks(mBoard);
        List<Node> path = AStar.findPath();

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("MEMORY USED = " + actualMemUsed/1000000 + "MB");
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
            System.out.println(node + " / " +  node.getMoveDirection());
        }
    }

    private static void runDFS() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("                DFS                  ");
        System.out.println("=====================================");

        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        DFS DFS = new DFS(initialNode, finalNode, mBoard);
        List<Node> path = DFS.findPath();


        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("MEMORY USED = " + actualMemUsed/1000000 + "MB");
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
                System.out.println(node + " / " +  node.getMoveDirection());
        }
    }
}
