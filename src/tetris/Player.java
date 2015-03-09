package tetris;

import tetris.logic.BestMoveFinder;

import static tetris.Move.*;

public class Player {
    public void play() throws Throwable {
        KeyPresser keyPresser = new KeyPresser();
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder(2);
        GameState previousState = null;
        //noinspection InfiniteLoopStatement
        ColumnAndOrientation target = null;
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
            TetriminoWithPosition twp = gameState.getFallingTetrimino();
            if (twp == null) {
                continue;
            }
            Tetrimino tetrimino = twp.getTetrimino();
            if (target == null || wrongTetrimino(target.getTetrimino(), tetrimino)) {
                target = bestMoveFinder.findBestMove(gameState, twp);
                if (target == null) {
                    continue;
                }
            }

            Move move;
            if (!tetrimino.equals(target.getTetrimino())) {
                if (canReachInOneOrTwoCWRotations(tetrimino, target.getTetrimino())) {
                    move = ROTATE_CW;
                } else {
                    move = ROTATE_CCW;
                }
            } else if (target.getColumn() > twp.getLeftCol()) {
                move = RIGHT;
            } else if (target.getColumn() < twp.getLeftCol()) {
                move = LEFT;
            } else {
                move = DROP;
                target = null;
            }
            keyPresser.makeMove(move);
            System.out.println(target);
            System.out.println("------");
        }
    }

    private boolean wrongTetrimino(Tetrimino tetrimino, Tetrimino target) {
        for (int i = 0; i < 4; i++) {
            if (tetrimino.equals(target)) {
                return false;
            }
            tetrimino = tetrimino.rotateCW();
        }
        return true;
    }

    private boolean canReachInOneOrTwoCWRotations(Tetrimino tetrimino, Tetrimino target) {
        for (int i = 0; i < 2; i++) {
            tetrimino = tetrimino.rotateCW();
            if (tetrimino.equals(target)) {
                return true;
            }
        }
        return false;
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
