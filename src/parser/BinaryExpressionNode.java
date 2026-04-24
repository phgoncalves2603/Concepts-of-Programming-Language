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
    public int evaluate(Memory memory) {
        switch (operator) {
            case ADDITION:
                return left.evaluate(memory) + right.evaluate(memory);
            case SUBTRACTION:
                return left.evaluate(memory) - right.evaluate(memory);
            default:
                throw new RuntimeException("Invalid expression operator: " + operator);
        }
    }
}