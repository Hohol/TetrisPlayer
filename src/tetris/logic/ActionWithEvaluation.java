/*
 * Copyright (c) 2008-2014 Maxifier Ltd. All Rights Reserved.
 */
package tetris.logic;

/**
 * ActionWithEvaluation
 *
 * @author Nikita Glashenko (nikita.glashenko@maxifier.com) (2014-11-22 18:30)
 */
public class ActionWithEvaluation {
    private final Action action;
    private final EvaluationState state;

    public ActionWithEvaluation(Action action, EvaluationState state) {
        this.action = action;
        this.state = state;
    }

    public Action getAction() {
        return action;
    }

    public EvaluationState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "ActionWithEvaluation{" +
                "action=" + action +
                ", state=" + state +
                '}';
    }
}