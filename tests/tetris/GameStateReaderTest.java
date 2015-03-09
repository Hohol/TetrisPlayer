package tetris;

import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.testng.Assert.*;

@Test
public class GameStateReaderTest {
    @Test
    void testYellowShift() {
        check(0, new TetriminoWithPosition(0, 2, Tetrimino.O));
    }

    @Test
    void testFallingT() {
        check(1, new TetriminoWithPosition(-1, 3, Tetrimino.T));
    }

    @Test
    void checkFallingJ() {
        check(11, new TetriminoWithPosition(-1, 3, Tetrimino.J));
    }

    @Test
    void checkFallingS() {
        check(29, new TetriminoWithPosition(-1, 3, Tetrimino.S));
    }

    @Test
    void checkFallingI() {
        check(54, new TetriminoWithPosition(-2, 6, Tetrimino.I.rotateCW()));
    }

    @Test
    void checkFallingZ() {
        check(92, new TetriminoWithPosition(-1, 3, Tetrimino.Z));
    }

    @Test
    void testFallingO() {
        check(163, new TetriminoWithPosition(-1, 4, Tetrimino.O));
    }

    @Test
    void checkFallingL() {
        check(189, new TetriminoWithPosition(-1, 3, Tetrimino.L));
    }

    private void check(int index, TetriminoWithPosition expected) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("tests/imgs/img" + index + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameStateReader gameStateReader = new GameStateReader();
        GameState gameState = gameStateReader.getGameState(true, img);
        assertEquals(gameState.getFallingTetrimino(), expected);
    }
}