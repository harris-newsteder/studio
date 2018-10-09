package studio.shape;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * come on you know what this is
     */
    private int width = 80;

    /*
     * this too
     */
    private int height = 80;

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
    public boolean containsPoint(int x, int y) {
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
        gc.translate(-(width / 2), -(height / 2));
        gc.strokeRect(0, 0, width, height);
        gc.restore();
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.translate(-(width / 2), -(height / 2));
        gc.fillRect(0, 0, width, height);
        gc.restore();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
