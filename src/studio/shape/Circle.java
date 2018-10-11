package studio.shape;

import javafx.scene.canvas.GraphicsContext;

public final class Circle extends Shape {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private int radius = 40;

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
    public boolean containsPoint(int x, int y) {
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
        gc.translate(-radius, -radius);
        gc.strokeOval(0, 0, radius * 2, radius * 2);
        gc.restore();
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.translate(-radius, -radius);
        gc.fillOval(0, 0, radius * 2, radius * 2);
        gc.restore();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
