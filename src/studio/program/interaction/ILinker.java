package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Cursor;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.LinkCandidate;
import studio.program.element.Pin;

/*
 * this class is responsible for managing the interaction of creating new links and adding sink pins to existing links
 * it is not responsible for verifying that a link is valid or of notifying pins that they have been linked, that logic
 * is implemented in the LinkCandidate class
 */
public class ILinker extends Interaction {

    /*
     *
     */
    private Pin start = null;

    /*
     *
     */
    private Element end = null;

    /*
     *
     */
    private LinkCandidate linkCandidate = null;

    /*
     *
     */
    private boolean validEnd = false;

    public ILinker(InteractionManager manager, Cursor cursor) {
        super(manager, cursor);
        linkCandidate = manager.getProgram().getLinkCandidate();
    }

    @Override
    public void onMouseMoved(MouseEvent event) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {
        validEnd = false;

        start = (Pin)manager.getHover();
        start.onExit();

        linkCandidate.setActive(true);
        linkCandidate.setStart(start);
        linkCandidate.setEndPosition(cursor.getGraphX(), cursor.getGraphY());

        end = null;
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        if (validEnd) {
            Link newLink = linkCandidate.createLink();

            if (newLink != null) {
                manager.getProgram().addElement(newLink);
            }
        }

        linkCandidate.setActive(false);
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        // snap to the end element's position if valid
        if (validEnd) {
            linkCandidate.setEndPosition(end.getX(), end.getY());
        } else {
            linkCandidate.setEndPosition(cursor.getGraphX(), cursor.getGraphY());
        }

        Element nh = manager.getCollider().checkElements();

        // the mouse is still over the element that it was hovering over before the new drag occurred
        if (nh == end) return;

        // no matter if the end is valid or not, there is definitely a new element that the mouse is over and that means
        // the old element should no longer be "hover" status
        if (end != null) end.onExit();

        // check to see if the new ending element is valid
        validEnd = linkCandidate.isValidEnd(nh);

        // apply "hover" status to the new element and hand it over the the linkCandidate
        if (validEnd) {
            nh.onEnter();
            linkCandidate.setEnd(nh);
        }

        // update the new end element on this side
        end = nh;
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
