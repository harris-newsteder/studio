package studio.program.element;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import studio.App;
import studio.interaction.shape.Circle;
import studio.interaction.shape.Rectangle;
import studio.program.Program;
import studio.program.Var;

public class BDiscreteOutput extends Block {

    private double[] xs = new double[] {
            -25, -12, -12, 12, 12, 25
    };

    private double[] ys = new double[] {
            12, 12, -12, -12, 12, 12
    };

    public BDiscreteOutput() {
        super();
        text = "DO";
        name = "discrete_output";
        shape = new Circle();
        ((Circle) shape).radius = 40;
        vars.put("output_pin", new Var(Var.Type.U8, 5.0));
    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-((Circle)shape).radius, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void tick(double dt) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        shape.fill(gc);
        shape.stroke(gc);

        if (hover) {
            gc.setFill(App.COLOR_HOVER_MASK);
            shape.fill(gc);
        }

        gc.translate(shape.x, shape.y);
        gc.strokePolyline(xs, ys, 6);

        gc.restore();
    }
}
