package studio.program.element;

import studio.program.ui.shape.Rectangle;
import studio.program.Program;
import studio.program.Var;

public class BCompare extends Block {
    public BCompare() {
        super();
        text = ">";
        name = "compare";
    }

    @Override
    public void tick(double dt) {

    }

    @Override
    public void createPins(Program program) {
        double w = ((Rectangle)shape).getWidth();
        double h = ((Rectangle)shape).getHeight();

        Pin p0 = new Pin(this, new Var(Var.Type.NUMBER), Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-w / 2, -20);
        p0.setIndex(0);

        Pin p1 = new Pin(this, new Var(Var.Type.NUMBER), Pin.Flow.INPUT);
        p1.setSide(Pin.Side.LEFT);
        p1.setAttachmentPoint(-w / 2, 20);
        p1.setIndex(1);

        Pin p2 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.OUTPUT);
        p2.setSide(Pin.Side.RIGHT);
        p2.setAttachmentPoint(w / 2, 0);
        p2.setIndex(2);

        program.addElement(p0);
        program.addElement(p1);
        program.addElement(p2);

        addPin(p0);
        addPin(p1);
        addPin(p2);
    }
}

