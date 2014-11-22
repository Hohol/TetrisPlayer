/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.util.Collections;
import java.util.List;

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

    Move findBestMove(GameState gameState, TetriminoWithPosition tetriminoWithPosition) {
        Action bestAction = findBestAction(gameState.getBoard(), tetriminoWithPosition.getTetrimino(), gameState.getNextTetriminoes(), 0).getAction();

        if (bestAction == null) {
            throw new RuntimeException("Best action not found");
        }

        if (bestAction.getCwRotationCnt() > 0) {
            return ROTATE_CW;
        } else {
            if (bestAction.getNewLeftCol() < tetriminoWithPosition.getLeftCol()) {
                return LEFT;
            } else if (bestAction.getNewLeftCol() > tetriminoWithPosition.getLeftCol()) {
                return RIGHT;
            } else {
                return DROP;
            }
        }
    }

    public ActionWithEval findBestAction(Board board, Tetrimino tetrimino, List<Tetrimino> nextTetriminoes, int nextPosition) {
        double maxProfit = Double.NEGATIVE_INFINITY;
        Action bestAction = null;

        for (int rotateCnt = 0; rotateCnt < 4; rotateCnt++) {
            for (int newLeftCol = 0; newLeftCol + tetrimino.getWidth() - 1 < board.getWidth(); newLeftCol++) {
                Board newBoard = board.drop(tetrimino, newLeftCol);
                if (newBoard == null) {
                    continue;
                }
                double curProfit;
                if (nextPosition == nextTetriminoes.size()) {
                    curProfit = evaluator.evaluate(newBoard);
                } else {
                    curProfit = findBestAction(newBoard, nextTetriminoes.get(nextPosition), nextTetriminoes, nextPosition + 1).getProfit();
                }
                if (curProfit > maxProfit) {
                    maxProfit = curProfit;
                    bestAction = new Action(newLeftCol, rotateCnt);
                }
            }
            tetrimino = tetrimino.rotateCW();
        }
        return new ActionWithEval(bestAction, maxProfit);
    }

    public ActionWithEval findBestAction(Board board, Tetrimino tetrimino) {
        return findBestAction(board, tetrimino, Collections.<Tetrimino>emptyList(), 0);
    }
}