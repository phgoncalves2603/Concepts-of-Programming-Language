package parser;

import lexer.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class parserTest {

    public static void main(String[] args) throws Exception {

        // Check if input file is provided
        if (args.length == 0) {
            System.out.println("Error: No input file provided.");
            System.out.println("Usage: java parser.parserTest <inputfile>");
            return;
        }

        // Read entire file as string
        String code = Files.readString(Path.of(args[0]));

        // Create lexer and parser
        LexicalAnalyzer lexer = new LexicalAnalyzer(code);
        Parser parser = new Parser(lexer);

        // Parse program
        ParseTree tree = parser.parse();

        // Execute program (IMPORTANT: replaces evaluate())
        tree.execute();
    }
}