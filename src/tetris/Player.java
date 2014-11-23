package tetris;

public class Player {
    public void play() throws Throwable {
        KeyPresser keyPresser = new KeyPresser();
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        Board previousBoard = null;
        //noinspection InfiniteLoopStatement
        while (true) {
            Thread.sleep(30);
            GameState gameState = gameStateReader.readGameState();
            Board board = gameState.getBoard();

            boolean same = board.equals(previousBoard);
            previousBoard = board;
            if (same) {
                continue;
            }
            System.out.println(board);
            System.out.println(gameState.getNextTetriminoes());
            if (true) {
                //continue;
            }
            TetriminoWithPosition tetrimino = board.extractFallingTetrimino();
            if (tetrimino == null) {
                //System.out.println("skip");
                continue;
            }
            Move bestMove = bestMoveFinder.findBestMove(gameState, tetrimino);
            //System.out.println(bestMove);
            keyPresser.makeMove(bestMove);
        }
    }
}
