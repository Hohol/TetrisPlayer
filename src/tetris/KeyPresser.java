/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.awt.*;

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

    public void makeMove(Move move) {
        press(move.getKeyCode());
    }

    private void press(int key) {
        robot.keyPress(key);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }/**/
        robot.keyRelease(key);
    }
}