package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Cursor;
import studio.program.element.Link;
import studio.program.element.Pin;

public class ILinker extends Interaction {

    private Pin p1 = null;
    private Pin p2 = null;
    private Link candidate = null;

    public ILinker(InteractionManager manager, Cursor cursor) {
        super(manager, cursor);
    }

    @Override
    public void onMouseMoved(MouseEvent event) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {
        p1 = (Pin)manager.getHover();
        candidate = new Link();
        candidate.setStartPosition(p1.getX(), p1.getY());
        candidate.setEndPosition(cursor.getGraphX(), cursor.getGraphY());

        manager.getProgram().addElement(candidate);
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        candidate.kill();
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        candidate.setEndPosition(cursor.getGraphX(), cursor.getGraphY());
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
