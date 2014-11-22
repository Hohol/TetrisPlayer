package tetris;

import static java.lang.Math.*;

public class Board {
    public static final int STANDARD_HEIGHT = 21;
    public static final int STANDARD_WIDTH = 10;

    private final int height;
    private final int width;
    private final boolean b[][];

    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        b = new boolean[height][width];
    }

    public Board(Board board) {
        width = board.width;
        height = board.height;
        b = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            System.arraycopy(board.b[i], 0, b[i], 0, width);
        }
    }

    public Board(String s) {
        String[] a = s.split("\n");
        height = a.length;
        width = a[0].length();
        b = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                b[i][j] = (a[i].charAt(j) == 'x');
            }
        }
    }

    public void set(int row, int col, boolean value) {
        b[row][col] = value;
    }

    public boolean get(int row, int col) {
        return b[row][col];
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (b[i][j]) {
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
        int topRow = 999;
        int leftCol = 999;
        int bottomRow = 0;
        int rightCol = 0;
        fori:
        for (int i = 0; i < height; i++) {
            boolean foundOnLine = false;
            for (int j = 0; j < width; j++) {
                if (b[i][j]) {
                    foundOnLine = true;
                    cnt++;
                    topRow = min(topRow, i);
                    leftCol = min(leftCol, j);
                    bottomRow = max(bottomRow, i);
                    rightCol = max(rightCol, j);
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
        boolean[][] b = new boolean[bottomRow - topRow + 1][rightCol - leftCol + 1];
        for (int i = topRow; i <= bottomRow; i++) {
            for (int j = leftCol; j <= rightCol; j++) {
                b[i - topRow][j - leftCol] = this.b[i][j];
                this.b[i][j] = false;
            }
        }
        return new Tetrimino(topRow, leftCol, b);
    }

    public int getWidth() {
        return width;
    }

    public Board drop(Tetrimino tetrimino, int leftCol) {
        int minNewTopTetriminoRow = 999;
        for (int j = 0; j < tetrimino.getWidth(); j++) {
            int tetriminoBottomRow = 0;
            for (int i = tetrimino.getHeight() - 1; i >= 0; i--) {
                if (tetrimino.get(i, j)) {
                    tetriminoBottomRow = i;
                    break;
                }
            }
            int curCol = leftCol + j;

            int boardTopRow = getTopRowInColumn(curCol);
            int newTopTetriminoRow = boardTopRow - tetriminoBottomRow - 1;
            minNewTopTetriminoRow = min(minNewTopTetriminoRow, newTopTetriminoRow);
        }

        Board r = new Board(this);
        for (int i = 0; i < tetrimino.getHeight(); i++) {
            for (int j = 0; j < tetrimino.getWidth(); j++) {
                if(tetrimino.get(i,j)) {
                    r.set(minNewTopTetriminoRow + i, leftCol + j, true);
                }
            }
        }
        return r;
    }

    public int getTopRowInColumn(int col) {
        int boardTopRow = height; // if empty column
        for (int i = 0; i < height; i++) {
            if (b[i][col]) {
                boardTopRow = i;
                break;
            }
        }
        return boardTopRow;
    }

    public int getHeight() {
        return height;
    }
}
