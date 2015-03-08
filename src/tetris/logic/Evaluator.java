/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris.logic;

import tetris.*;

import java.util.List;

/**
 * Evaluator
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 15:00)
 */
public class Evaluator {
    public EvaluationState getEvaluation(Board board, List<Integer> linesCleared) {
        int badCnt = 0;
        for (int col = 0; col < board.getWidth(); col++) {
            boolean found = false;
            for (int row = 0; row < board.getHeight(); row++) {
                if (board.get(row, col)) {
                    found = true;
                } else {
                    if (found) {
                        badCnt++;
                    }
                }
            }
        }
        int flatRate = 0;
        for (int i = 0; i < board.getWidth() - 1; i++) {
            flatRate += Math.abs(board.getTopRowInColumn(i) - board.getTopRowInColumn(i + 1));
        }
        int nonTetrisLinesCleared = 0;
        for (int v : linesCleared) {
            if (v != 0 && v != 4) {
                nonTetrisLinesCleared++;
            }
        }
        return new EvaluationState(badCnt, flatRate, nonTetrisLinesCleared);
    }
}