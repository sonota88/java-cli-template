package sample.model.command.diff;

/**
 * operation
 */
public enum Op {

    NOP(" "),
    ADD("+"),
    DEL("-"),
    ;

    private final String headSymbol;
    
    Op(String headSymbol) {
        this.headSymbol = headSymbol;
    }

    public String getHeadSymbol() {
        return this.headSymbol;
    }

}
