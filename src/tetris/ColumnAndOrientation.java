package tetris;

public class ColumnAndOrientation {
    private final int column;
    private final Tetrimino tetrimino;

    public ColumnAndOrientation(int column, Tetrimino tetrimino) {
        this.column = column;
        this.tetrimino = tetrimino;
    }

    public int getColumn() {
        return column;
    }

    public Tetrimino getTetrimino() {
        return tetrimino;
    }
}
