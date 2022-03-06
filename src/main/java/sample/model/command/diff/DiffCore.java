package sample.model.command.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class DiffCore {

    private final List<String> workListA;
    private final List<String> workListB;
    private final Grid grid;
    private boolean ignoreSpace = false;

    private DiffCore(
            List<String> workListA,
            List<String> workListB,
            Grid grid
    ) {
        this.workListA = workListA;
        this.workListB = workListB;
        this.grid = grid;
    }

    public static DiffCore create(
            List<String> listA,
            List<String> listB
    ) {
        List<String> workListA = toWorkList(listA);
        List<String> workListB = toWorkList(listB);

        Grid grid = Grid.create(
                workListA.size(),
                workListB.size()
        );

        return new DiffCore(workListA, workListB, grid);
    }

    public List<FullOp> exec() {
        fillGrid();
        List<Op> ops = walk();
        List<FullOp> fullOps = toFullOps(ops);

        return fullOps;
    }

    private List<FullOp> toFullOps(List<Op> ops) {
        int ai = 0;
        int bi = 0;

        List<FullOp> fullOps = new ArrayList<>();

        for (Op op : ops) {
            String line;
            switch (op) {
            case DEL:
                line = this.workListA.get(ai + 1);
                ai++;
                break;
            case ADD:
                line = this.workListB.get(bi + 1);
                bi++;
                break;
            case NOP:
                line = this.workListA.get(ai + 1);
                ai++;
                bi++;
                break;
            default:
                throw new RuntimeException("invalid operation");
            }

            fullOps.add(new FullOp(op, line));
        }
        return fullOps;
    }

    private void fillGrid() {
        for (int ai = 0; ai < this.workListA.size(); ai++) {
            this.grid.set(ai, 0, ai);
        }
        for (int bi = 0; bi < this.workListB.size(); bi++) {
            this.grid.set(0, bi, bi);
        }
        for (int ai = 1; ai < this.workListA.size(); ai++) {
            for (int bi = 1; bi < this.workListB.size(); bi++) {
                this.grid.set(ai, bi, getDistance(ai, bi));
            }
        }
    }

    private int getDistance(int ai, int bi) {
        if (isNop(ai, bi)) {
            return this.grid.getOrThrow(ai - 1, bi - 1);
        } else {
            int top  = this.grid.getOrThrow(ai - 1, bi    );
            int left = this.grid.getOrThrow(ai    , bi - 1);
            return getMin(top, left) + 1;
        }
    }

    private boolean isNop(int ai, int bi) {
        String sa = this.workListA.get(ai);
        String sb = this.workListB.get(bi);

        if (this.ignoreSpace) {
            sa = StringUtils.replace(sa, " ", "");
            sb = StringUtils.replace(sb, " ", "");
        }

        return Objects.equals(sa, sb);
    }

    private List<Op> walk() {
        Pos pos = new Pos(
                this.workListA.size() - 1,
                this.workListB.size() - 1
        );
        List<Op> ops = new ArrayList<>();

        while (true) {
            if (pos.ai == 0 && pos.bi == 0) {
                break;
            }
            Op op = _walk_op(pos);
            ops.add(op);

            pos = _walk_pos(op, pos);
        }

        Collections.reverse(ops);
        return ops;
    }

    private Op _walk_op(Pos pos) {
        final int ai = pos.ai;
        final int bi = pos.bi;

        int delDistance = this.grid.get(ai - 1, bi    ).orElse(Integer.MAX_VALUE);
        int addDistance = this.grid.get(ai    , bi - 1).orElse(Integer.MAX_VALUE);

        int nopDistance;
        if (isNop(ai, bi)) {
            nopDistance = this.grid.getOrThrow(ai, bi);
        } else {
            nopDistance = Integer.MAX_VALUE;
        }

        int min = getMin(delDistance, addDistance, nopDistance);

        if (min == nopDistance) {
            return Op.NOP;
        } else if (min == addDistance) {
            return Op.ADD;
        } else if (min == delDistance) {
            return Op.DEL;
        } else {
            throw new RuntimeException("invalid operation");
        }
    }

    private Pos _walk_pos(Op op, Pos pos) {
        final int ai = pos.ai;
        final int bi = pos.bi;

        switch (op) {
        case NOP: return new Pos(ai - 1, bi - 1);
        case ADD: return new Pos(ai    , bi - 1);
        case DEL: return new Pos(ai - 1, bi    );
        default:
            throw new RuntimeException("invalid operation");
        }
    }

    private int getMin(int ... ns) {
        int min = ns[0];
        for (int i = 0; i < ns.length; i++) {
            int n = ns[i];
            if (n < min) {
                min = n;
            }
        }
        return min;
    }

    private static List<String> toWorkList(List<String> lines) {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.addAll(lines);
        return list;
    }

    public void setIgnoreSpace(boolean ignoreSpace) {
        this.ignoreSpace = ignoreSpace;
    }

    private static class Pos {
        final int ai;
        final int bi;

        Pos(int ai, int bi) {
            this.ai = ai;
            this.bi = bi;
        }
    }

}
