package studio.program.ui.shape;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    public double radius = 40.0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Circle() {
        super();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean containsPoint(double x, double y) {
        double dx = Math.abs(x - this.x);
        double dy = Math.abs(y - this.y);

        if (dx > radius || dy > radius) return false;
        if (dx + dy <= radius) return true;
        if ((dx * dx) + (dy * dy) <= (radius * radius)) return true;

        return false;
    }

    @Override
    public void stroke(GraphicsContext gc) {
        gc.save();
        gc.translate(x - radius, y - radius);
        gc.strokeOval(0, 0, radius * 2, radius * 2);
        gc.restore();
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.translate(x - radius, y - radius);
        gc.fillOval(0, 0, radius * 2, radius * 2);
        gc.restore();
    }
}
