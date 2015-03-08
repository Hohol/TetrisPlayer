/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
            new Color(1, 36, 118).getRGB(),
            new Color(55, 55, 55).getRGB() //battle2p only (penalty blocks at the bottom)
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
        return readSprintGameState();
        //return readBattle2PGameState();
    }

    private GameState readBattle2PGameState() {
        return getGameState(2267, 280, true);
    }

    private GameState readSprintGameState() {
        return getGameState(2468, 252, false);
    }

    private GameState getGameState(int xShift, int yShift, boolean battle2p) {
        final int holdPart = 100;
        final int nextPart = 100;

        final int cellSize = 18;
        final Color emptyColor1 = new Color(38, 38, 38);

        BufferedImage img = robot.createScreenCapture(new Rectangle(xShift - holdPart, yShift - cellSize, STANDARD_WIDTH * cellSize + holdPart + nextPart, STANDARD_HEIGHT * cellSize + 5));

        List<Cell> f = new ArrayList<Cell>();
        int minRow = 999, minCol = 999, maxRow = -1, maxCol = -1;
        Board board = new Board(STANDARD_WIDTH, STANDARD_HEIGHT);
        for (int row = 0; row < STANDARD_HEIGHT; row++) {
            for (int col = 0; col < STANDARD_WIDTH; col++) {
                int x = holdPart + col * cellSize;
                int y = row * cellSize + cellSize - 1;
                if (battle2p) {
                    x--;
                    y++;
                }
                Color pixelColor = new Color(img.getRGB(x, y));
                if (STILL_COLORS.contains(pixelColor.getRGB())) {
                    board.set(row, col, true);
                } else if (FALLING_COLORS.contains(pixelColor.getRGB())) {
                    f.add(new Cell(row, col));
                    minRow = min(minRow, row);
                    minCol = min(minCol, col);
                    maxRow = max(maxRow, row);
                    maxCol = max(maxCol, col);
                }
            }
        }

        //printImgAndExit(img);

        TetriminoWithPosition fallingTetrimino = null;
        if (maxRow != -1) {
            boolean[][] b = new boolean[maxRow - minRow + 1][maxCol - minCol + 1];
            int cnt = 0;
            for (Cell cell : f) {
                b[cell.x - minRow][cell.y - minCol] = true;
                cnt++;
            }
            if (cnt == 4) {
                fallingTetrimino = new TetriminoWithPosition(minRow, minCol, new Tetrimino(b));
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

        if (battle2p) {
            return new GameState(board, fallingTetrimino, Collections.<Tetrimino>emptyList());
        } else {
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

            return new GameState(board, fallingTetrimino, Collections.singletonList(next));
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void printImgAndExit(BufferedImage img) {
        try {
            ImageIO.write(img, "png", new File("img.png"));
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}