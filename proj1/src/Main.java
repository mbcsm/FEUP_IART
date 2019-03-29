import java.util.List;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        runAStar();

    }

    private static void runAStar() throws CloneNotSupportedException {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        Block mBlock = new Block();
        Board mBoard = new Board(mBlock);
        mBoard.buildMatrixFromFile("board5.txt");
        Node initialNode = mBoard.getInitialCell();
        Node finalNode = mBoard.getFinalCell();
        AI AI = new AI(mBoard.getSizeX(), mBoard.getSizeY(), initialNode, finalNode, mBoard);
        AI.setBlocks(mBoard);
        List<Node> path = AI.findPath();

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("MOVES NEEDED = " + AI.getMovesMade());
        System.out.println("MEMORY USED = " + actualMemUsed/1000000 + "MB");
        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        for (Node node : path) {
            if(node.getMoveDirection() != null)
            System.out.println(node + " / " +  node.getMoveDirection() + "   |   " + node.getOrientation() );
        }
    }
}
