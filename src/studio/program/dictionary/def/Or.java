package studio.program.dictionary.def;

import studio.program.Var;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;

public class Or extends BlockDefinition {
    public static final String NAME = "or";

    public Or() {
        super(NAME);

        pins.add(0, new PinDefinition(
                PinDefinition.Flow.INPUT,
                PinDefinition.Side.LEFT,
                Var.Type.DISCRETE_SIGNAL
        ));

        pins.add(1, new PinDefinition(
                PinDefinition.Flow.INPUT,
                PinDefinition.Side.LEFT,
                Var.Type.DISCRETE_SIGNAL
        ));

        pins.add(2, new PinDefinition(
                PinDefinition.Flow.OUTPUT,
                PinDefinition.Side.RIGHT,
                Var.Type.DISCRETE_SIGNAL
        ));
    }
}
