package studio.program.element.block;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;
import studio.program.Var;
import studio.program.element.Block;
import studio.program.element.Pin;
import studio.program.ui.shape.Circle;
import studio.program.ui.view.View;

public class BAnalogInput extends Block {
    public static final String NAME = "analog_input";

    public BAnalogInput() {
        super(NAME);

        shape = new Circle();
        ((Circle) shape).radius = 40;
    }

    @Override
    public void createPins(Program program) {
        Pin p;

        p = new Pin(this);
        p.index = 0;
        p.flow = Pin.Flow.OUTPUT;
        p.side = Pin.Side.RIGHT;
        p.var = new Var(Var.Type.DISCRETE_SIGNAL);
        p.setAttachmentPoint(((Circle)shape).radius, 0);
        addPin(p);
        program.addElement(p);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(shape.x, shape.y);

        shape.fill(gc);
        shape.stroke(gc);

        gc.beginPath();
        gc.appendSVGPath("M -25 0 q 12.5 -35 25 0 q 12.5 35 25 0");
        gc.stroke();
        gc.closePath();

        if (hover) {
            gc.setFill(View.COLOR_HOVER_MASK);
            shape.fill(gc);
        }

        gc.restore();
    }
}
