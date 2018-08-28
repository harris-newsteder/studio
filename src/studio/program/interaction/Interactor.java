package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/*
 *
 */
public abstract class Interactor {

    /*
     *
     */
    protected InteractionManager manager;

    /*
     *
     */
    protected Cursor cursor = null;

    public Interactor(InteractionManager manager) {
        this.manager = manager;
        this.cursor = manager.getCursor();
    }

    public abstract void onMouseMoved(MouseEvent event);
    public abstract void onMousePressed(MouseEvent event);
    public abstract void onMouseReleased(MouseEvent event);
    public abstract void onMouseDragged(MouseEvent event);
    public abstract void onScroll(ScrollEvent event);
}
