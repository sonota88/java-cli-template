package sample.model.command;

public class Split {

    public void exec(String file, int div) {

    }

    int calcNumLinesForRange(int total, int div) {
        if (total % div == 0) {
            return total / div;
        } else {
            return (total / div) + 1;
        }
    }

    class Range {

        Integer from;
        Integer to;

        Range(int from, int to) {
            if (from <= to) {
                // valid
                this.from = from;
                this.to = to;
            } else {
                // invalid
            }
        }

    }

}
