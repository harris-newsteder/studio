package studio.program;

import javafx.scene.canvas.GraphicsContext;
import studio.util.UID;

public abstract class Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * element id
     */
    public final String eid;

    /*
     * universal id, a unique integer value given to every element instance
     * this is mostly needed for code generation so I know which blocks are which when linking things together
     */
    public final int uid;

    /*
     *
     */
    public boolean hover = false;

    /*
     *
     */
    public boolean alive = true;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Element(String eid) {
        // the element id must be set by the subclass
        this.eid = eid;

        // generate a new universal id
        uid = UID.generate();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void kill() {
        alive = false;
    }

    public void onEnter() {
        hover = true;
    }

    public void onExit() {
        hover = false;
    }

    // TODO: have draw in this class?????
    public abstract void tick(double dt);
    public abstract void draw(GraphicsContext gc);
    public abstract boolean hitTest(int x, int y);
}
