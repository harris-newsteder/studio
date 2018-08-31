package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.program.shape.Shape;

public class Element {
    public static final String ID = "element";

    protected boolean hover = false;
    protected boolean selected = false;
    protected boolean alive = true;
    protected Shape shape = null;
    protected String id = "element";

    public Element() {

    }

    public void onEnter() {
        hover = true;
    }

    public void onExit() {
        hover = false;
    }

    public void tick(double dt) {

    }

    public void draw(GraphicsContext gc) {

    }

    public Shape getShape() {
        return shape;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public String getId() {
        return id;
    }
}
