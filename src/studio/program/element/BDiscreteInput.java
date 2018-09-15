package studio.program.element;

import studio.interaction.shape.Rectangle;
import studio.program.Program;
import studio.program.Var;

public class BDiscreteInput extends Block {
    public BDiscreteInput() {
        super();
        text = "DI";
        name = "discrete_input";

        vars.put("input_pin", new Var(Var.Type.U8, 6.0));
    }

    @Override
    public void createPins(Program program) {
        double w = ((Rectangle)shape).getWidth();
        double h = ((Rectangle)shape).getHeight();

        Pin p0 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.OUTPUT);
        p0.setSide(Pin.Side.RIGHT);
        p0.setAttachmentPoint(w / 2, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void tick(double dt) {

    }
}