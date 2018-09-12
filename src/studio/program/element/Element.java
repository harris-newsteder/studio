package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.util.UID;

public abstract class Element {
    public static final String EID = "element";

    /*
     *
     */
    protected boolean hover = false;

    /*
     *
     */
    protected boolean alive = true;

    /*
     * element identifier, a string that identifies which type of element this is
     */
    protected String eid = "";

    /*
     *
     */
    protected double x = 0;

    /*
     *
     */
    protected double y = 0;

    /*
     *
     */
    private final int uid;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Element() {
        eid = EID;
        uid = UID.generate();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void onEnter() {
        hover = true;
    }
    public void onExit() {
        hover = false;
    }

    public abstract void tick(double dt);
    public abstract void draw(GraphicsContext gc);
    public abstract boolean containsPoint(double x, double y);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public String getEID() {
        return eid;
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

    public int getUID() {
        return uid;
    }
}
