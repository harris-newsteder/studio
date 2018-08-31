package studio.program.shape;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    private double width = 80;
    private double height = 80;
    private double x = 0;
    private double y = 0;

    public Rectangle() {
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        gc.fillRect(-width / 2, -height / 2, width, height);
        gc.restore();
    }

    @Override
    public void stroke(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        gc.strokeRect(-width / 2, -height / 2, width, height);
        gc.restore();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        if (x >= this.x - (width  / 2) &&
            x <= this.x + (width  / 2) &&
            y >= this.y - (height / 2) &&
            y <= this.y + (height / 2)) {
            return true;
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
