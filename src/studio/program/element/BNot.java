package studio.program.element;

import studio.interaction.shape.Rectangle;
import studio.program.Program;
import studio.program.Var;

public class BNot extends Block {
    public BNot() {
        super();
        text = "!";
        name = "not";
    }

    @Override
    public void createPins(Program program) {
        double w = ((Rectangle)shape).getWidth();
        double h = ((Rectangle)shape).getHeight();

        Pin p0 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-w / 2, 0);
        p0.setIndex(0);

        Pin p1 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.OUTPUT);
        p1.setSide(Pin.Side.RIGHT);
        p1.setAttachmentPoint(w / 2, 0);
        p1.setIndex(1);

        program.addElement(p0);
        program.addElement(p1);

        addPin(p0);
        addPin(p1);
    }

    @Override
    public void tick(double dt) {

    }
}
