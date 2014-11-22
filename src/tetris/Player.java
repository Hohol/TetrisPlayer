package tetris;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {
    private final Robot robot;
    private final KeyPresser keyPresser;

    public Player() throws AWTException {
        robot = new Robot();
        keyPresser = new KeyPresser(robot);
    }

    public void play() throws Throwable {
        while (true) {
            Board board = readField();
            System.out.println(board);
            Tetrimino tetrimino = board.extractFallingTetrimino();
            if (tetrimino == null) {
                continue;
            }
            if (tetrimino.getCol() > 0) {
                keyPresser.left();
            } else {
                keyPresser.drop();
            }/**/
        }
    }

    private Board readField() {
        final int cellSize = 18;
        final Color emptyColor1 = new Color(38, 38, 38);
        final Color emptyColor2 = new Color(47, 47, 47);

        BufferedImage img = robot.createScreenCapture(new Rectangle(2468, 259 - cellSize, Board.WIDTH * cellSize, Board.HEIGHT * cellSize));

        /*try {
            ImageIO.write(img, "png", new File("img.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }/**/

        Board r = new Board();

        for (int i = 0; i < Board.HEIGHT; i++) {
            for (int j = 0; j < Board.WIDTH; j++) {
                Color pixelColor = new Color(img.getRGB(j * cellSize, i * cellSize + cellSize - 1));
                if (!pixelColor.equals(emptyColor1) && !pixelColor.equals(emptyColor2)) {
                    r.set(i, j, true);
                }
            }
        }

        return r;
    }
}
