package studio.blocklib;

import studio.blocklib.definitions.And;
import studio.blocklib.definitions.DiscreteInput;
import studio.program.Block;

import java.util.HashMap;

public final class BlockLibrary {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private static final HashMap<String, BlockDefinition> definitions = new HashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private BlockLibrary() {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void init() {
        definitions.put(And.Companion.getNAME(), new And());
        definitions.put(DiscreteInput.Companion.getNAME(), new DiscreteInput());
    }

    public static BlockDefinition lookup(String name) {
        return definitions.get(name);
    }

    public static Block construct(String name) {
        return definitions.get(name).constructInstance();
    }
}
