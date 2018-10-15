package studio.program;

import javafx.scene.canvas.GraphicsContext;
import studio.shape.Circle;
import studio.ui.View;

public final class Pin extends Element {
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
     * the block this pin belongs to
     */
    public Block parent;

    /*
     * this pin's number on the parent block, can be [0, +inf)
     */
    public int index = -1;

    /*
     * whether or not this pin is linked
     */
    public boolean linked = false;

    /*
     * this link this pin is connected to
     */
    public Link link = null;

    /*
     * which side of the parent block this pin is on (TOP, RIGHT, BOTTOM, LEFT)
     */
    public Side side;

    /*
     * whether this is an input or an output pin
     */
    public Flow flow;

    /*
     * the signal variable associated with this pin
     */
    public Variable var = null;

    /*
     *
     */
    public int x = 0;
    public int y = 0;

    /*
     *
     */
    public Circle body;

    /*
     * the point where the pin is attached on the parent block
     */
    public double attachX = 0;
    public double attachY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Pin() {
        super(EID);

        body = new Circle();
        body.radius = 6;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void tick(double dt) {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(x, y);

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
            body.fill(gc);
            body.stroke(gc);
            if (hover) {
                gc.setFill(View.COLOR_HOVER_MASK);
                body.fill(gc);
            }
        }

        gc.restore();
    }

    @Override
    public boolean hitTest(int x, int y) {
        return body.hitTest(x - this.x, y - this.y);
    }

    public void reposition() {
        x = parent.x;
        y = parent.y;

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
    }

    public void link(Link to) {
        link = to;
        linked = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FLUENT FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Pin create() {
        return new Pin();
    }

    public Pin withParent(Block parent) {
        this.parent = parent;
        return this;
    }

    public Pin withVariable(Variable var) {
        this.var = var;
        return this;
    }

    public Pin atIndex(int index) {
        this.index = index;
        return this;
    }

    public Pin ofFlow(Flow flow) {
        this.flow = flow;
        return this;
    }

    public Pin onSide(Side side) {
        this.side = side;
        return this;
    }

    public Pin attachedAt(int x, int y) {
        this.attachX = x;
        this.attachY = y;
        return this;
    }
}
