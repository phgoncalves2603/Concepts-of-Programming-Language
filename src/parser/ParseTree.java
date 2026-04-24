package parser;

import java.util.List;

public class ParseTree {
    private List<StatementNode> statements;

    public ParseTree(List<StatementNode> statements) {
        this.statements = statements;
    }

    public void execute() {
        Memory memory = new Memory();

        for (StatementNode stmt : statements) {
            stmt.execute(memory);
        }
    }
}