/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris.logic;

/**
 * Action
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 15:54)
 */
public class Action {
    private final int newLeftCol;
    private final int cwRotationCnt;

    public Action(int newLeftCol, int cwRotationCnt) {
        this.newLeftCol = newLeftCol;
        this.cwRotationCnt = cwRotationCnt;
    }

    public int getNewLeftCol() {
        return newLeftCol;
    }

    public int getCwRotationCnt() {
        return cwRotationCnt;
    }

    @Override
    public String toString() {
        return "Action{" +
                "newLeftCol=" + newLeftCol +
                ", cwRotationCnt=" + cwRotationCnt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (cwRotationCnt != action.cwRotationCnt) return false;
        if (newLeftCol != action.newLeftCol) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = newLeftCol;
        result = 31 * result + cwRotationCnt;
        return result;
    }
}