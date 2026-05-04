package parser;

import java.util.List;

public class IfStatementNode extends StatementNode {
    private LogicalExpressionNode condition;
    private List<StatementNode> thenStatements;
    private List<StatementNode> elseStatements;

    public IfStatementNode(
            LogicalExpressionNode condition,
            List<StatementNode> thenStatements,
            List<StatementNode> elseStatements
    ) {
        this.condition = condition;
        this.thenStatements = thenStatements;
        this.elseStatements = elseStatements;
    }

    @Override
    public void execute(Memory memory) {
        if (condition.evaluate(memory)) {
            for (StatementNode stmt : thenStatements) {
                stmt.execute(memory);
            }
        } else {
            for (StatementNode stmt : elseStatements) {
                stmt.execute(memory);
            }
        }
    }
}