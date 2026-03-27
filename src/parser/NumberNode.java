package parser;

public class NumberNode extends FactorNode {
    private int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public int evaluate() {
        return value;
    }
}