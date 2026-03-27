package parser;

import lexer.TokenType;

public class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private TokenType operator;
    private TermNode right;

    public BinaryExpressionNode(ExpressionNode left, TokenType operator, TermNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public int evaluate() {
        switch (operator) {
            case ADDITION:
                return left.evaluate() + right.evaluate();
            case SUBTRACTION:
                return left.evaluate() - right.evaluate();
            default:
                throw new RuntimeException("Invalid expression operator: " + operator);
        }
    }
}