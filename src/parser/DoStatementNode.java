package parser;

import java.util.List;

public class DoStatementNode extends StatementNode {
    private String id;
    private ExpressionNode startExpression;
    private ExpressionNode endExpression;
    private List<StatementNode> statements;

    public DoStatementNode(
            String id,
            ExpressionNode startExpression,
            ExpressionNode endExpression,
            List<StatementNode> statements
    ) {
        this.id = id.toLowerCase();
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.statements = statements;
    }

    @Override
    public void execute(Memory memory) {
        int start = startExpression.evaluate(memory);
        int end = endExpression.evaluate(memory);

        for (int i = start; i <= end; i++) {
            memory.set(id, i);

            for (StatementNode stmt : statements) {
                stmt.execute(memory);
            }
        }
    }
}