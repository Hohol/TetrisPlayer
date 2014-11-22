/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import static tetris.Move.*;

/**
 * BestMoveFinder
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 14:30)
 */
public class BestMoveFinder {

    private final Evaluator evaluator;

    public BestMoveFinder() {
        this.evaluator = new Evaluator();
    }

    Move findBestMove(Board board, Tetrimino tetrimino) {
        Action bestAction = findBestAction(board, tetrimino);

        if (bestAction == null) {
            throw new RuntimeException("Best action not found");
        }

        if (bestAction.getCwRotationCnt() > 0) {
            return ROTATE_CW;
        } else {
            if (bestAction.getNewLeftCol() < tetrimino.getLeftCol()) {
                return LEFT;
            } else if (bestAction.getNewLeftCol() > tetrimino.getLeftCol()) {
                return RIGHT;
            } else {
                return DROP;
            }
        }
    }

    public Action findBestAction(Board board, Tetrimino tetrimino) {
        double maxEval = Double.NEGATIVE_INFINITY;

        Action bestAction = null;

        for (int rotateCnt = 0; rotateCnt < 4; rotateCnt++) {
            for (int newLeftCol = 0; newLeftCol + tetrimino.getWidth() - 1 < board.getWidth(); newLeftCol++) {
                Board newBoard = board.drop(tetrimino, newLeftCol);
                double curEval = evaluator.evaluate(newBoard);
                if (curEval > maxEval) {
                    maxEval = curEval;
                    bestAction = new Action(newLeftCol, rotateCnt);
                }
            }
            tetrimino = tetrimino.rotateCW();
        }
        return bestAction;
    }
}