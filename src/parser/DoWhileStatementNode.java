package parser;

import java.util.List;

public class DoWhileStatementNode extends StatementNode {
    private LogicalExpressionNode condition;
    private List<StatementNode> statements;

    public DoWhileStatementNode(LogicalExpressionNode condition, List<StatementNode> statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public void execute(Memory memory) {
        while (condition.evaluate(memory)) {
            for (StatementNode stmt : statements) {
                stmt.execute(memory);
            }
        }
    }
}