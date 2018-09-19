package studio.program.dictionary.def;

import studio.program.Var;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;

public class DiscreteInput extends BlockDefinition {
    public static final String NAME = "discrete_input";

    public DiscreteInput() {
        super(NAME);

        pins.add(0, new PinDefinition(
                PinDefinition.Flow.OUTPUT,
                PinDefinition.Side.RIGHT,
                Var.Type.DISCRETE_SIGNAL
        ));
    }
}
