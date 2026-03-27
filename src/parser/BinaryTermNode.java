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
    public int evaluate() {
        switch (operator) {
            case MULTIPLICATION:
                return left.evaluate() * right.evaluate();
            case DIVISION:
                return left.evaluate() / right.evaluate();
            default:
                throw new RuntimeException("Invalid term operator: " + operator);
        }
    }
}