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
    public static final Color PENALTY_BLOCK_COLOR = new Color(55, 55, 55);

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
        //return readSprintGameState();
        return readBattle2PGameState();
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

        BufferedImage img = robot.createScreenCapture(new Rectangle(xShift - holdPart, yShift - cellSize, STANDARD_WIDTH * cellSize + holdPart + nextPart, STANDARD_HEIGHT * cellSize + 5));

        return getGameState(battle2p, holdPart, cellSize, img);
    }

    private GameState getGameState(boolean battle2p, int holdPart, int cellSize, BufferedImage img) {
        final Color emptyColor = new Color(38, 38, 38);

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
                } else if (pixelColor.equals(PENALTY_BLOCK_COLOR)) {
                    board.set(row, col, true);
                    if (board.getPenalty() == 0) {
                        board.setPenalty(STANDARD_HEIGHT - row);
                    }
                }
            }
        }

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

        int otherNextCellSize = 8;
        int firstNextCellSize = 12;

        Tetrimino next;

        if (battle2p) {
            int x1 = holdPart + STANDARD_WIDTH * cellSize + 27;
            int x2 = holdPart + STANDARD_WIDTH * cellSize + 75;
            int y1 = cellSize * 2 + 8;
            int y2 = cellSize * 2 + 52;
            next = getNext(img, x1, y1, x2, y2, firstNextCellSize, Color.BLACK);
        } else {
            int x1 = holdPart + STANDARD_WIDTH * cellSize + 29;
            int x2 = holdPart + STANDARD_WIDTH * cellSize + 79;
            int y1 = cellSize * 2 + 3;
            int y2 = cellSize * 2 + 53;
            next = getNext(img, x1, y1, x2, y2, firstNextCellSize, emptyColor);
        }
        return new GameState(board, fallingTetrimino, Collections.singletonList(next));
    }

    private Tetrimino getNext(BufferedImage img, int x1, int y1, int x2, int y2, int firstNextCellSize, Color emptyColor) {
        Tetrimino next;
        int minX = 1 << 20;
        int minY = 1 << 20;
        int maxX = 0;
        int maxY = 0;
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                if (img.getRGB(x, y) != emptyColor.getRGB()) {
                    minX = min(minX, x);
                    minY = min(minY, y);
                    maxX = max(maxX, x);
                    maxY = max(maxY, y);
                }
            }
        }

        int width = (maxX - minX + 1) / firstNextCellSize;
        int height = (maxY - minY + 1) / firstNextCellSize;

        boolean[][] b = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (img.getRGB(minX + firstNextCellSize * col, minY + firstNextCellSize * row) != emptyColor.getRGB()) {
                    b[row][col] = true;
                }
            }
        }

        next = new Tetrimino(b);
        return next;
    }

    @SuppressWarnings("UnusedDeclaration")
    private void printImgAndExit(BufferedImage img) {
        printImg(img);
        System.exit(0);
    }

    private void printImg(BufferedImage img) {
        try {
            ImageIO.write(img, "png", new File("img.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        new GameStateReader().test();
    }

    private void test() throws IOException {
        BufferedImage img = ImageIO.read(new File("img.png"));
        GameState gameState = getGameState(true, 100, 18, img);
    }
}