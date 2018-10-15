package studio.program;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Section {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public static final int INTERACTION_DISTANCE = 3;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Terminal a = null;
    public Terminal b = null;

    public ArrayList<Terminal> junctions;

    public Section.Orientation orientation;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Section() {
        a = new Terminal(this);
        b = new Terminal(this);

        junctions = new ArrayList<>();
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
        if (orientation == Section.Orientation.HORIZONTAL) {
            if (Math.abs(y - a.y) > INTERACTION_DISTANCE) return false;
            if (x < Math.min(a.x, b.x)) return false;
            if (x > Math.max(a.x, b.x)) return false;
            return true;
        }

        if (orientation == Section.Orientation.VERTICAL) {
            if (Math.abs(x - a.x) > INTERACTION_DISTANCE) return false;
            if (y < Math.min(a.y, b.y)) return false;
            if (y > Math.max(a.y, b.y)) return false;
            return true;
        }

        return false;
    }
}
