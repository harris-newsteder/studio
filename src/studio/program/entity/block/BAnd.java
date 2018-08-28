package studio.program.entity.block;

import studio.program.Program;
import studio.program.entity.Block;
import studio.program.entity.Pin;

public class BAnd extends Block {
    public BAnd(Program program) {
        super(program);
        text = "AND";
        addPin(new Pin(program, this, Pin.FLOW_INPUT, Pin.SIDE_LEFT));
        addPin(new Pin(program, this, Pin.FLOW_INPUT, Pin.SIDE_LEFT));
        addPin(new Pin(program, this, Pin.FLOW_OUTPUT, Pin.SIDE_RIGHT));
    }
}
