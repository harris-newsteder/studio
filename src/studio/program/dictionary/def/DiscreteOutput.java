package studio.program.dictionary.def;

import studio.program.Var;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;

public class DiscreteOutput extends BlockDefinition {
    public static final String NAME = "discrete_output";

    public DiscreteOutput() {
        super(NAME);

        pins.add(0, new PinDefinition(
                PinDefinition.Flow.INPUT,
                PinDefinition.Side.LEFT,
                Var.Type.DISCRETE_SIGNAL
        ));
    }
}
