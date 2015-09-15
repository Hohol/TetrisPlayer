package tetris;

import tetris.logic.BestMoveFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tetris.Move.*;

public class Player {
    public void play() throws Throwable {
        KeyPresser keyPresser = new KeyPresser();
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder(1);
        GameState previousState = null;
        //noinspection InfiniteLoopStatement
        gameStateReader.findBoard();
        ColumnAndOrientationOrStash target = null;
        boolean stashAllowed = true;
        while (true) {
            Thread.sleep(40);

            GameState gameState = gameStateReader.readGameState();
            if (broken(gameState)) {
                keyPresser.makeMove(Arrays.asList(Move.ENTER));
                gameStateReader.findBoard();
                Thread.sleep(500);
                continue;
            }
            Board board = gameState.getBoard();
            System.out.println(board);
            System.out.println(gameState.getNextTetriminoes());
            TetriminoWithPosition twp = gameState.getFallingTetrimino();
            if (twp == null) {
                continue;
            }
            Tetrimino tetrimino = twp.getTetrimino();
            if (target == null || wrongTetrimino(target.getTetrimino(), tetrimino)) {
                target = bestMoveFinder.findBestMove(gameState, twp, stashAllowed);
                if (target == null) {
                    continue;
                }
            }

            List<Move> moves = new ArrayList<>();
            if (target.isStash()) {
                moves.add(STASH);
                target = null;
                stashAllowed = false;
            } else {
                if (!tetrimino.equals(target.getTetrimino())) {
                    if (canReachInOneOrTwoCWRotations(tetrimino, target.getTetrimino())) {
                        moves.add(ROTATE_CW);
                    } else {
                        moves.add(ROTATE_CCW);
                    }
                }
                if (target.getColumn() > twp.getLeftCol()) {
                    moves.add(RIGHT);
                } else if (target.getColumn() < twp.getLeftCol()) {
                    moves.add(LEFT);
                }
                if (moves.isEmpty()) {
                    moves.add(DROP);
                    target = null;
                    stashAllowed = true;
                }
            }
            keyPresser.makeMove(moves);
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
        if (gameState == null) {
            return true;
        }
        for (Tetrimino tetrimino : gameState.getNextTetriminoes()) {
            if (tetrimino == null || tetrimino.getWidth() == 4 && tetrimino.getHeight() == 4) {
                return true;
            }
        }
        return false;
    }
}
