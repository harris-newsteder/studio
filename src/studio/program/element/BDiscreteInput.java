package studio.program.element;

import studio.program.ui.shape.Circle;
import studio.program.Program;
import studio.program.Var;

public class BDiscreteInput extends Block {
    public BDiscreteInput() {
        super();
        shape = new Circle();
        ((Circle) shape).radius = 40;
        text = "DI";
        name = "discrete_input";

        vars.put("input_pin", new Var(Var.Type.U8, 6.0));
    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.OUTPUT);
        p0.setSide(Pin.Side.RIGHT);
        p0.setAttachmentPoint(((Circle)shape).radius, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void tick(double dt) {

    }
}