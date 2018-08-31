package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.App;
import studio.program.Point;

import java.util.ArrayList;

/*
 * a block is any element that has pins
 */
public class Block extends Element {
    public static final String ID = "block";

    protected ArrayList<Pin> pins = null;

    public Block() {
        super();
        id = ID;
        pins = new ArrayList<>();
    }

    @Override
    public void draw(GraphicsContext gc) {
    }

    public void addPin(Pin pin) {

    }
}
