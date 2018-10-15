package studio.program;

import javafx.scene.canvas.GraphicsContext;

public class LinkSection {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public static final int INTERACTION_DISTANCE = 2;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkTerminal a = null;
    public LinkTerminal b = null;

    public LinkSection.Orientation orientation;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkSection() {
        a = new LinkTerminal(this);
        b = new LinkTerminal(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void stroke(GraphicsContext gc) {
        gc.save();
        gc.strokeLine(a.x, a.y, b.x, b.y);
        a.draw(gc);
        b.draw(gc);
        gc.restore();
    }

    public boolean hitTest(int x, int y) {
        if (orientation == LinkSection.Orientation.HORIZONTAL) {
            if (Math.abs(y - a.y) > INTERACTION_DISTANCE) return false;
            if (x < Math.min(a.x, b.x)) return false;
            if (x > Math.max(a.x, b.x)) return false;
            return true;
        }

        if (orientation == LinkSection.Orientation.VERTICAL) {
            if (Math.abs(x - a.x) > INTERACTION_DISTANCE) return false;
            if (y < Math.min(a.y, b.y)) return false;
            if (y > Math.max(a.y, b.y)) return false;
            return true;
        }

        return false;
    }

    public void moveY(int y) {
        this.a.y = y;

        if (a.next != null) {
            a.next.y = y;
        }

        this.b.y = y;

        if (b.next != null) {
            b.next.y = y;
        }
    }

    public void moveX(int x) {
        this.a.x = x;

        if (a.next != null) {
            a.next.x = x;
        }

        this.b.x = x;

        if (b.next != null) {
            b.next.x = x;
        }
    }
}
