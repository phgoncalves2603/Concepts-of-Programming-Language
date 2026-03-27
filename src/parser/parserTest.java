package parser;

import lexer.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class parserTest {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Error: No input file provided.\n");
            System.out.println("Usage: java parser.parserTest <inputfile>");
            System.out.println("Example: java parser.parserTest input.txt");
            return;
        }

        String code = Files.readString(Path.of(args[0]));

        LexicalAnalyzer lexer = new LexicalAnalyzer(code);

        Parser parser = new Parser(lexer);
        ParseTree tree = parser.parse();
        System.out.println("Result = " + tree.evaluate());

    }
}