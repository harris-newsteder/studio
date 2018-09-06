package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.App;

public class Pin extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String ID = "pin";

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

    public static final double LENGTH = 20;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Block parent = null;

    private boolean linked = false;

    private Link link = null;

    private Side side;
    private Flow flow;

    private double radius = 6;

    /*
     *
     */
    private double attachX = 0;
    private double attachY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Pin(Block parent) {
        super();
        id = ID;
        this.parent = parent;
    }


    @Override
    public void tick(double dt) {
        this.x = parent.getX();
        this.y = parent.getY();
        switch (side) {
            case TOP:
                this.x += attachX;
                this.y += attachY - LENGTH;
                break;
            case RIGHT:
                this.x += attachX + LENGTH;
                this.y += attachY;
                break;
            case BOTTOM:
                this.x += attachX;
                this.y += attachY + LENGTH;
                break;
            case LEFT:
                this.x += attachX - LENGTH;
                this.y += attachY;
                break;
        }
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

        if (flow == Flow.INPUT) {
            gc.strokeLine(0, LENGTH - 1.8, 3, LENGTH - 5);
            gc.strokeLine(0, LENGTH - 1.8, -3, LENGTH - 5);
        }

        if (linked) {
            gc.restore();
            return;
        }

        gc.fillOval(-radius, -radius, radius * 2, radius * 2);
        gc.strokeOval(-radius, -radius, radius * 2, radius * 2);
        if (hover) {
            gc.setFill(App.COLOR_HOVER_MASK);
            gc.fillOval(-radius, -radius, radius * 2, radius * 2);
        }
        gc.restore();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        double dx = Math.abs(x - this.x);
        double dy = Math.abs(y - this.y);

        if (dx > radius || dy > radius) return false;
        if (dx + dy <= radius) return true;
        if ((dx * dx) + (dy * dy) <= (radius * radius)) return true;

        return false;
    }

    public void setAttachmentPoint(double x, double y) {
        this.attachX = x;
        this.attachY = y;
    }

    // TODO: remove???
    public void updatePosition() {
        // this.x = parent.getX() + attachX;
        // this.y = parent.getY() + attachY;
    }

    public void link(Link link) {
        this.link = link;
        this.linked = true;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }
}
