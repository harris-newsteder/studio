package studio.program.element.block;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;
import studio.program.Var;
import studio.program.element.Block;
import studio.program.element.Pin;
import studio.program.ui.shape.Path;
import studio.program.ui.shape.Shape;
import studio.program.ui.view.View;

public class BAnd extends Block {
    public static final String NAME = "and";

    private Path path = null;

    public BAnd() {
        super(NAME);

        path = new Path();
        path.content = "M -40 -40 l 40 0 c 53 0 53 80 0 80 l -40 0 l 0 -80";
        shape = path;
    }

    @Override
    public void createPins(Program program) {
        Pin p;

        // 0
        p = new Pin(this);
        p.index = 0;
        p.flow = Pin.Flow.INPUT;
        p.side = Pin.Side.LEFT;
        p.var = new Var(Var.Type.DISCRETE_SIGNAL);
        p.setAttachmentPoint(-40, -20);
        addPin(p);
        program.addElement(p);

        // 1
        p = new Pin(this);
        p.index = 1;
        p.flow = Pin.Flow.INPUT;
        p.side = Pin.Side.LEFT;
        p.var = new Var(Var.Type.DISCRETE_SIGNAL);
        p.setAttachmentPoint(-40, 20);
        addPin(p);
        program.addElement(p);

        // 2
        p = new Pin(this);
        p.index = 2;
        p.flow = Pin.Flow.OUTPUT;
        p.side = Pin.Side.RIGHT;
        p.var = new Var(Var.Type.DISCRETE_SIGNAL);
        p.setAttachmentPoint(40, 0);
        addPin(p);
        program.addElement(p);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(shape.x, shape.y);

        shape.fill(gc);
        shape.stroke(gc);

        gc.setFill(View.COLOR_DARK);
        gc.fillText("AND", -2, 0);

        if (hover) {
            gc.setFill(View.COLOR_HOVER_MASK);
            shape.fill(gc);
        }

        gc.restore();
    }
}
