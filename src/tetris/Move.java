/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import static java.awt.event.KeyEvent.*;

/**
 * Move
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 14:30)
 */
public enum Move {
    LEFT(VK_LEFT), RIGHT(VK_RIGHT), DROP(VK_SPACE), STASH(VK_C), ROTATE_CW(VK_UP), ROTATE_CCW(VK_Z), ENTER(VK_ENTER);

    private final int keyCode;

    Move(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}