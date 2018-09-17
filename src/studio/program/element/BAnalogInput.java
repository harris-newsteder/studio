package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.App;
import studio.interaction.shape.Circle;
import studio.program.Program;
import studio.program.Var;

public class BAnalogInput extends Block {



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public BAnalogInput() {
        super();
        text = "AI";
        name = "analog_input";
        shape = new Circle();

        vars.put("input_pin", new Var(Var.Type.U8, 0));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void tick(double dt) {

    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, new Var(Var.Type.ANALOG_SIGNAL), Pin.Flow.OUTPUT);
        p0.setSide(Pin.Side.RIGHT);
        p0.setAttachmentPoint(((Circle)shape).radius, 0);
        p0.setIndex(0);

        program.addElement(p0);

        addPin(p0);
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
        gc.beginPath();
        gc.appendSVGPath("M -25 0 q 12.5 -35 25 0 q 12.5 35 25 0");
        gc.stroke();
        gc.closePath();

        gc.restore();
    }
}
