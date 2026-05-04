package parser;

import lexer.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class parserTest {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Error: No input file provided.");
            System.out.println("Usage: java parser.parserTest <input-file>");
            return;
        }

        String code = Files.readString(Path.of(args[0]));

        try {
            LexicalAnalyzer lexer = new LexicalAnalyzer(code);
            Parser parser = new Parser(lexer);
            ParseTree tree = parser.parse();

            tree.execute();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}