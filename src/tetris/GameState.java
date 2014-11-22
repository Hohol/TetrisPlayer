/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

/**
 * GameState
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 17:31)
 */
public class GameState {
    private final Board board;
    private final Tetrimino next;

    public GameState(Board board, Tetrimino next) {
        this.board = board;
        this.next = next;
    }

    public Board getBoard() {
        return board;
    }

    public Tetrimino getNext() {
        return next;
    }
}