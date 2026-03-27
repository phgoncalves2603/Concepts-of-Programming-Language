package parser;

public class UnaryTermNode extends TermNode {
    private FactorNode factor;

    public UnaryTermNode(FactorNode factor) {
        this.factor = factor;
    }

    @Override
    public int evaluate() {
        return factor.evaluate();
    }
}