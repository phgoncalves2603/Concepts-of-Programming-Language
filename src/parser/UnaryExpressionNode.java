package parser;

public class UnaryExpressionNode extends ExpressionNode {
    private TermNode term;

    public UnaryExpressionNode(TermNode term) {
        this.term = term;
    }

    @Override
    public int evaluate(Memory memory) {
        return term.evaluate(memory);
    }
}