/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris.logic;

import tetris.*;

import java.util.ArrayList;
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
    private final int depthLimit;

    public BestMoveFinder(int depthLimit) {
        this.depthLimit = depthLimit;
        this.evaluator = new Evaluator();
    }

    public Move findBestMove(GameState gameState, TetriminoWithPosition tetriminoWithPosition) {
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

    public ActionWithEvaluation findBestAction(Board board, Tetrimino tetrimino, List<Tetrimino> nextTetriminoes, int nextPosition, List<Integer> linesCleared, int depth) {
        EvaluationState bestState = null;
        Action bestAction = null;

        Tetrimino originalTetrimino = tetrimino;
        for (int rotateCnt = 0; rotateCnt < 4; rotateCnt++) {
            for (int newLeftCol = 0; newLeftCol + tetrimino.getWidth() - 1 < board.getWidth(); newLeftCol++) {
                DropResult dropResult = board.drop(tetrimino, newLeftCol);
                if (dropResult == null) {
                    continue;
                }
                Board newBoard = dropResult.getBoard();
                linesCleared.add(dropResult.getLinesCleared());

                EvaluationState curState;

                if (nextPosition == nextTetriminoes.size() || depth == depthLimit) {
                    curState = evaluator.getEvaluation(newBoard, linesCleared);
                } else {
                    curState = findBestAction(newBoard, nextTetriminoes.get(nextPosition), nextTetriminoes, nextPosition + 1, linesCleared, depth + 1).getState();
                }
                if (curState != null && curState.better(bestState)) {
                    bestState = curState;
                    bestAction = new Action(newLeftCol, rotateCnt);
                }
                linesCleared.remove(linesCleared.size() - 1);
            }
            tetrimino = tetrimino.rotateCW();
            if (tetrimino.equals(originalTetrimino)) {
                break;
            }
        }
        return new ActionWithEvaluation(bestAction, bestState);
    }

    public ActionWithEvaluation findBestAction(Board board, Tetrimino tetrimino) {
        return findBestAction(board, tetrimino, Collections.<Tetrimino>emptyList(), 0);
    }

    public ActionWithEvaluation findBestAction(Board board, Tetrimino tetrimino, List<Tetrimino> tetriminoes, int nextPosition) {
        return findBestAction(board, tetrimino, tetriminoes, nextPosition, new ArrayList<Integer>(), 0);
    }
}