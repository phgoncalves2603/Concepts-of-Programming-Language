package parser;

public class PrintStatementNode extends StatementNode {
    private String id;

    public PrintStatementNode(String id) {
        this.id = id.toLowerCase();
    }

    @Override
    public void execute(Memory memory) {
        System.out.println(memory.get(id));
    }
}