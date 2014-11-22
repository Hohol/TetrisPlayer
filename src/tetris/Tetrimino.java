/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

/**
 * Tetrimino
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 13:37)
 */
public class Tetrimino {
    private final int topRow, leftCol;
    private final boolean[][] b;

    public Tetrimino(int topRow, int leftCol, boolean[][] b) {
        this.topRow = topRow;
        this.leftCol = leftCol;
        this.b = b;
    }

    public int getTopRow() {
        return topRow;
    }

    public int getLeftCol() {
        return leftCol;
    }

    public int getWidth() {
        return b[0].length;
    }

    public int getHeight() {
        return b.length;
    }

    public boolean get(int row, int col) {
        return b[row][col];
    }
}