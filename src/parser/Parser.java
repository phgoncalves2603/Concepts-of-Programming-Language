package parser;

import lexer.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private LexicalAnalyzer lexer;
    private Token currentToken;

    public Parser(LexicalAnalyzer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getToken();
    }

    private void match(TokenType expected) {
        if (currentToken.getType() == expected) {
            currentToken = lexer.getToken();
        } else {
            error("Expected " + expected + " but found " + currentToken.getType());
        }
    }

    private void error(String message) {
        throw new RuntimeException(
                "Syntax Error: " + message +
                        " at row " + currentToken.getRow() +
                        ", column " + currentToken.getColumn()
        );
    }

    // =========================
    // PROGRAM
    // =========================

    public ParseTree parse() {
        List<StatementNode> statements = new ArrayList<>();

        while (currentToken.getType() != TokenType.EOS) {
            statements.add(statement());
        }

        return new ParseTree(statements);
    }

    // =========================
    // STATEMENTS
    // =========================

    private StatementNode statement() {

        if (currentToken.getType() == TokenType.ID) {
            return assignmentStatement();
        }

        if (currentToken.getType() == TokenType.PRINT) {
            return printStatement();
        }

        if (currentToken.getType() == TokenType.READ) {
            return readStatement();
        }

        error("Invalid statement. Expected assignment, print, or read");
        return null;
    }

    private StatementNode assignmentStatement() {
        String id = currentToken.getLexeme();
        match(TokenType.ID);

        if (currentToken.getType() != TokenType.ASSIGNMENT) {
            error("Expected '=' after identifier");
        }

        match(TokenType.ASSIGNMENT);

        ExpressionNode expr = expression();

        return new AssignmentStatementNode(id, expr);
    }

    private StatementNode printStatement() {
        match(TokenType.PRINT);

        if (currentToken.getType() != TokenType.ID) {
            error("Expected identifier after 'print'");
        }

        String id = currentToken.getLexeme();
        match(TokenType.ID);

        return new PrintStatementNode(id);
    }

    private StatementNode readStatement() {
        match(TokenType.READ);

        if (currentToken.getType() != TokenType.ID) {
            error("Expected identifier after 'read'");
        }

        String id = currentToken.getLexeme();
        match(TokenType.ID);

        return new ReadStatementNode(id);
    }

    // =========================
    // EXPRESSIONS
    // =========================

    private ExpressionNode expression() {
        TermNode leftTerm = term();
        ExpressionNode left = new UnaryExpressionNode(leftTerm);
        return expressionPrime(left);
    }

    private ExpressionNode expressionPrime(ExpressionNode left) {

        if (currentToken.getType() == TokenType.ADDITION) {
            TokenType op = currentToken.getType();
            match(TokenType.ADDITION);

            TermNode right = term();
            ExpressionNode newLeft = new BinaryExpressionNode(left, op, right);

            return expressionPrime(newLeft);
        }

        if (currentToken.getType() == TokenType.SUBTRACTION) {
            TokenType op = currentToken.getType();
            match(TokenType.SUBTRACTION);

            TermNode right = term();
            ExpressionNode newLeft = new BinaryExpressionNode(left, op, right);

            return expressionPrime(newLeft);
        }

        return left;
    }

    private TermNode term() {
        FactorNode leftFactor = factor();
        TermNode left = new UnaryTermNode(leftFactor);
        return termPrime(left);
    }

    private TermNode termPrime(TermNode left) {

        if (currentToken.getType() == TokenType.MULTIPLICATION) {
            TokenType op = currentToken.getType();
            match(TokenType.MULTIPLICATION);

            FactorNode right = factor();
            TermNode newLeft = new BinaryTermNode(left, op, right);

            return termPrime(newLeft);
        }

        if (currentToken.getType() == TokenType.DIVISION) {
            TokenType op = currentToken.getType();
            match(TokenType.DIVISION);

            FactorNode right = factor();
            TermNode newLeft = new BinaryTermNode(left, op, right);

            return termPrime(newLeft);
        }

        return left;
    }

    private FactorNode factor() {

        if (currentToken.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            ExpressionNode expr = expression();
            match(TokenType.RPAREN);

            return new ParenthesizedFactorNode(expr);
        }

        if (currentToken.getType() == TokenType.SUBTRACTION) {
            match(TokenType.SUBTRACTION);
            return new NegativeFactorNode(factor());
        }

        if (currentToken.getType() == TokenType.INTEGER) {
            return number();
        }

        if (currentToken.getType() == TokenType.ID) {
            String id = currentToken.getLexeme();
            match(TokenType.ID);

            return new IdNode(id);
        }

        error("Expected factor (number, identifier, or expression)");
        return null;
    }

    private NumberNode number() {
        int value = Integer.parseInt(currentToken.getLexeme());
        match(TokenType.INTEGER);

        return new NumberNode(value);
    }
}