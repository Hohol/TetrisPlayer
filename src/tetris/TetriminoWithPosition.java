/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

/**
 * Tetrimino
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 13:37)
 */
public class TetriminoWithPosition {
    private final int topRow, leftCol;
    private final Tetrimino tetrimino;

    public TetriminoWithPosition(int topRow, int leftCol, Tetrimino tetrimino) {
        this.topRow = topRow;
        this.leftCol = leftCol;
        this.tetrimino = tetrimino;
    }

    public int getTopRow() {
        return topRow;
    }

    public int getLeftCol() {
        return leftCol;
    }

    public Tetrimino getTetrimino() {
        return tetrimino;
    }
}