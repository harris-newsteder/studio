package studio.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;

public final class UI {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private Program program = null;

    /*
     *
     */
    private Canvas canvas = null;

    /*
     *
     */
    private View view = null;

    /*
     *
     */
    private ActionManager actionManager = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public UI(Program program, Canvas canvas) {
        this.program = program;
        this.canvas = canvas;

        view = new View(this);
        actionManager = new ActionManager(this);

        view.actionManager = actionManager;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Canvas getCanvas() {
        return canvas;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public Program getProgram() {
        return program;
    }

    public View getView() {
        return view;
    }

    public void tick(double dt) {
        actionManager.tick(dt);
    }

    public void draw(GraphicsContext gc) {
        view.draw(gc);
    }
}
