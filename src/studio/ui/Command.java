package studio.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class Command {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean finished = false;

    protected InteractionManager manager = null;
    protected Mouse mouse = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Command(InteractionManager manager) {
        this.manager = manager;
        this.mouse = manager.getMouse();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public abstract void onActivate();
    public abstract void undo();
    public abstract void redo();

    public void tick(double dt)                   {}
    public void draw(GraphicsContext gc)          {}
    public void onMouseMoved(MouseEvent event)    {}
    public void onMousePressed(MouseEvent event)  {}
    public void onMouseReleased(MouseEvent event) {}
    public void onMouseClicked(MouseEvent event)  {}
    public void onMouseDragged(MouseEvent event)  {}
    public void onKeyPressed(KeyEvent event)      {}
    public void onKeyReleased(KeyEvent event)     {}
    public void onKeyTyped(KeyEvent event)        {}
    public void onScroll(ScrollEvent event)       {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void finish() {
        finished = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isFinished() {
        return finished;
    }
}
