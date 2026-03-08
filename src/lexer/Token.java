package lexer;

public class Token {

    private TokenType type;
    private String lexeme;
    private int row;
    private int column;

    public Token(TokenType type, String lexeme, int row, int column) {

        if (type == null) {
            throw new IllegalArgumentException("Token type cannot be null");
        }

        if (lexeme == null) {
            throw new IllegalArgumentException("Token lexeme cannot be null");
        }

        if (row <= 0 || column <= 0) {
            throw new IllegalArgumentException("Invalid token position");
        }

        this.type = type;
        this.lexeme = lexeme;
        this.row = row;
        this.column = column;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format(
                "Token(type=%s, lexeme='%s', row=%d, col=%d)",
                type, lexeme, row, column
        );
    }
}