package studio.program.ui.shape;

import javafx.scene.canvas.GraphicsContext;

public class Path extends Shape {
    /*
     *
     */
    public String content = "";

    @Override
    public boolean containsPoint(double x, double y) {



        return false;
    }

    @Override
    public void stroke(GraphicsContext gc) {
        gc.save();
        gc.beginPath();
        gc.appendSVGPath(content);
        gc.fill();
        gc.stroke();
        gc.restore();
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.save();
        gc.beginPath();
        gc.appendSVGPath(content);
        gc.fill();
        gc.closePath();
        gc.restore();
    }
}
