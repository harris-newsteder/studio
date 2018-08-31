package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.App;
import studio.program.shape.Circle;
import studio.program.shape.Rectangle;

public class Sum extends Block {
    public Sum() {
        super();
        shape = new Rectangle();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(App.COLOR_WHITE);
        shape.fill(gc);
        gc.setStroke(App.COLOR_DARK);
        gc.setLineWidth(2.0);
        shape.stroke(gc);

        if (hover) {
            gc.setFill(App.COLOR_HOVER_MASK);
            shape.fill(gc);
        }
    }
}
