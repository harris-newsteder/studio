package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.program.ui.shape.Circle;
import studio.program.Var;
import studio.program.ui.view.View;

public class Pin extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String EID = "pin";

    public static final double LENGTH = 20;

    public enum Flow {
        INPUT,
        OUTPUT
    }

    public enum Side {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    public final Block parent;

    /*
     *
     */
    private boolean linked = false;

    /*
     *
     */
    private Link link = null;

    /*
     *
     */
    public Side side;

    /*
     *
     */
    public Flow flow;


    /*
     *
     */
    public Var var = null;

    /*
     *
     */
    private double attachX = 0;
    private double attachY = 0;

    /*
     *
     */
    public int index = -1;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Pin(Block parent) {
        super(EID);
        this.parent = parent;

        shape = new Circle();
        ((Circle)shape).radius = 6;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void tick(double dt) {
        double x, y;

        x = parent.shape.x;
        y = parent.shape.y;

        switch (side) {
            case TOP:
                x += attachX;
                y += attachY - LENGTH;
                break;
            case RIGHT:
                x += attachX + LENGTH;
                y += attachY;
                break;
            case BOTTOM:
                x += attachX;
                y += attachY + LENGTH;
                break;
            case LEFT:
                x += attachX - LENGTH;
                y += attachY;
                break;
        }

        shape.x = x;
        shape.y = y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(shape.x, shape.y);

        switch (side) {
            case TOP: gc.rotate(0); break;
            case RIGHT: gc.rotate(90); break;
            case BOTTOM: gc.rotate(180); break;
            case LEFT: gc.rotate(270); break;
        }

        gc.strokeLine(0, 0, 0, LENGTH);

        // TODO: use a triangle instead of stroke line
        if (flow == Flow.INPUT) {
            gc.strokeLine(0, LENGTH - 1.8, 3, LENGTH - 5);
            gc.strokeLine(0, LENGTH - 1.8, -3, LENGTH - 5);
        }

        if (!linked) {
            shape.fill(gc);
            shape.stroke(gc);
            if (hover) {
                gc.setFill(View.COLOR_HOVER_MASK);
                shape.fill(gc);
            }
        }

        gc.restore();
    }

    public void setAttachmentPoint(double x, double y) {
        this.attachX = x;
        this.attachY = y;
    }

    public void link(Link link) {
        this.link = link;
        this.linked = true;
    }

    public Link getLink() {
        return link;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public int getIndex() {
        return index;
    }
}
