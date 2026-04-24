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
        while (index < source.length() && Character.isWhitespace(source.charAt(index))) {
            if (source.charAt(index) == '\n') {
                row++;
                column = 1;
            } else {
                column++;
            }
            index++;
        }

        if (index >= source.length()) {
            return new Token(TokenType.EOS, "EOS", row, column);
        }

        char current = source.charAt(index);
        int startColumn = column;

        switch (current) {
            case '+': advance(); return new Token(TokenType.ADDITION, "+", row, startColumn);
            case '-': advance(); return new Token(TokenType.SUBTRACTION, "-", row, startColumn);
            case '*': advance(); return new Token(TokenType.MULTIPLICATION, "*", row, startColumn);
            case '/': advance(); return new Token(TokenType.DIVISION, "/", row, startColumn);
            case '(': advance(); return new Token(TokenType.LPAREN, "(", row, startColumn);
            case ')': advance(); return new Token(TokenType.RPAREN, ")", row, startColumn);
            case '=': advance(); return new Token(TokenType.ASSIGNMENT, "=", row, startColumn);
        }

        // INTEGER
        if (Character.isDigit(current)) {
            StringBuilder number = new StringBuilder();

            while (index < source.length() && Character.isDigit(source.charAt(index))) {
                number.append(source.charAt(index));
                advance();
            }

            return new Token(TokenType.INTEGER, number.toString(), row, startColumn);
        }

        // IDENTIFIER / KEYWORDS
        if (Character.isLetter(current)) {
            StringBuilder id = new StringBuilder();

            while (index < source.length() &&
                    Character.isLetterOrDigit(source.charAt(index))) {
                id.append(source.charAt(index));
                advance();
            }

            String lexeme = id.toString().toLowerCase();

            if (lexeme.equals("print")) return new Token(TokenType.PRINT, lexeme, row, startColumn);
            if (lexeme.equals("read")) return new Token(TokenType.READ, lexeme, row, startColumn);

            return new Token(TokenType.ID, lexeme, row, startColumn);
        }

        throw new RuntimeException(
                "Lexical Error: invalid character '" + current +
                        "' at row " + row + ", column " + column
        );
    }

    private void advance() {
        index++;
        column++;
    }
}