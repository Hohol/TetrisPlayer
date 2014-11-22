package tetris;

import java.util.Arrays;

public class Board {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;

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
}
