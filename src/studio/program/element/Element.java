package studio.program.element;

import javafx.scene.canvas.GraphicsContext;

public class Element {
    public static final String ID = "element";

    /*
     *
     */
    protected boolean hover = false;

    /*
     *
     */
    protected boolean alive = true;

    /*
     *
     */
    protected String id = "element";

    /*
     *
     */
    protected double x = 0;

    /*
     *
     */
    protected double y = 0;

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

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean containsPoint(double x, double y) {
        return false;
    }
}
