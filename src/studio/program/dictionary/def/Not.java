package studio.program.dictionary.def;

import studio.program.Var;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;

public class Not extends BlockDefinition {
    public static final String NAME = "not";

    public Not() {
        super(NAME);

        symbol = "m -25 20 l 0 -40 l 50 20 l -50 20 m 50 -20";

        pins.add(0, new PinDefinition(
                PinDefinition.Flow.INPUT,
                PinDefinition.Side.LEFT,
                Var.Type.DISCRETE_SIGNAL
        ));

        pins.add(1, new PinDefinition(
                PinDefinition.Flow.OUTPUT,
                PinDefinition.Side.RIGHT,
                Var.Type.DISCRETE_SIGNAL
        ));
    }
}
