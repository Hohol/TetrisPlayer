/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris.logic;

/**
 * ActionWithEval
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 18:30)
 */
public class ActionWithEval {
    private final Action action;
    private final double profit;

    public ActionWithEval(Action action, double profit) {
        this.action = action;
        this.profit = profit;
    }

    public Action getAction() {
        return action;
    }

    public double getProfit() {
        return profit;
    }
}