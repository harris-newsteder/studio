package studio.interaction.shape;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * come on you know what this is
     */
    private double width = 80.0;

    /*
     * this too
     */
    private double height = 80.0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Rectangle() {
        super();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    @Override
    public void stroke(GraphicsContext gc) {
        gc.save();
        gc.translate(x - (width / 2), y - (height / 2));
        gc.strokeRect(0, 0, width, height);
        gc.restore();
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.translate(x - (width / 2), y - (height / 2));
        gc.fillRect(0, 0, width, height);
        gc.restore();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
