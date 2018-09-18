package studio.program.ui.shape;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Polyline extends Shape {

    private static final double INTERACTION_DISTANCE = 2.0;

    /*
     *
     */
    public ArrayList<LineSection> sections = null;

    public Polyline() {

    }

    @Override
    public boolean containsPoint(double x, double y) {
        // go through every section and do a simple comparison to see if the requested point is "near" any on the of the
        // sections
        for (LineSection ls : sections) {
            if (ls.orientation == LineSection.Orientation.HORIZONTAL) {
                if (x < ls.startX || x > ls.endX) continue;
                if (Math.abs(y - ls.startY) < INTERACTION_DISTANCE) return true;
            } else {
                if (y < ls.startY || y > ls.endY) continue;
                if (Math.abs(x - ls.startX) < INTERACTION_DISTANCE) return true;
            }
        }
        return false;
    }

    @Override
    public void stroke(GraphicsContext gc) {
        for (LineSection ls : sections) {
            gc.strokeLine(ls.startX, ls.startY, ls.endX, ls.endY);
        }
    }

    @Override
    public void fill(GraphicsContext gc) {

    }
}
