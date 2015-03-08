package tetris;

public class DropResult {
    private final Board board;
    private final int linesCleared;

    public DropResult(Board board, int linesCleared) {
        this.board = board;
        this.linesCleared = linesCleared;
    }

    public Board getBoard() {
        return board;
    }

    public int getLinesCleared() {
        return linesCleared;
    }
}
