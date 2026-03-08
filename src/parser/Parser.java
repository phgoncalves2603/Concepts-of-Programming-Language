package parser;

import lexer.*;

public class Parser {

    private LexicalAnalyzer lexer;
    private Token currentToken;

    public Parser(LexicalAnalyzer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getToken();
    }

    private void match(TokenType expected) {

        System.out.println("Trying to match: " + expected +
                " | Current token: " + currentToken.getType());

        if (currentToken.getType() == expected) {
            System.out.println("Matched: " + currentToken);
            currentToken = lexer.getToken();
        } else {
            error("Expected " + expected + " but found " + currentToken.getType());
        }

    }

    private void error(String message) {
        throw new RuntimeException(
                message + " at row " + currentToken.getRow() +
                        ", column " + currentToken.getColumn()
        );
    }

    // Expression → Term Expression_Prime
    public void expression() {
        System.out.println("Enter <Expression>");
        term();
        expressionPrime();
        System.out.println("Exit <Expression>");
    }

    // Expression_Prime → + Term Expression_Prime | - Term Expression_Prime | null
    private void expressionPrime() {
        System.out.println("Enter <Expression_Prime>");

        if (currentToken.getType() == TokenType.ADDITION) {

            match(TokenType.ADDITION);
            term();
            expressionPrime();

        } else if (currentToken.getType() == TokenType.SUBTRACTION) {

            match(TokenType.SUBTRACTION);
            term();
            expressionPrime();

        }

        System.out.println("Exit <Expression_Prime>");
    }

    // Term → Factor Term_Prime
    private void term() {
        System.out.println("Enter <Term>");
        factor();
        termPrime();
        System.out.println("Exit <Term>");
    }

    // Term_Prime → * Factor Term_Prime | / Factor Term_Prime | null
    private void termPrime() {
        System.out.println("Enter <Term_Prime>");

        if (currentToken.getType() == TokenType.MULTIPLICATION) {

            match(TokenType.MULTIPLICATION);
            factor();
            termPrime();

        } else if (currentToken.getType() == TokenType.DIVISION) {

            match(TokenType.DIVISION);
            factor();
            termPrime();

        }

        System.out.println("Exit <Term_Prime>");
    }

    // Factor → ( Expression ) | - Expression | Number
    private void factor() {
        System.out.println("Enter <Factor>");

        if (currentToken.getType() == TokenType.LPAREN) {

            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);

        } else if (currentToken.getType() == TokenType.SUBTRACTION) {

            match(TokenType.SUBTRACTION);
            expression();

        } else {

            number();

        }

        System.out.println("Exit <Factor>");
    }

    // Number → INTEGER
    private void number() {
        System.out.println("Enter <Number>");

        if (currentToken.getType() == TokenType.INTEGER) {
            match(TokenType.INTEGER);
        } else {
            error("Expected INTEGER");
        }

        System.out.println("Exit <Number>");
    }

    public void parse() {

        if (currentToken.getType() == TokenType.EOS) {
            error("Empty expression");
        }

        expression();

        if (currentToken.getType() != TokenType.EOS) {
            error("Unexpected tokens after expression");
        }

        System.out.println("Parsing completed successfully.");
    }
}