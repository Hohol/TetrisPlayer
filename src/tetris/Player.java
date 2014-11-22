package tetris;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Player {
    public void play() throws Throwable {
        KeyPresser keyPresser = new KeyPresser();
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        //noinspection InfiniteLoopStatement
        while (true) {
            GameState gameState = gameStateReader.readGameState();
            Board board = gameState.getBoard();
            System.out.println(board);
            TetriminoWithPosition tetrimino = board.extractFallingTetrimino();
            if (tetrimino == null) {
                System.out.println("skip");
                continue;
            }
            Move bestMove = bestMoveFinder.findBestMove(gameState, tetrimino);
            System.out.println(bestMove);
            keyPresser.makeMove(bestMove);
        }
    }
}
