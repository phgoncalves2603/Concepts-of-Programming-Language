package parser;

import lexer.TokenType;

public class BinaryTermNode extends TermNode {
    private TermNode left;
    private TokenType operator;
    private FactorNode right;

    public BinaryTermNode(TermNode left, TokenType operator, FactorNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public int evaluate(Memory memory) {
        int leftVal = left.evaluate(memory);
        int rightVal = right.evaluate(memory);

        switch (operator) {
            case MULTIPLICATION:
                return leftVal * rightVal;

            case DIVISION:
                if (rightVal == 0) {
                    throw new RuntimeException("Runtime Error: Division by zero");
                }
                return leftVal / rightVal;

            default:
                throw new RuntimeException("Invalid term operator: " + operator);
        }
    }
}