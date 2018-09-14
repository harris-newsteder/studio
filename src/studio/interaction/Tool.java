package studio.interaction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class Tool {
    protected Cursor cursor = null;
    protected InteractionManager manager = null;
    protected boolean active = false;

    public Tool(InteractionManager manager) {
        this.manager = manager;
        this.cursor = manager.getCursor();
    }

    public abstract void draw(GraphicsContext gc);
    public abstract void onMouseMoved(MouseEvent event);
    public abstract void onMouseClicked(MouseEvent event);
    public abstract void onMousePressed(MouseEvent event);
    public abstract void onMouseDragged(MouseEvent event);
    public abstract void onMouseReleased(MouseEvent event);
    public abstract void onKeyPressed(KeyEvent event);
    public abstract void onScroll(ScrollEvent event);
}
