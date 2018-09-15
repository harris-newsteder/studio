package studio.program.element;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import studio.App;
import studio.interaction.shape.Rectangle;
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
        double w = ((Rectangle)shape).getWidth();
        double h = ((Rectangle)shape).getHeight();

        Pin p0 = new Pin(this, new Var(Var.Type.DISCRETE_SIGNAL), Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-w / 2, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
    }

    @Override
    public void tick(double dt) {

    }
}
