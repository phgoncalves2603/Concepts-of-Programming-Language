package lexer;

public enum TokenType {
        ADDITION,        // +
        SUBTRACTION,     // -
        MULTIPLICATION,  // *
        DIVISION,        // /
        LPAREN,          // (
        RPAREN,          // )
        COMMA,           // ,
        INTEGER,         // integer literal
        EOS,             // end of source

        ASSIGNMENT,      // =
        ID,              // identifier

        PRINT,
        READ,
        IF,
        THEN,
        ELSE,
        END,
        DO,
        WHILE,

        LT,              // .lt.
        LE,              // .le.
        GT,              // .gt.
        GE,              // .ge.
        EQ,              // .eq.
        NE               // .ne.
}