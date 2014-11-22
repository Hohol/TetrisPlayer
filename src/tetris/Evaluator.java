/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

/**
 * Evaluator
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 15:00)
 */
public class Evaluator {

    // greater is better
    public double evaluate(Board board) {
        int minTopRow = 9999;
        for (int col = 0; col < board.getWidth(); col++) {
            minTopRow = Math.min(minTopRow, board.getTopRowInColumn(col));
        }
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
        return minTopRow * 100 - badCnt;
    }
}