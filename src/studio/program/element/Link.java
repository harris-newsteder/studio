package studio.program.element;

import javafx.scene.canvas.GraphicsContext;

public class Link extends Element {
    public static final String ID = "link";

    private double startX = 0;
    private double startY = 0;

    private double endX = 0;
    private double endY = 0;

    public Link() {
        super();
        id = Link.ID;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.strokeLine(startX, startY, endX, endY);
        gc.restore();
    }

    public void setStartPosition(double x, double y) {
        startX = x;
        startY = y;
    }

    public void setEndPosition(double x, double y) {
        endX = x;
        endY = y;
    }

}
