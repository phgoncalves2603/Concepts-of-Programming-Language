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

        System.out.print("> "); //for some mysterious reason this doesn't show up
        System.out.flush(); //this fix it
        if (!sc.hasNextInt()) {
            throw new RuntimeException("Runtime Error: Input must be an integer");
        }

        int value = sc.nextInt();

        memory.set(id, value);
    }
}