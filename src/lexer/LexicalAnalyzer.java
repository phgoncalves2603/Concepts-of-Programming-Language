package lexer;

public class LexicalAnalyzer {
    private String source;
    private int index;
    private int row;
    private int column;

    public LexicalAnalyzer(String source) {
        this.source = source;
        this.index = 0;
        this.row = 1;
        this.column = 1;
    }

    public Token getToken() {
        skipWhitespace();

        if (index >= source.length()) {
            return new Token(TokenType.EOS, "EOS", row, column);
        }

        char current = source.charAt(index);
        int startColumn = column;

        switch (current) {
            case '+':
                advance();
                return new Token(TokenType.ADDITION, "+", row, startColumn);
            case '-':
                advance();
                return new Token(TokenType.SUBTRACTION, "-", row, startColumn);
            case '*':
                advance();
                return new Token(TokenType.MULTIPLICATION, "*", row, startColumn);
            case '/':
                advance();
                return new Token(TokenType.DIVISION, "/", row, startColumn);
            case '(':
                advance();
                return new Token(TokenType.LPAREN, "(", row, startColumn);
            case ')':
                advance();
                return new Token(TokenType.RPAREN, ")", row, startColumn);
            case ',':
                advance();
                return new Token(TokenType.COMMA, ",", row, startColumn);
            case '=':
                advance();
                return new Token(TokenType.ASSIGNMENT, "=", row, startColumn);
            case '.':
                return relationalOperator();
            default:
                break;
        }

        if (Character.isDigit(current)) {
            return integerLiteral();
        }

        if (Character.isLetter(current)) {
            return identifierOrKeyword();
        }

        throw new RuntimeException(
                "Lexical Error: invalid character '" + current +
                        "' at row " + row + ", column " + column
        );
    }

    private void skipWhitespace() {
        while (index < source.length() && Character.isWhitespace(source.charAt(index))) {
            if (source.charAt(index) == '\n') {
                row++;
                column = 1;
                index++;
            } else {
                advance();
            }
        }
    }

    private Token integerLiteral() {
        int startColumn = column;
        StringBuilder number = new StringBuilder();

        while (index < source.length() && Character.isDigit(source.charAt(index))) {
            number.append(source.charAt(index));
            advance();
        }

        return new Token(TokenType.INTEGER, number.toString(), row, startColumn);
    }

    private Token identifierOrKeyword() {
        int startColumn = column;
        StringBuilder id = new StringBuilder();

        while (index < source.length() && Character.isLetterOrDigit(source.charAt(index))) {
            id.append(source.charAt(index));
            advance();
        }

        String lexeme = id.toString().toLowerCase();

        switch (lexeme) {
            case "print":
                return new Token(TokenType.PRINT, lexeme, row, startColumn);
            case "read":
                return new Token(TokenType.READ, lexeme, row, startColumn);
            case "if":
                return new Token(TokenType.IF, lexeme, row, startColumn);
            case "then":
                return new Token(TokenType.THEN, lexeme, row, startColumn);
            case "else":
                return new Token(TokenType.ELSE, lexeme, row, startColumn);
            case "end":
                return new Token(TokenType.END, lexeme, row, startColumn);
            case "do":
                return new Token(TokenType.DO, lexeme, row, startColumn);
            case "while":
                return new Token(TokenType.WHILE, lexeme, row, startColumn);
            default:
                return new Token(TokenType.ID, lexeme, row, startColumn);
        }
    }

    private Token relationalOperator() {
        int startColumn = column;
        StringBuilder op = new StringBuilder();

        op.append(source.charAt(index));
        advance();

        while (index < source.length() && source.charAt(index) != '.') {
            op.append(source.charAt(index));
            advance();
        }

        if (index >= source.length()) {
            throw new RuntimeException(
                    "Lexical Error: unfinished relational operator at row " + row +
                            ", column " + startColumn
            );
        }

        op.append(source.charAt(index));
        advance();

        String lexeme = op.toString().toLowerCase();

        switch (lexeme) {
            case ".lt.":
                return new Token(TokenType.LT, lexeme, row, startColumn);
            case ".le.":
                return new Token(TokenType.LE, lexeme, row, startColumn);
            case ".gt.":
                return new Token(TokenType.GT, lexeme, row, startColumn);
            case ".ge.":
                return new Token(TokenType.GE, lexeme, row, startColumn);
            case ".eq.":
                return new Token(TokenType.EQ, lexeme, row, startColumn);
            case ".ne.":
                return new Token(TokenType.NE, lexeme, row, startColumn);
            default:
                throw new RuntimeException(
                        "Lexical Error: invalid relational operator '" + lexeme +
                                "' at row " + row + ", column " + startColumn
                );
        }
    }

    private void advance() {
        index++;
        column++;
    }
}