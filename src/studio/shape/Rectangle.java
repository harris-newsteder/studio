package studio.shape;

import javafx.scene.canvas.GraphicsContext;

public final class Rectangle extends Shape {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * come on you know what this is
     */
    public int width = 80;

    /*
     * this too
     */
    public int height = 80;

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
    public boolean hitTest(int lx, int ly) {
        if (lx >= -(width  / 2) &&
            lx <= +(width  / 2) &&
            ly >= -(height / 2) &&
            ly <= +(height / 2)) {
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
}
