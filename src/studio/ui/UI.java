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
    private InteractionManager interactionManager = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public UI(Program program, Canvas canvas) {
        this.program = program;
        this.canvas = canvas;

        view = new View(this);
        interactionManager = new InteractionManager(this);

        view.interactionManager = interactionManager;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Canvas getCanvas() {
        return canvas;
    }

    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

    public Program getProgram() {
        return program;
    }

    public View getView() {
        return view;
    }

    public void tick(double dt) {
        interactionManager.tick(dt);
    }

    public void draw(GraphicsContext gc) {
        view.draw(gc);
    }
}
