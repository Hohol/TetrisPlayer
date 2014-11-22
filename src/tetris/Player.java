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
            Board board = gameStateReader.readGameState().getBoard();
            System.out.println(board);
            TetriminoWithPosition tetrimino = board.extractFallingTetrimino();
            if (tetrimino == null) {
                System.out.println("skip");
                continue;
            }
            Move bestMove = bestMoveFinder.findBestMove(board, tetrimino);
            System.out.println(bestMove);
            keyPresser.makeMove(bestMove);
            //Thread.sleep(100);
        }
    }
}
