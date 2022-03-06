package sample.model.command.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Grid {

    private final int sizeA;
    private final int sizeB;
    private final List<List<Integer>> data;

    private Grid(
            int sizeA,
            int sizeB,
            List<List<Integer>> data
    ) {
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.data = data;
    }

    public static Grid create(int sizeA, int sizeB) {
        List<List<Integer>> data = new ArrayList<>();

        for (int ai = 0; ai < sizeA; ai++) {
            List<Integer> cols = new ArrayList<>();
            for (int bi = 0; bi < sizeB; bi++) {
                cols.add(null);
            }
            data.add(cols);
        }

        return new Grid(sizeA, sizeB, data);
    }

    public void set(int ai, int bi, int val) {
        this.data.get(ai).set(bi, val);
    }

    public int getOrThrow(int ai, int bi) {
        return this.get(ai, bi)
                .orElseThrow(() -> new RuntimeException("must not happen"));
    }

    public Optional<Integer> get(int ai, int bi) {
        if (0 <= ai && ai < this.sizeA) {
            // ok
        } else {
            return Optional.empty();
        }

        if (0 <= bi && bi < this.sizeB) {
            // ok
        } else {
            return Optional.empty();
        }

        return Optional.of(
            this.data.get(ai).get(bi)
        );
    }

}
