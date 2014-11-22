/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

/**
 * Tetrimino
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 17:32)
 */
public class Tetrimino {
    private final boolean[][] b;

    public Tetrimino(boolean[][] b) {
        this.b = b;
    }

    public Tetrimino(String s) {
        String[] a = s.split("\n");
        b = new boolean[a.length][a[0].length()];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                b[i][j] = (a[i].charAt(j) == 'x');
            }
        }
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

    public Tetrimino rotateCW() {
        boolean[][] newB = new boolean[b[0].length][b.length];
        for (int newRow = 0; newRow < newB.length; newRow++) {
            for (int newCol = 0; newCol < newB[0].length; newCol++) {
                newB[newRow][newCol] = b[newB[0].length - newCol - 1][newRow];
            }
        }
        return new Tetrimino(newB);
    }
}