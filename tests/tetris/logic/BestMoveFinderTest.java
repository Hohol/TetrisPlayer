package tetris.logic;

import org.testng.Assert;
import org.testng.annotations.Test;
import tetris.Board;
import tetris.Tetrimino;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class BestMoveFinderTest {

    private final BestMoveFinder bestMoveFinder = new BestMoveFinder();

    @Test
    void test() {
        Board board = new Board(
                "" +
                        "....xxxx..\n" +
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
        Assert.assertEquals(getAction(board), new Action(board.getWidth() - 1, 1));
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
        Board newBoard = board.drop(tetrimino, board.getWidth() - 1).getBoard();
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

    @Test
    void testNextTetrimino() {
        Board board = new Board(
                "" +
                        ".......x..\n" +
                        ".......x..\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxx..\n"
        );
        Tetrimino tetrimino = new Tetrimino("xxxx");
        List<Tetrimino> nextTetriminoes = Collections.singletonList(new Tetrimino("xxxx"));
        ActionWithEvaluation action = bestMoveFinder.findBestAction(board, tetrimino, nextTetriminoes, 0);
        assertEquals(action.getAction(), new Action(board.getWidth() - 2, 1));
    }

    @Test
    void minimizeLowTiles() {
        Board board = new Board(
                "......x....\n" +
                        "......x....\n" +
                        "......x....\n" +
                        "......x....\n" +
                        "x.xxxxxxxx.\n" +
                        "x.xxxxxxxx.\n" +
                        "x.xxxxxxxx.\n" +
                        "x.xxxxxxxx."
        );
        Tetrimino tetrimino = new Tetrimino("xxxx");
        ActionWithEvaluation action = bestMoveFinder.findBestAction(board, tetrimino);
        assertEquals(action.getAction(), new Action(1, 1));
    }

    @Test
    void testEquals() {
        assertEquals(
                new Board(
                        "" +
                                "....x.....\n" +
                                "...xxx....\n" +
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
                                "x.........\n" +
                                "xxx......."
                ),
                new Board(
                        "" +
                                "....x.....\n" +
                                "...xxx....\n" +
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
                                "x.........\n" +
                                "xxx......."
                )
        );
    }

    @Test
    void testBug() {
        Board board = new Board(
                "" +
                        "...xxxx...\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        ".xxxxxxxx."
        );
        checkAction(board, new Action(board.getWidth() - 1, 1));
    }

    @Test
    void testBug2() {
        Board board = new Board(
                "" +
                        "..x.......\n" +
                        "..x.......\n" +
                        "..x.......\n" +
                        "..x.......\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..xxx.....\n" +
                        ".xxxx.....\n" +
                        "xxxxx.....\n" +
                        "xxxxxxx...\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx."
        );
        Action bestAction = bestMoveFinder.findBestAction(board, board.extractFallingTetrimino().getTetrimino(), Collections.singletonList(new Tetrimino("xx\nxx")), 0).getAction();
        assertEquals(bestAction, new Action(board.getWidth() - 1, 0));
    }

    @Test
    void testBug4() {
        Board board = new Board(
                "....x.....\n" +
                        "....x.....\n" +
                        "....x.....\n" +
                        "....x.....\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "xxxxx.xxx.\n" +
                        "xxxxxx.xx.\n" +
                        "xxxxxxx.x.\n" +
                        "xxxxxxxx.x\n" +
                        "xxxxxxxxx."
        );
        ActionWithEvaluation bestAction = bestMoveFinder.findBestAction(board, board.extractFallingTetrimino().getTetrimino());
        assertEquals(bestAction.getAction(), new Action(board.getWidth() - 1, 0));
    }

    @Test
    void test777() {
        Board board = new Board(
                "....xx....\n" +
                        "....xx....\n" +
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
                        "xxxxxxxx..\n" +
                        "xxxxxxxx.."
        );
        ActionWithEvaluation bestAction = bestMoveFinder.findBestAction(board, board.extractFallingTetrimino().getTetrimino());
        assertFalse(bestAction.getAction().equals(new Action(board.getWidth() - 2, 0)));
    }

    @Test
    void leaveLastColumnEmpty() {
        Board board = new Board(
                "" +
                        "...xx.....\n" +
                        "...xx.....\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        ".x........\n" +
                        ".xxxxxxx.."
        );
        checkAction(board, new Action(2, 0));
    }

    @Test
    void avoidAbrupt() {
        Board board = new Board(
                "" +
                        "...xx.....\n" +
                        "...xx.....\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        ".xxxxxxx.."
        );
        checkAction(board, new Action(2, 0));
    }

    @Test
    void avoidBigHeight() {
        Board board = new Board(
                "" +
                        "....x.....\n" +
                        "....x.....\n" +
                        "....x.....\n" +
                        "....x.....\n" +
                        ".x........\n" +
                        ".x........\n" +
                        ".x........\n" +
                        "xxxxxx....\n" +
                        "xxxxxx....\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxxx.\n" +
                        "xxxxxxxxx."
        );
        Action bestAction = getAction(board);
        Action forbiddenAction = new Action(board.getWidth() - 2, 0);
        assertFalse(bestAction.equals(forbiddenAction));
    }

    @Test
    void testBug3() {
        Board board = new Board(
                "" +
                        ".....x....\n" +
                        ".....x....\n" +
                        ".....x....\n" +
                        ".....x....\n" +
                        "x.........\n" +
                        "xxxx......\n" +
                        "xxxx...xxx\n" +
                        "xxxx...xxx\n" +
                        "xxxx..xxxx\n" +
                        "xxxx..xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "xxxxx.xxxx\n" +
                        "oooooooooo\n" +
                        "oooooooooo\n" +
                        "oooooooooo\n" +
                        "oooooooooo"
        );
        checkAction(board, new Action(5, 0));
    }

    @Test
    void avoidVirtualHole() {
        Board board = new Board(
                "" +
                        "....xx....\n" +
                        "....xx....\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "..........\n" +
                        "xxxxxx....\n" +
                        "xxxxxx....\n" +
                        "xxxxxxxx..\n" +
                        "xxxxxxxxx."
        );
        checkForbidden(board, new Action(6, 0));
    }

    private void checkForbidden(Board board, Action forbiddenAction) {
        Action bestAction = getAction(board);
        assertFalse(bestAction.equals(forbiddenAction));
    }

    //-------- utils

    private void checkAction(Board board, Action expected) {
        Action bestAction = getAction(board);
        assertEquals(bestAction, expected);
    }

    private Action getAction(Board board) {
        return bestMoveFinder.findBestAction(board, board.extractFallingTetrimino().getTetrimino()).getAction();
    }
}