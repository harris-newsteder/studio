package studio.interaction.shape;

import javafx.scene.canvas.GraphicsContext;
import studio.program.element.LinkSection;

public class Polyline extends Shape {
    public Polyline() {

    }

    @Override
    public boolean containsPoint(double x, double y) {
        // go through every section and do a simple comparison to see if the requested point is "near" any on the of the
        // sections
//        for (LinkSection ls : sections) {
//            if (ls.getOrientation() == LinkSection.Orientation.HORIZONTAL) {
//                if (x < ls.getStartX() || x > ls.getEndX()) continue;
//                if (Math.abs(y - ls.getStartY()) < INTERACTION_DISTANCE) return true;
//            } else {
//                if (y < ls.getStartY() || y > ls.getEndY()) continue;
//                if (Math.abs(x - ls.getStartX()) < INTERACTION_DISTANCE) return true;
//            }
//        }
//        return false;

        return false;
    }

    @Override
    public void stroke(GraphicsContext gc) {

    }

    @Override
    public void fill(GraphicsContext gc) {

    }
}
