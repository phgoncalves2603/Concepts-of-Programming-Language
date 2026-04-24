package parser;

import java.util.Scanner;

public class ReadStatementNode extends StatementNode {
    private String id;

    public ReadStatementNode(String id) {
        this.id = id.toLowerCase();
    }

    @Override
    public void execute(Memory memory) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) {
            throw new RuntimeException("Input must be an integer");
        }

        memory.set(id, sc.nextInt());
    }
}