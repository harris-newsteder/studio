package studio.program.dictionary;

import java.util.ArrayList;

/*
 * block name
 * block symbol (what it looks like how to draw it etc.)
 * pin types and numbers and descriptions
 * generation code
 * simulation logic
 */
public class BlockDefinition {
    /*
     *
     */
    public final String name;

    /*
     *
     */
    public ArrayList<PinDefinition> pins = null;

    /*
     *
     */
    public String symbol;

    public BlockDefinition(String name) {
        this.name = name;
        pins = new ArrayList<>();
    }
}
