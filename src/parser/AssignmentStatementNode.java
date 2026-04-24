package parser;

public class AssignmentStatementNode extends StatementNode {
    private String id;
    private ExpressionNode expression;

    public AssignmentStatementNode(String id, ExpressionNode expression) {
        this.id = id.toLowerCase();
        this.expression = expression;
    }

    @Override
    public void execute(Memory memory) {
        memory.set(id, expression.evaluate(memory));
    }
}