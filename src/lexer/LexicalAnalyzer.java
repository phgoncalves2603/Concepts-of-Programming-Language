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
        // Skip whitespace
        while (index < source.length() && Character.isWhitespace(source.charAt(index))) {
            if (source.charAt(index) == '\n') {
                row++;
                column = 1;
            } else {
                column++;
            }
            index++;
        }

        // End of source
        if (index >= source.length()) {
            return new Token(TokenType.EOS, "EOS", row, column);
        }

        char current = source.charAt(index);
        int startColumn = column;

        // Single-character tokens
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
        }

        // Integer literal
        if (Character.isDigit(current)) {
            StringBuilder number = new StringBuilder();
            while (index < source.length() && Character.isDigit(source.charAt(index))) {
                number.append(source.charAt(index));
                advance();
            }
            return new Token(TokenType.INTEGER, number.toString(), row, startColumn);
        }

        // Invalid character
        throw new RuntimeException(
                "Invalid character '" + current + "' at row " + row + ", column " + column
        );
    }

    private void advance() {
        index++;
        column++;
    }
}
