package tetris.logic;

public class EvaluationState {
    private final int badCnt;
    private final int flatRate;
    private final int nonTetrisLinesCleared;

    public EvaluationState(int badCnt, int flatRate, int nonTetrisLinesCleared) {
        this.badCnt = badCnt;
        this.flatRate = flatRate;
        this.nonTetrisLinesCleared = nonTetrisLinesCleared;
    }

    public boolean better(EvaluationState st) {
        if (st == null) {
            return true;
        }
        if (badCnt != st.badCnt) {
            return badCnt < st.badCnt;
        }
        if (nonTetrisLinesCleared != st.nonTetrisLinesCleared) {
            return nonTetrisLinesCleared < st.nonTetrisLinesCleared;
        }
        if (flatRate != st.flatRate) {
            return flatRate < st.flatRate;
        }
        return false;
    }

    @Override
    public String toString() {
        return "EvaluationState{" +
                "badCnt=" + badCnt +
                ", flatRate=" + flatRate +
                ", nonTetrisLinesCleared=" + nonTetrisLinesCleared +
                '}';
    }
}
