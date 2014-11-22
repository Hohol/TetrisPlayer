package tetris;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static tetris.Move.*;

@Test
public class BestMoveFinderTest {
    @Test
    void test() {
        Board board = new Board(
                "....xx....\n" +
                ".....xx...\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..x.......\n" +
                "....x.....\n" +
                ".x.xxx....\n" +
                "xxxxx.xxxx\n" +
                "xxxx.x.xx.\n" +
                "..xxxx.xxx\n" +
                "xxxxxxxxx.\n" +
                "xxxxx..x..\n" +
                "xx.xxxxx..\n" +
                ".xxxxxxxx."
        );
        Tetrimino tetrimino = board.extractFallingTetrimino();
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        assertEquals(bestMoveFinder.findBestMove(board, tetrimino), RIGHT);
    }
}