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
    private Block parent;

    /*
     * this pin's number on the parent block, can be [0, +inf)
     */
    private int index = -1;

    /*
     * whether or not this pin is linked
     */
    private boolean linked = false;

    /*
     * this link this pin is connected to
     */
    private Link link = null;

    /*
     * which side of the parent block this pin is on (TOP, RIGHT, BOTTOM, LEFT)
     */
    private Side side;

    /*
     * whether this is an input or an output pin
     */
    private Flow flow;

    /*
     * the signal variable associated with this pin
     */
    private Variable variable = null;

    /*
     *
     */
    private double attachX = 0;
    private double attachY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Pin() {
        super(EID);

        body = new Circle();
        ((Circle)body).setRadius(6);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Pin create() {
        return new Pin();
    }

    @Override
    public void tick(double dt) {
        double x, y;

        x = parent.getBody().getX();
        y = parent.body.getY();

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

        body.setPosition((int)x, (int)y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(body.getX(), body.getY());

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public Variable getVariable() {
        return variable;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FLUENT FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Pin withParent(Block parent) {
        this.parent = parent;
        return this;
    }

    public Pin withVariable(Variable variable) {
        this.variable = variable;
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
