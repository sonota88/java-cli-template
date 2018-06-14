package sample.model.command;

import java.util.ArrayList;
import java.util.List;

public class Split {

    public void exec(String file, int div) {

    private int countLines(String file) {
        // TODO Auto-generated method stub
        return 0;
    }

    List<Range> generateRanges(int total, int div) {
        if (div <= 0) {
            throw new IllegalArgumentException("div must be positive");
        }

        int blockSize = calcNumLinesForRange(total, div);
        List<Range> ranges = new ArrayList<>();
        for (int di = 0; di < div; di++) {
            int offset = di * blockSize;
            int nextOffset = (di + 1) * blockSize;
            int from = offset + 1;
            int to = nextOffset;
            if (to > total) {
                to = total;
            }
            ranges.add(new Range(from, to));
        }
        return ranges;
    }

    int calcNumLinesForRange(int total, int div) {
        if (div <= 0) {
            throw new IllegalArgumentException("div must be positive");
        }
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
                this.from = null;
                this.to = null;
            }
        }

        public boolean contains(int n) {
            if (!isValid()) {
                return false;
            }
            return this.from <= n && n <= this.to;
        }

        public boolean isValid() {
            return from != null;
        }

    }

}
