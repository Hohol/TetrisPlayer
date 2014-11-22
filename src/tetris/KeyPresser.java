/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.awt.*;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_SPACE;

/**
 * KeyPresser
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 13:33)
 */
public class KeyPresser {
    private final Robot robot;

    public KeyPresser(Robot robot) {
        this.robot = robot;
    }

    /*private void pressButtons() throws InterruptedException {
        Thread.sleep(5000);
        press(KeyEvent.VK_ENTER);

        //for (int i = 0; i < 30; i++) {
        while (true) {
            Thread.sleep(100);
            press(KeyEvent.VK_LEFT);
        }
    }/**/

    public void makeMove(Move move) {
        press(move.getKeyCode());
    }

    private void press(int key) {
        System.out.println("press!");
        robot.keyPress(key);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        robot.keyRelease(key);
    }
}