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
        //System.out.println(board);
        int bestNewLeftCol = -1;
        int bestRotateCnt = -1;
        double maxEval = Double.NEGATIVE_INFINITY;

        for (int rotateCnt = 0; rotateCnt < 4; rotateCnt++) {
            for (int newLeftCol = 0; newLeftCol + tetrimino.getWidth() - 1 < board.getWidth(); newLeftCol++) {
                Board newBoard = board.drop(tetrimino, newLeftCol);
                //System.out.println(newBoard);
                double curEval = evaluator.evaluate(newBoard);
                if (curEval > maxEval) {
                    maxEval = curEval;
                    bestNewLeftCol = newLeftCol;
                    bestRotateCnt = rotateCnt;
                }
            }
            tetrimino = tetrimino.rotateCW();
        }

        if (bestRotateCnt > 0) {
            return ROTATE_CW;
        } else if (bestNewLeftCol < tetrimino.getLeftCol()) {
            return LEFT;
        } else if (bestNewLeftCol > tetrimino.getLeftCol()) {
            return RIGHT;
        } else {
            return DROP;
        }
    }
}