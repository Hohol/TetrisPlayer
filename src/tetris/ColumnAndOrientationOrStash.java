package tetris;

public class ColumnAndOrientationOrStash { // so ugly =(
    private final boolean stash;
    private final int column;
    private final Tetrimino tetrimino;

    public ColumnAndOrientationOrStash(int column, Tetrimino tetrimino) {
        stash = false;
        this.column = column;
        this.tetrimino = tetrimino;
    }

    public ColumnAndOrientationOrStash(boolean stash) {
        this.stash = stash;
        column = -1;
        tetrimino = null;
    }

    public int getColumn() {
        return column;
    }

    public Tetrimino getTetrimino() {
        return tetrimino;
    }

    @Override
    public String toString() {
        return "ColumnAndOrientation{" +
                "column=" + column +
                ", tetrimino=" + tetrimino +
                '}';
    }

    public boolean isStash() {
        return stash;
    }
}
