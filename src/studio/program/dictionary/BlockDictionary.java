package studio.program.dictionary;

import studio.program.dictionary.def.*;

import java.util.HashMap;

public final class BlockDictionary {
    private BlockDictionary() {}

    private static HashMap<String, BlockDefinition> definitions = null;

    public static void init() {
        definitions = new HashMap<>();
        definitions.put(AnalogInput.NAME, new AnalogInput());
        definitions.put(And.NAME, new And());
        definitions.put(DiscreteInput.NAME, new DiscreteInput());
        definitions.put(DiscreteOutput.NAME, new DiscreteOutput());
        definitions.put(Not.NAME, new Not());
        definitions.put(Or.NAME, new Or());
        definitions.put(Sum.NAME, new Sum());
    }

    public static BlockDefinition lookup(String name) {
        return definitions.get(name);
    }
}
