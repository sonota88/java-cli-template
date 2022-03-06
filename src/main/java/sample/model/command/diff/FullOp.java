package sample.model.command.diff;

public class FullOp {

    private final Op op;
    private final String line;

    public FullOp(Op op, String line) {
        this.op = op;
        this.line = line;
    }

    public String toStr() {
        return this.op.getHeadSymbol() + this.line;
    }

}
