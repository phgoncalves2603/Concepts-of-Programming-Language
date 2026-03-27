package parser;

public class ParseTree {
    private ExpressionNode expressionNode;

    public ParseTree(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    public int evaluate() {
        return expressionNode.evaluate();
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }
}