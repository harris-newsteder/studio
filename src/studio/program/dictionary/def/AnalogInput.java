package studio.program.dictionary.def;

import studio.program.Var;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;

public class AnalogInput extends BlockDefinition {
    public static final String NAME = "analog_input";

    public AnalogInput() {
        super(NAME);

        symbol = "M -25 0 q 12.5 -35 25 0 q 12.5 35 25 0";

        pins.add(0, new PinDefinition(
                PinDefinition.Flow.OUTPUT,
                PinDefinition.Side.RIGHT,
                Var.Type.ANALOG_SIGNAL
        ));
    }
}
