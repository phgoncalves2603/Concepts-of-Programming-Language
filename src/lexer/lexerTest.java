package lexer;

import java.nio.file.Files;
import java.nio.file.Path;

public class lexerTest {
    public static void main(String[] args) throws Exception {
        String code = Files.readString(Path.of("input.txt"));

        LexicalAnalyzer lexer = new LexicalAnalyzer(code);
        Token token;

        do {
            token = lexer.getToken();
            System.out.println(token);
        } while (token.getType() != TokenType.EOS);
    }
}
