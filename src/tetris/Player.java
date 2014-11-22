package tetris;

import java.awt.*;
import java.awt.image.BufferedImage;

import static tetris.Board.*;
import static tetris.Board.STANDARD_HEIGHT;

public class Player {
    private final Robot robot;
    private final KeyPresser keyPresser;

    public Player() throws AWTException {
        robot = new Robot();
        keyPresser = new KeyPresser(robot);
    }

    public void play() throws Throwable {
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        while (true) {
            Board board = readField();
            System.out.println(board);
            Tetrimino tetrimino = board.extractFallingTetrimino();
            if (tetrimino == null) {
                System.out.println("skip");
                continue;
            }
            Move bestMove = bestMoveFinder.findBestMove(board, tetrimino);
            System.out.println(bestMove);
            keyPresser.makeMove(bestMove);
            //Thread.sleep(100);
        }
    }

    private Board readField() {
        final int cellSize = 18;
        final Color emptyColor1 = new Color(38, 38, 38);
        final Color emptyColor2 = new Color(47, 47, 47);

        BufferedImage img = robot.createScreenCapture(new Rectangle(2468, 259 - cellSize, STANDARD_WIDTH * cellSize, STANDARD_HEIGHT * cellSize));

        Board r = new Board(STANDARD_WIDTH, STANDARD_HEIGHT);
        for (int i = 0; i < STANDARD_HEIGHT; i++) {
            for (int j = 0; j < STANDARD_WIDTH; j++) {
                Color pixelColor = new Color(img.getRGB(j * cellSize, i * cellSize + cellSize - 1));
                if (!pixelColor.equals(emptyColor1) && !pixelColor.equals(emptyColor2)) {
                    r.set(i, j, true);
                }
            }
        }
        return r;
    }
}
