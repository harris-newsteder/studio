package studio.program.element;

import studio.program.Program;
import studio.program.Var;

public class BDiscreteOutput extends Block {
    public BDiscreteOutput() {
        super();
        text = "DO";
        name = "discrete_output";

        Var v = new Var();
        v.setType(Var.Type.U8);
        vars.put("output_pin", v);
    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, Var.Type.DISCRETE_SIGNAL, Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-width / 2, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void tick(double dt) {

    }
}
