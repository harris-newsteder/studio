package studio.program.ui.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.SVGPath;

public class Path extends Shape {
    /*
     *
     */
    public String content = "";

    /*
     *
     */
    private SVGPath path = null;

    public Path() {
        path = new SVGPath();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        /*
         * this feels like cheating but I'm sure not going to waste my time writing a collision algorithm for arbitrary
         * SVG paths
         */
        path.setContent(content);

        double lx = x - this.x;
        double ly = y - this.y;

        return path.contains(lx, ly);
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
