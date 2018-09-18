package studio.program.element;

import studio.program.ui.shape.Rectangle;
import studio.program.Program;
import studio.program.Var;

public class BTimer extends Block {
    public BTimer() {
        super();
        text = "TIMER";
        name = "timer";

        vars.put("frequency", new Var(Var.Type.U8, 2.0));
        vars.put("old_time", new Var(Var.Type.U32, 0.0));
        vars.put("dt", new Var(Var.Type.F32, 0.0));
        vars.put("timer", new Var(Var.Type.F32, 0.0));
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
