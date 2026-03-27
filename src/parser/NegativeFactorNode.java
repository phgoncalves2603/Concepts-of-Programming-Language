package parser;

public class NegativeFactorNode extends FactorNode {
    private FactorNode factor;

    public NegativeFactorNode(FactorNode factor) {
        this.factor = factor;
    }

    @Override
    public int evaluate() {
        return -factor.evaluate();
    }
}