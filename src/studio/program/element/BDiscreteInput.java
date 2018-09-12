package studio.program.element;

import studio.program.Program;
import studio.program.Var;

public class BDiscreteInput extends Block {
    public BDiscreteInput() {
        super();
        text = "DI";
        name = "discrete_input";

        Var v = new Var();
        v.setType(Var.Type.U8);
        vars.put("input_pin", v);
    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, Var.Type.DISCRETE_SIGNAL, Pin.Flow.OUTPUT);
        p0.setSide(Pin.Side.RIGHT);
        p0.setAttachmentPoint(width / 2, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void tick(double dt) {

    }
}