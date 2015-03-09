package tetris.logic;

// actually it represents not just state but difference between result state and start state
public class EvaluationState {
    private final int badCnt;
    private final int flatRate;
    private final int nonTetrisLinesCleared;
    private final int tetrisLinesCleared;
    private final int lastColumnHeight;
    private final int holeCnt;
    private final boolean virtualHole;
    private final boolean tooHigh;
    private final boolean lineInStash;

    public EvaluationState(int badCnt, int flatRate, int nonTetrisLinesCleared, int tetrisLinesCleared, int lastColumnHeight, int holeCnt, boolean virtualHole, boolean tooHigh, boolean lineInStash) {
        this.badCnt = badCnt;
        this.flatRate = flatRate;
        this.nonTetrisLinesCleared = nonTetrisLinesCleared;
        this.tetrisLinesCleared = tetrisLinesCleared;
        this.lastColumnHeight = lastColumnHeight;
        this.holeCnt = holeCnt;
        this.virtualHole = virtualHole;
        this.tooHigh = tooHigh;
        this.lineInStash = lineInStash;
    }

    public boolean better(EvaluationState st) {
        if (st == null) {
            return true;
        }
        if (tooHigh != st.tooHigh) {
            return !tooHigh;
        }
        if (badCnt != st.badCnt) {
            return badCnt < st.badCnt;
        }
        if (tetrisLinesCleared != st.tetrisLinesCleared) {
            return tetrisLinesCleared > st.tetrisLinesCleared;
        }

        if (holeCnt != st.holeCnt) {
            return holeCnt < st.holeCnt;
        }

        if (badCnt == 0 && holeCnt == 0) {
            if (lastColumnHeight != st.lastColumnHeight) {
                return lastColumnHeight < st.lastColumnHeight;
            }
            if (lastColumnHeight == 0) {
                if (virtualHole != st.virtualHole) {
                    return !virtualHole;
                }
                if (nonTetrisLinesCleared != st.nonTetrisLinesCleared) {
                    return nonTetrisLinesCleared < st.nonTetrisLinesCleared;
                }
            }
        }
        if (lineInStash != st.lineInStash) {
            return lineInStash;
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
