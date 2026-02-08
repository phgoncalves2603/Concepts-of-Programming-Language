package parser;

import lexer.LexicalAnalyzer;
import lexer.Token;
import lexer.TokenType;

public class Parser {

    private LexicalAnalyzer lexer;
    private Token currentToken;

    public Parser(LexicalAnalyzer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getToken(); // load first token
    }

    // Ensures the current token matches what we expect
    private void eat(TokenType expected) {
        if (currentToken.getType() == expected) {
            currentToken = lexer.getToken();
        } else {
            throw new RuntimeException(
                    "Syntax error: expected " + expected +
                            " but found " + currentToken.getType() +
                            " at row " + currentToken.getRow() +
                            ", column " + currentToken.getColumn()
            );
        }
    }

    // Entry point
    public int parse() {
        int value = expression();

        if (currentToken.getType() != TokenType.EOS) {
            throw new RuntimeException(
                    "Unexpected token after expression: " + currentToken.getLexeme()
            );
        }

        return value;
    }

    // Expression → Term ExpressionPrime
    private int expression() {
        int value = term();
        return expressionPrime(value);
    }

    // ExpressionPrime → + Term ExpressionPrime
    //                 | - Term ExpressionPrime
    //                 | ε
    private int expressionPrime(int inherited) {
        if (currentToken.getType() == TokenType.ADDITION) {
            eat(TokenType.ADDITION);
            int value = term();
            return expressionPrime(inherited + value);
        }

        if (currentToken.getType() == TokenType.SUBTRACTION) {
            eat(TokenType.SUBTRACTION);
            int value = term();
            return expressionPrime(inherited - value);
        }

        return inherited; // ε
    }

    // Term → Factor TermPrime
    private int term() {
        int value = factor();
        return termPrime(value);
    }

    // TermPrime → * Factor TermPrime
    //            | / Factor TermPrime
    //            | ε
    private int termPrime(int inherited) {
        if (currentToken.getType() == TokenType.MULTIPLICATION) {
            eat(TokenType.MULTIPLICATION);
            int value = factor();
            return termPrime(inherited * value);
        }

        if (currentToken.getType() == TokenType.DIVISION) {
            eat(TokenType.DIVISION);
            int value = factor();

            if (value == 0) {
                throw new ArithmeticException("Division by zero");
            }

            return termPrime(inherited / value);
        }

        return inherited; // ε
    }

    // Factor → ( Expression )
    //        | - Factor
    //        | Number
    private int factor() {
        if (currentToken.getType() == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            int value = expression();
            eat(TokenType.RPAREN);
            return value;
        }

        if (currentToken.getType() == TokenType.SUBTRACTION) {
            eat(TokenType.SUBTRACTION);
            return -factor(); // unary minus
        }

        return number();
    }

    // Number → Int-Lit
    private int number() {
        if (currentToken.getType() == TokenType.INTEGER) {
            int value = Integer.parseInt(currentToken.getLexeme());
            eat(TokenType.INTEGER);
            return value;
        }

        throw new RuntimeException(
                "Expected integer literal but found " + currentToken.getType()
        );
    }
}
