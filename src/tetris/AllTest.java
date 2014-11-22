package tetris;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class AllTest {
    @Test
    void test() {
        Board board = new Board(
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "x.........\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx."
        );
        Tetrimino tetrimino = new Tetrimino("xxxx");
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        assertEquals(bestMoveFinder.findBestAction(board, tetrimino), new Action(board.getWidth() - 1, 1));
    }

    @Test
    void testClearFull() {
        Board board = new Board(
                        "x.........\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx."
        );
        Tetrimino tetrimino = new Tetrimino(
                        "x\n" +
                        "x\n" +
                        "x\n" +
                        "x"
        );
        Board newBoard = board.drop(tetrimino, board.getWidth() - 1);
        Board expectedNewBoard = new Board(
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "x........."
        );
        assertEquals(newBoard, expectedNewBoard);
    }

    @Test
    void testNoTetrimino() {
        Board board = new Board(
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..x..x....\n" +
                "x.xxxxxxx.\n" +
                ".........."
        );
        assertNull(board.extractFallingTetrimino());
    }
}