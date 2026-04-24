package parser;

public class IdNode extends FactorNode {
    private String id;

    public IdNode(String id) {
        this.id = id.toLowerCase();
    }

    @Override
    public int evaluate(Memory memory) {
        return memory.get(id);
    }
}