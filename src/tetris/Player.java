package tetris;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {
    private final Robot robot;

    public Player() throws AWTException {
        robot = new Robot();
    }

    public void play() throws Throwable {
        while(true) {
            Board board = readField();
            System.out.println(board);
           // Thread.sleep(5000);
        }
    }

    private Board readField() {
        // todo There are 3 pixels above. We can use them also.

        final int cellSize = 18;
        int width = 10;
        int height = 20;
        final Color emptyColor1 = new Color(38, 38, 38);
        final Color emptyColor2 = new Color(47, 47, 47);

        BufferedImage img = robot.createScreenCapture(new Rectangle(2468, 259, width * cellSize, height * cellSize));

        /*try {
            ImageIO.write(img, "png", new File("img.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }/**/

        Board r = new Board();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //Color pixelColor = robot.getPixelColor(2468 + j * cellSize, 259 + i * cellSize);
                Color pixelColor = new Color(img.getRGB(j * cellSize, i * cellSize));
                if (!pixelColor.equals(emptyColor1) && !pixelColor.equals(emptyColor2)) {
                    r.set(i, j, true);
                }
                //System.out.print(pixelColor + " ");
            }
            //System.out.println();
        }

        return r;
    }

    private void pressButtons() throws InterruptedException {
        Thread.sleep(5000);
        press(KeyEvent.VK_ENTER);

        //for (int i = 0; i < 30; i++) {
        while (true) {
            Thread.sleep(100);
            press(KeyEvent.VK_LEFT);
        }
    }

    private void press(int key) throws InterruptedException {
        System.out.println("press!");
        robot.keyPress(key);
        Thread.sleep(100);
        robot.keyRelease(key);
    }
}
