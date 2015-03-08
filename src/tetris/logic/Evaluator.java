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
            int diff = Math.abs(board.getTopRowInColumn(i) - board.getTopRowInColumn(i + 1));
            flatRate += diff;
        }
        int holeCnt = 0;
        for (int i = 0; i < board.getWidth() - 1; i++) {
            int left = i == 0 ? 999 : board.getColumnHeight(i - 1);
            int mid = board.getColumnHeight(i);
            int right = board.getColumnHeight(i + 1);
            if (mid < Math.min(left, right) - 2) {
                holeCnt++;
            }
        }
        int nonTetrisLinesCleared = 0;
        int tetrisLinesCleared = 0;
        for (int v : linesCleared) {
            if (v != 0 && v != 4) {
                nonTetrisLinesCleared++;
            } else if (v == 4) {
                tetrisLinesCleared++;
            }
        }
        int lastColumnHeight = board.getColumnHeight(board.getWidth() - 1);
        int maxColumnHeight = 0;
        for (int i = 0; i < board.getWidth(); i++) {
            maxColumnHeight = Math.max(maxColumnHeight, board.getColumnHeight(i));
        }
        return new EvaluationState(badCnt, flatRate, nonTetrisLinesCleared, tetrisLinesCleared, lastColumnHeight, holeCnt, maxColumnHeight > board.getHeight() - 4);
    }
}