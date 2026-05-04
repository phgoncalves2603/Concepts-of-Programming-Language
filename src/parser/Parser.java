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

    public ParseTree parse() {
        List<StatementNode> statements = statementList();

        if (currentToken.getType() != TokenType.EOS) {
            error("Expected end of source");
        }

        return new ParseTree(statements);
    }

    private List<StatementNode> statementList() {
        List<StatementNode> statements = new ArrayList<>();

        while (currentToken.getType() != TokenType.EOS
                && currentToken.getType() != TokenType.ELSE
                && currentToken.getType() != TokenType.END) {
            statements.add(statement());
        }

        return statements;
    }

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

        if (currentToken.getType() == TokenType.IF) {
            return ifStatement();
        }

        if (currentToken.getType() == TokenType.DO) {
            return doStatement();
        }

        error("Invalid statement. Expected assignment, print, read, if, or do");
        return null;
    }

    private StatementNode assignmentStatement() {
        String id = currentToken.getLexeme();
        match(TokenType.ID);

        match(TokenType.ASSIGNMENT);

        ExpressionNode expr = arithmeticExpression();

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

    private StatementNode ifStatement() {
        match(TokenType.IF);
        match(TokenType.LPAREN);

        LogicalExpressionNode condition = logicalExpression();

        match(TokenType.RPAREN);
        match(TokenType.THEN);

        List<StatementNode> thenStatements = statementList();

        match(TokenType.ELSE);

        List<StatementNode> elseStatements = statementList();

        match(TokenType.END);
        match(TokenType.IF);

        return new IfStatementNode(condition, thenStatements, elseStatements);
    }

    private StatementNode doStatement() {
        match(TokenType.DO);

        if (currentToken.getType() == TokenType.WHILE) {
            return doWhileStatementAfterDo();
        }

        if (currentToken.getType() == TokenType.ID) {
            return countedDoStatementAfterDo();
        }

        error("Expected 'while' or identifier after 'do'");
        return null;
    }

    private StatementNode doWhileStatementAfterDo() {
        match(TokenType.WHILE);
        match(TokenType.LPAREN);

        LogicalExpressionNode condition = logicalExpression();

        match(TokenType.RPAREN);

        List<StatementNode> statements = statementList();

        match(TokenType.END);
        match(TokenType.DO);

        return new DoWhileStatementNode(condition, statements);
    }

    private StatementNode countedDoStatementAfterDo() {
        String id = currentToken.getLexeme();
        match(TokenType.ID);

        match(TokenType.ASSIGNMENT);

        ExpressionNode startExpression = arithmeticExpression();

        match(TokenType.COMMA);

        ExpressionNode endExpression = arithmeticExpression();

        List<StatementNode> statements = statementList();

        match(TokenType.END);
        match(TokenType.DO);

        return new DoStatementNode(id, startExpression, endExpression, statements);
    }

    private LogicalExpressionNode logicalExpression() {
        ExpressionNode left = arithmeticExpression();
        TokenType operator = relationalOperator();
        ExpressionNode right = arithmeticExpression();

        return new LogicalExpressionNode(left, operator, right);
    }

    private TokenType relationalOperator() {
        TokenType operator = currentToken.getType();

        switch (operator) {
            case LT:
                match(TokenType.LT);
                return operator;
            case LE:
                match(TokenType.LE);
                return operator;
            case GT:
                match(TokenType.GT);
                return operator;
            case GE:
                match(TokenType.GE);
                return operator;
            case EQ:
                match(TokenType.EQ);
                return operator;
            case NE:
                match(TokenType.NE);
                return operator;
            default:
                error("Expected relational operator");
                return null;
        }
    }

    private ExpressionNode arithmeticExpression() {
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

            ExpressionNode expr = arithmeticExpression();

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

        error("Expected factor: number, identifier, or expression");
        return null;
    }

    private NumberNode number() {
        int value = Integer.parseInt(currentToken.getLexeme());

        match(TokenType.INTEGER);

        return new NumberNode(value);
    }
}