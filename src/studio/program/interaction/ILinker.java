package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Cursor;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.Pin;

/*
 * this class is responsible for managing the interaction of creating new links and adding sink pins to existing links
 * it is not responsible for verifying that a link is valid or of notifying pins that they have been linked, that logic
 * is implemented in the link class itself
 */
public class ILinker extends Interaction {

    private Pin p1 = null;
    private Pin p2 = null;
    private Link candidate = null;
    private Element over = null;

    public ILinker(InteractionManager manager, Cursor cursor) {
        super(manager, cursor);
    }

    @Override
    public void onMouseMoved(MouseEvent event) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {
        p1 = null;
        p2 = null;
        over = null;

        p1 = (Pin)manager.getHover();
        p1.onExit();
        candidate = new Link();
        candidate.setStartPosition(p1.getX(), p1.getY());
        candidate.setEndPosition(cursor.getGraphX(), cursor.getGraphY());

        manager.getProgram().addElement(candidate);
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        //
        if (!candidate.place(p1, (Pin)over)) {
            candidate.kill();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        Element nh = manager.getCollider().checkElements();

        if (over == null) {
            candidate.setEndPosition(cursor.getGraphX(), cursor.getGraphY());
        } else {
            candidate.setEndPosition(over.getX(), over.getY());
        }

        if (nh == over) return;
        if (nh == p1) return;
        // TODO: only over on pins
        if (over != null) over.onExit();
        if (nh != null) nh.onEnter();
        over = nh;

    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
