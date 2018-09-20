package studio.program.element.block;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;
import studio.program.Var;
import studio.program.element.Block;
import studio.program.element.Pin;
import studio.program.ui.shape.Circle;
import studio.program.ui.shape.Shape;
import studio.program.ui.view.View;

public class BDiscreteInput extends Block {
    public static final String NAME = "discrete_input";

    private static final double[] symbolXs = new double[] {
            -20.0, -13.0, -13.0, 13.0, 13.0, 20.0
    };

    private static final double[] symbolYs = new double[] {
            15.0, 15.0, -15.0, -15.0, 15.0, 15.0
    };

    public Circle circle = null;

    public BDiscreteInput() {
        super(NAME);

        circle = new Circle();
        shape = circle;

        vars.put("input_pin", new Var(Var.Type.U8, 6));
    }

    @Override
    public void createPins(Program program) {
        Pin p;

        //
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

        gc.strokePolyline(symbolXs, symbolYs, 6);

        if (hover) {
            gc.setFill(View.COLOR_HOVER_MASK);
            shape.fill(gc);
        }

        gc.restore();
    }
}
