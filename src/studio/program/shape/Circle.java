package studio.program.shape;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Point;

public class Circle extends Shape {
    private double radius = 40;

    private double x = 0;
    private double y = 0;

    public Circle() {
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        gc.fillOval(-radius, -radius, radius * 2, radius * 2);
        gc.restore();
    }

    @Override
    public void stroke(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        gc.strokeOval(-radius, -radius, radius * 2, radius * 2);
        gc.restore();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        double dx = Math.abs(x - this.x);
        double dy = Math.abs(y - this.y);

        if (dx > radius || dy > radius) return false;
        if (dx + dy <= radius) return true;
        if (((dx * dx) + (dy * dy)) <= (radius * radius)) return true;

        return false;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
