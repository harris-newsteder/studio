package studio.program.shape;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Point;

public abstract class Shape {
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract boolean containsPoint(double x, double y);
}
