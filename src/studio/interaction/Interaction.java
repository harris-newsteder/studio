package studio.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

// TODO: remove uneccessary MouseEvent arguments
// TODO: remove onMouseMoved and OnScroll and change to more standardized function calls: being, drag, end?????

public abstract class Interaction {
    /*
     *
     */
    protected InteractionManager manager;

    /*
     *
     */
    protected Cursor cursor = null;

    public Interaction(InteractionManager manager, Cursor cursor) {
        this.manager = manager;
        this.cursor = cursor;
    }

    public abstract void onMouseMoved(MouseEvent event);
    public abstract void onMousePressed(MouseEvent event);
    public abstract void onMouseReleased(MouseEvent event);
    public abstract void onMouseDragged(MouseEvent event);
    public abstract void onScroll(ScrollEvent event);
}
