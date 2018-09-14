package studio.program.element;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import studio.App;
import studio.program.Program;
import studio.program.Var;

public class BDiscreteOutput extends Block {

    private double[] xs = new double[] {
            -30, -15, -15, 15, 15, 30
    };

    private double[] ys = new double[] {
            15, 15, -15, -15, 15, 15
    };

    public BDiscreteOutput() {
        super();
        text = "DO";
        name = "discrete_output";
        vars.put("output_pin", new Var(Var.Type.U8, 5.0));
    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-width / 2, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        gc.fillRect(-width / 2, -height / 2, width, height);
        gc.strokeRect(-width / 2, -height / 2, width, height);
        if (hover) {
            gc.setFill(App.COLOR_HOVER_MASK);
            gc.fillRect(-width / 2, -height / 2, width, height);
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(App.COLOR_DARK);
        gc.strokePolyline(xs, ys, 6);
        gc.restore();
    }

    @Override
    public void tick(double dt) {

    }
}
