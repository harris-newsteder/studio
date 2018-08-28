package studio.program.entity.block;

import studio.program.Program;
import studio.program.entity.Block;
import studio.program.entity.Pin;

public class BSum extends Block {
    public BSum(Program program) {
        super(program);
        text = "+";
        addPin(new Pin(program,this, Pin.FLOW_INPUT, Pin.SIDE_LEFT));
        addPin(new Pin(program,this, Pin.FLOW_INPUT, Pin.SIDE_LEFT));
        addPin(new Pin(program,this, Pin.FLOW_OUTPUT, Pin.SIDE_RIGHT));
    }
}
