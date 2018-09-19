package studio.program.dictionary.def;

import studio.program.Var;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;

public class And extends BlockDefinition {
    public static final String NAME = "and";

    public And() {
        super(NAME);

        // TODO: I don't like this because it's possible to fuck up the index of the pins but it will have to do for now
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
