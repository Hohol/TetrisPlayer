/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris;

import java.util.List;

/**
 * GameState
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 17:31)
 */
public class GameState {
    private final Board board;
    private final List<Tetrimino> nextTetriminoes;

    public GameState(Board board, List<Tetrimino> nextTetriminoes) {
        this.board = board;
        this.nextTetriminoes = nextTetriminoes;
    }

    public Board getBoard() {
        return board;
    }

    public List<Tetrimino> getNextTetriminoes() {
        return nextTetriminoes;
    }
}