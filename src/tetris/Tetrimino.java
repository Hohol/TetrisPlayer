/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.util.Arrays;

public class Tetrimino {
    public static final Tetrimino T = new Tetrimino(
            "" +
                    ".x.\n" +
                    "xxx"

    );
    public static final Tetrimino O = new Tetrimino(
            "" +
                    "xx\n" +
                    "xx"
    );
    public static final Tetrimino J = new Tetrimino(
            "" +
                    "x..\n" +
                    "xxx"
    );
    public static final Tetrimino S = new Tetrimino(
            "" +
                    ".xx\n" +
                    "xx."
    );
    public static final Tetrimino I = new Tetrimino(
            "" +
                    "xxxx"
    );
    public static final Tetrimino Z = new Tetrimino(
            "" +
                    "xx.\n" +
                    ".xx"
    );
    public static final Tetrimino L = new Tetrimino(
            "" +
                    "..x\n" +
                    "xxx"
    );
    public static Tetrimino[] ALL = {I, O, J, L, T, S, Z,};

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

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (boolean[] aB : b) {
            for (int j = 0; j < b[0].length; j++) {
                if (aB[j]) {
                    r.append("x");
                } else {
                    r.append(".");
                }
            }
            r.append("\n");
        }
        return r.toString();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        Tetrimino t = (Tetrimino) o;
        return Arrays.deepEquals(b, t.b);
    }
}