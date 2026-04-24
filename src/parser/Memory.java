package parser;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private Map<String, Integer> variables = new HashMap<>();

    public int get(String id) {
        return variables.getOrDefault(id.toLowerCase(), 0);
    }

    public void set(String id, int value) {
        variables.put(id.toLowerCase(), value);
    }
}