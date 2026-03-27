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
    private ExpressionNode expression() {
        System.out.println("Enter <Expression>");
        TermNode leftTerm = term();
        ExpressionNode left = new UnaryExpressionNode(leftTerm);
        left = expressionPrime(left);
        System.out.println("Exit <Expression>");
        return left;
    }

    // Expression_Prime → + Term Expression_Prime | - Term Expression_Prime | null
    private ExpressionNode expressionPrime(ExpressionNode left) {
        System.out.println("Enter <Expression_Prime>");

        if (currentToken.getType() == TokenType.ADDITION) {
            TokenType op = currentToken.getType();
            match(TokenType.ADDITION);
            TermNode right = term();
            ExpressionNode newLeft = new BinaryExpressionNode(left, op, right);
            System.out.println("Exit <Expression_Prime>");
            return expressionPrime(newLeft);

        } else if (currentToken.getType() == TokenType.SUBTRACTION) {
            TokenType op = currentToken.getType();
            match(TokenType.SUBTRACTION);
            TermNode right = term();
            ExpressionNode newLeft = new BinaryExpressionNode(left, op, right);
            System.out.println("Exit <Expression_Prime>");
            return expressionPrime(newLeft);
        }

        System.out.println("Exit <Expression_Prime>");
        return left;
    }

    // Term → Factor Term_Prime
    private TermNode term() {
        System.out.println("Enter <Term>");
        FactorNode leftFactor = factor();
        TermNode left = new UnaryTermNode(leftFactor);
        left = termPrime(left);
        System.out.println("Exit <Term>");
        return left;
    }

    // Term_Prime → * Factor Term_Prime | / Factor Term_Prime | null
    private TermNode termPrime(TermNode left) {
        System.out.println("Enter <Term_Prime>");

        if (currentToken.getType() == TokenType.MULTIPLICATION) {
            TokenType op = currentToken.getType();
            match(TokenType.MULTIPLICATION);
            FactorNode right = factor();
            TermNode newLeft = new BinaryTermNode(left, op, right);
            System.out.println("Exit <Term_Prime>");
            return termPrime(newLeft);

        } else if (currentToken.getType() == TokenType.DIVISION) {
            TokenType op = currentToken.getType();
            match(TokenType.DIVISION);
            FactorNode right = factor();
            TermNode newLeft = new BinaryTermNode(left, op, right);
            System.out.println("Exit <Term_Prime>");
            return termPrime(newLeft);
        }

        System.out.println("Exit <Term_Prime>");
        return left;
    }

    // Factor → ( Expression ) | - Factor | Number
    private FactorNode factor() {
        System.out.println("Enter <Factor>");

        FactorNode node;

        if (currentToken.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            ExpressionNode expr = expression();
            match(TokenType.RPAREN);
            node = new ParenthesizedFactorNode(expr);

        } else if (currentToken.getType() == TokenType.SUBTRACTION) {
            match(TokenType.SUBTRACTION);
            node = new NegativeFactorNode(factor());

        } else {
            node = number();
        }

        System.out.println("Exit <Factor>");
        return node;
    }

    // Number → INTEGER
    private NumberNode number() {
        System.out.println("Enter <Number>");

        if (currentToken.getType() == TokenType.INTEGER) {
            int value = Integer.parseInt(currentToken.getLexeme());
            match(TokenType.INTEGER);
            System.out.println("Exit <Number>");
            return new NumberNode(value);
        } else {
            error("Expected INTEGER");
            return null;
        }
    }

    public ParseTree parse() {
        if (currentToken.getType() == TokenType.EOS) {
            error("Empty expression");
        }

        ExpressionNode root = expression();

        if (currentToken.getType() != TokenType.EOS) {
            error("Unexpected tokens after expression");
        }

        System.out.println("Parsing completed successfully.");
        return new ParseTree(root);
    }
}