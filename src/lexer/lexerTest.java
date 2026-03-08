package lexer;

import java.nio.file.Files;
import java.nio.file.Path;

public class lexerTest {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Error: No input file provided.");
            System.out.println("Usage: java lexer.lexerTest <inputfile>");
            return;
        }

        String code = Files.readString(Path.of(args[0]));

        LexicalAnalyzer lexer = new LexicalAnalyzer(code);
        Token token;

        do {
            token = lexer.getToken();
            System.out.println(token);
        } while (token.getType() != TokenType.EOS);

    }
}