/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static tetris.Board.STANDARD_HEIGHT;
import static tetris.Board.STANDARD_WIDTH;

/**
 * GameStateReader
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 18:14)
 */
public class GameStateReader {
    private final static Set<Integer> STILL_COLORS = new HashSet<Integer>(Arrays.asList(
            new Color(2, 92, 1).getRGB(),
            new Color(158, 12, 41).getRGB(),
            new Color(102, 0, 102).getRGB(),
            new Color(2, 88, 108).getRGB(),
            new Color(153, 51, 0).getRGB(),
            new Color(153, 102, 0).getRGB(),
            new Color(1, 36, 118).getRGB()
    ));
    private final static Set<Integer> FALLING_COLORS = new HashSet<Integer>(Arrays.asList(
            new Color(188, 137, 35).getRGB(),
            new Color(188, 86, 35).getRGB(),
            new Color(36, 71, 153).getRGB(),
            new Color(193, 47, 76).getRGB(),
            new Color(37, 123, 143).getRGB(),
            new Color(137, 35, 137).getRGB(),
            new Color(37, 127, 36).getRGB()
    ));

    private final Robot robot;

    public GameStateReader() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public GameState readGameState() {

        new Color(193, 47, 76);

        final int holdPart = 100;
        final int nextPart = 100;

        final int cellSize = 18;
        final Color emptyColor1 = new Color(38, 38, 38);
        //final Color emptyColor2 = new Color(47, 47, 47);

        BufferedImage img = robot.createScreenCapture(new Rectangle(2468 - holdPart, 259 - cellSize, STANDARD_WIDTH * cellSize + holdPart + nextPart, STANDARD_HEIGHT * cellSize));
        //img.setRGB(holdPart + STANDARD_WIDTH*cellSize + 29, cellSize*2+3, Color.WHITE.getRGB());
        /*try {
            ImageIO.write(img, "png", new File("img.png"));
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }/**/

        Board board = new Board(STANDARD_WIDTH, STANDARD_HEIGHT);
        for (int i = 0; i < STANDARD_HEIGHT; i++) {
            for (int j = 0; j < STANDARD_WIDTH; j++) {
                Color pixelColor = new Color(img.getRGB(holdPart + j * cellSize, i * cellSize + cellSize - 1));
                if (STILL_COLORS.contains(pixelColor.getRGB()) || FALLING_COLORS.contains(pixelColor.getRGB())) {
                    board.set(i, j, true);
                }
            }
        }

        int nextCellSize1 = 12;
        int minX = 1 << 20;
        int minY = 1 << 20;
        int maxX = 0;
        int maxY = 0;
        for (int x = holdPart + STANDARD_WIDTH * cellSize + 29; x < holdPart + STANDARD_WIDTH * cellSize + 29 + 50; x++) {
            for (int y = cellSize * 2 + 3; y < cellSize * 2 + 3 + 50; y++) {
                if (img.getRGB(x, y) != emptyColor1.getRGB()) {
                    minX = min(minX, x);
                    minY = min(minY, y);
                    maxX = max(maxX, x);
                    maxY = max(maxY, y);
                }
            }
        }

        int width = (maxX - minX + 1) / nextCellSize1;
        int height = (maxY - minY + 1) / nextCellSize1;

        boolean[][] b = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (img.getRGB(minX + nextCellSize1 * col, minY + nextCellSize1 * row) != emptyColor1.getRGB()) {
                    b[row][col] = true;
                }
            }
        }

        Tetrimino next = new Tetrimino(b);

        return new GameState(board, Collections.singletonList(next));
    }
}