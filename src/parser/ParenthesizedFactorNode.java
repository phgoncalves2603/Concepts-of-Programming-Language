package parser;

public class ParenthesizedFactorNode extends FactorNode {
    private ExpressionNode expression;

    public ParenthesizedFactorNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate() {
        return expression.evaluate();
    }
}