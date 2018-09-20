package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.program.ui.shape.Circle;
import studio.program.Var;
import studio.program.ui.shape.Shape;
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

    private static final double[] xs = new double[] {
        5, 0, -5, 0
    };

    private static final double[] ys = new double[] {
        LENGTH - 7, LENGTH, LENGTH - 7, LENGTH - 5
    };

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

    /*
     *
     */
    public Circle circle = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Pin(Block parent) {
        super(EID);
        this.parent = parent;

        circle = new Circle();
        ((Circle)circle).radius = 6;

        shape = circle;
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

        circle.x = x;
        circle.y = y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(circle.x, circle.y);

        switch (side) {
            case TOP: gc.rotate(0); break;
            case RIGHT: gc.rotate(90); break;
            case BOTTOM: gc.rotate(180); break;
            case LEFT: gc.rotate(270); break;
        }

        gc.strokeLine(0, 0, 0, LENGTH);

        // TODO: use a triangle instead of stroke line
        if (flow == Flow.INPUT) {
            gc.save();
            gc.setFill(View.COLOR_DARK);
            gc.beginPath();
            gc.appendSVGPath("M 0 " + LENGTH + " l 5 -7 l -10 0 l 5 7");
            gc.fill();
            gc.closePath();
            gc.restore();
        }

        if (!linked) {
            circle.fill(gc);
            circle.stroke(gc);
            if (hover) {
                gc.setFill(View.COLOR_HOVER_MASK);
                circle.fill(gc);
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
