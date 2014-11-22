package tetris;

import java.util.Arrays;

import static java.lang.Math.*;

public class Board {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 21;

    private final boolean board[][] = new boolean[HEIGHT][WIDTH];

    public void set(int row, int col, boolean value) {
        board[row][col] = value;
    }

    public boolean get(int row, int col) {
        return board[row][col];
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j]) {
                    r.append("x");
                } else {
                    r.append(".");
                }
            }
            r.append("\n");
        }
        return r.toString();
    }

    public Tetrimino extractFallingTetrimino() {
        int cnt = 0;
        int minX = 999;
        int minY = 999;
        int maxX = 0;
        int maxY = 0;
        fori:
        for (int i = 0; i < HEIGHT; i++) {
            boolean foundOnLine = false;
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j]) {
                    foundOnLine = true;
                    cnt++;
                    minX = min(minX, i);
                    minY = min(minY, j);
                    maxX = max(maxX, i);
                    maxY = max(maxY, j);
                    if (cnt == 4) {
                        break fori;
                    }
                }
            }
            if (cnt > 0 && !foundOnLine) {
                return null;
            }
        }
        if (cnt != 4) {
            return null;
        }
        boolean[][] b = new boolean[maxX - minX + 1][maxY - minY + 1];
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                b[i - minX][j - minY] = board[i][j];
                board[i][j] = false;
            }
        }
        return new Tetrimino(minX, minY, b);
    }
}
