package parser;

import lexer.*;


import java.nio.file.Files;
import java.nio.file.Path;

public class parserTest {
    public static void main(String[] args) throws Exception {
        String input = Files.readString(Path.of("input.txt"));

        LexicalAnalyzer lexer = new LexicalAnalyzer(input);
        Parser parser = new Parser(lexer);

        int result = parser.parse();
        System.out.println("Result = " + result);
    }
}
