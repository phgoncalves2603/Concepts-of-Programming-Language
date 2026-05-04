package parser;

import lexer.TokenType;

public class LogicalExpressionNode {
    private ExpressionNode left;
    private TokenType operator;
    private ExpressionNode right;

    public LogicalExpressionNode(ExpressionNode left, TokenType operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public boolean evaluate(Memory memory) {
        int leftValue = left.evaluate(memory);
        int rightValue = right.evaluate(memory);

        switch (operator) {
            case LT:
                return leftValue < rightValue;
            case LE:
                return leftValue <= rightValue;
            case GT:
                return leftValue > rightValue;
            case GE:
                return leftValue >= rightValue;
            case EQ:
                return leftValue == rightValue;
            case NE:
                return leftValue != rightValue;
            default:
                throw new RuntimeException("Invalid logical operator: " + operator);
        }
    }
}