import java.util.List;

public class Main {

    private static int IDDFS_MAX_SEARCH_DISTANCE = 20;

    static String boardName = "board10.txt";
    public static void main(String[] args) throws CloneNotSupportedException {

        runAStar();
        runBFS();
        runDFS();
    }

    private static void runAStar() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               A STAR                ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        AStar AStar = new AStar(mBoard.getSizeX(), mBoard.getSizeY(), initialNode, finalNode, mBoard);
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

    private static void runBFS() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("                BFS                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);
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

    private static void runDFS() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("                IDDFS                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);
        mBoard.buildMatrixFromFile(boardName);
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        IDDFS IDDFS = new IDDFS(initialNode, finalNode, mBoard, IDDFS_MAX_SEARCH_DISTANCE);
        List<Node> path = IDDFS.findPath();


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("NODES CREATED = " + IDDFS.getMovesMade());
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
