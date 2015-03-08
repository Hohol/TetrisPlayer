package tetris;

import tetris.logic.BestMoveFinder;

public class Player {
    public void play() throws Throwable {
        KeyPresser keyPresser = new KeyPresser();
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder(2);
        GameState previousState = null;
        //noinspection InfiniteLoopStatement
        while (true) {
            Thread.sleep(30);
            GameState gameState = gameStateReader.readGameState();
            Board board = gameState.getBoard();

            if (broken(gameState)) {
                continue;
            }
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

    private boolean broken(GameState gameState) {
        for (Tetrimino tetrimino : gameState.getNextTetriminoes()) {
            if (tetrimino == null) {
                return true;
            }
        }
        return false;
    }
}
