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
        switch (operator) {
            case MULTIPLICATION:
                return left.evaluate(memory) * right.evaluate(memory);
            case DIVISION:
                return left.evaluate(memory) / right.evaluate(memory);
            default:
                throw new RuntimeException("Invalid term operator: " + operator);
        }
    }
}