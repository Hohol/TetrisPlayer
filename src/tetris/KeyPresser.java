/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.awt.*;
import java.util.List;

/**
 * KeyPresser
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 13:33)
 */
public class KeyPresser {
    private final Robot robot;

    public KeyPresser() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(List<Move> moves) {
        System.out.println(moves);
        for (Move move : moves) {
            robot.keyPress(move.getKeyCode());
        }
        try {
            Thread.sleep(35);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Move move : moves) {
            robot.keyRelease(move.getKeyCode());
        }
    }

    /*public static void main(String[] args) {
        KeyPresser keyPresser = new KeyPresser();
        keyPresser.test();
    }

    private void test() {
        try {
            Thread.sleep(5000);
            press(KeyEvent.VK_SPACE);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/
}