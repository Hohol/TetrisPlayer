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
    private final int row, col;
    private final boolean[][] b;

    public Tetrimino(int row, int col, boolean[][] b) {
        this.row = row;
        this.col = col;
        this.b = b;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}