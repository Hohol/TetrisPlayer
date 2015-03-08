package tetris;

public class Player {
    public void play() throws Throwable {
        KeyPresser keyPresser = new KeyPresser();
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        GameState previousState = null;
        //noinspection InfiniteLoopStatement
        while (true) {
            Thread.sleep(30);
            GameState gameState = gameStateReader.readGameState();
            Board board = gameState.getBoard();

            boolean same = gameState.equals(previousState);
            previousState = gameState;
            if (same) {
                continue;
            }
            System.out.println(board);
            System.out.println(gameState.getNextTetriminoes());
            TetriminoWithPosition tetrimino = gameState.getFallingTetrimino();
            if (tetrimino == null) {
                //System.out.println("skip");
                continue;
            }
            Move bestMove = bestMoveFinder.findBestMove(gameState, tetrimino);
            //System.out.println(bestMove);
            keyPresser.makeMove(bestMove);
            System.out.println("---------\n");
        }
    }
}
