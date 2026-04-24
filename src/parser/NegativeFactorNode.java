package parser;

public class NegativeFactorNode extends FactorNode {
    private FactorNode factor;

    public NegativeFactorNode(FactorNode factor) {
        this.factor = factor;
    }

    @Override
    public int evaluate(Memory memory) {
        return -factor.evaluate(memory);
    }
}