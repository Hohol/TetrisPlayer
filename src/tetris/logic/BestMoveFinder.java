/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris.logic;

import tetris.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BestMoveFinder
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 14:30)
 */
public class BestMoveFinder {

    private final Evaluator evaluator;
    private final int depthLimit;

    public BestMoveFinder(int depthLimit) {
        this.depthLimit = depthLimit;
        this.evaluator = new Evaluator();
    }

    public ColumnAndOrientationOrStash findBestMove(GameState gameState, TetriminoWithPosition tetriminoWithPosition, boolean stashAllowed) {
        Action bestAction = findBestAction(gameState.getBoard(), gameState.getTetriminoInStash(), stashAllowed, tetriminoWithPosition.getTetrimino(), gameState.getNextTetriminoes(), 0).getAction();

        if (bestAction == null) {
            return null;
        }
        if (bestAction.isStash()) {
            return new ColumnAndOrientationOrStash(true);
        } else {
            Tetrimino tetrimino = tetriminoWithPosition.getTetrimino();
            for (int i = 0; i < bestAction.getCwRotationCnt(); i++) {
                tetrimino = tetrimino.rotateCW();
            }
            return new ColumnAndOrientationOrStash(bestAction.getNewLeftCol(), tetrimino);
        }
    }

    public ActionWithEvaluation findBestAction(Board board, Tetrimino tetriminoInStash, boolean stashAllowed, Tetrimino fallingTetrimino, List<Tetrimino> nextTetriminoes, int nextPosition, List<Integer> linesCleared, int depth) {
        EvaluationState bestState = null;
        Action bestAction = null;

        Tetrimino originalTetrimino = fallingTetrimino;
        for (int rotateCnt = 0; rotateCnt < 4; rotateCnt++) {
            for (int newLeftCol = 0; newLeftCol + fallingTetrimino.getWidth() - 1 < board.getWidth(); newLeftCol++) {
                DropResult dropResult = board.drop(fallingTetrimino, newLeftCol);
                if (dropResult == null) {
                    continue;
                }
                Board newBoard = dropResult.getBoard();
                linesCleared.add(dropResult.getLinesCleared());

                EvaluationState curState;

                if (nextPosition == nextTetriminoes.size() || depth == depthLimit) {
                    curState = evaluator.getEvaluation(newBoard, linesCleared);
                } else {
                    curState = findBestAction(newBoard, tetriminoInStash, true, nextTetriminoes.get(nextPosition), nextTetriminoes, nextPosition + 1, linesCleared, depth + 1).getState();
                }
                if (curState != null && curState.better(bestState)) {
                    bestState = curState;
                    bestAction = new Action(newLeftCol, rotateCnt);
                }
                linesCleared.remove(linesCleared.size() - 1);
            }
            fallingTetrimino = fallingTetrimino.rotateCW();
            if (fallingTetrimino.equals(originalTetrimino)) {
                break;
            }
        }
        if (stashAllowed && (tetriminoInStash != null || nextPosition != nextTetriminoes.size())) {
            if (tetriminoInStash == null) {
                tetriminoInStash = fallingTetrimino;
                fallingTetrimino = nextTetriminoes.get(nextPosition);
                nextPosition++;
            } else {
                Tetrimino oldTetriminoInStash = tetriminoInStash;
                tetriminoInStash = fallingTetrimino;
                fallingTetrimino = oldTetriminoInStash;
            }

            EvaluationState curState = findBestAction(board, tetriminoInStash, false, fallingTetrimino, nextTetriminoes, nextPosition, linesCleared, depth).getState();
            if (curState.better(bestState)) {
                bestState = curState;
                bestAction = new Action(true);
            }
        }
        return new ActionWithEvaluation(bestAction, bestState);
    }

    public ActionWithEvaluation findBestAction(Board board, Tetrimino tetrimino) {
        return findBestAction(board, null, true, tetrimino, Collections.<Tetrimino>emptyList(), 0);
    }

    public ActionWithEvaluation findBestAction(Board board, Tetrimino tetriminoInStash, boolean stashAllowed, Tetrimino tetrimino, List<Tetrimino> tetriminoes, int nextPosition) {
        return findBestAction(board, tetriminoInStash, stashAllowed, tetrimino, tetriminoes, nextPosition, new ArrayList<>(), 0);
    }
}