package studio.program.interaction;

import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Cursor;
import studio.program.Program;
import studio.program.element.Block;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.Pin;

import java.util.EnumMap;

public class InteractionManager {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum InteractionType {
        NONE,
        BLOCK_DRAG,
        LINKER,
        LINK_DRAG,
        SELECTION,
        CAMERA
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Program program = null;
    private Cursor cursor = null;
    private Element hover = null;
    private Collider collider = null;
    private InteractionType type = InteractionType.NONE;

    /*
     *
     */
    private EnumMap<InteractionType, Interaction> interactions = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InteractionManager(Program program) {
        this.program = program;
        this.cursor = program.getCursor();
        this.collider = new Collider();

        interactions = new EnumMap<InteractionType, Interaction>(InteractionType.class);
        interactions.put(InteractionType.NONE,       new INone(this, cursor));
        interactions.put(InteractionType.BLOCK_DRAG, new IBlockDrag(this, cursor));
        interactions.put(InteractionType.LINKER,     new ILinker(this, cursor));
        interactions.put(InteractionType.LINK_DRAG,  new ILinkDrag(this, cursor));
        interactions.put(InteractionType.SELECTION,  new ISelector(this, cursor));
        interactions.put(InteractionType.CAMERA,     new ICamera(this, cursor));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void onMouseMoved(MouseEvent event) {
        // TODO: move to collider
        Element nh = null;

        for (Element e : program.getElements()) {

            if (e.getShape().containsPoint(cursor.getGraph().getX(), cursor.getGraph().getY())) {
                nh = e;
            }
        }

        if (nh == hover) return;

        // a change has occured
        if (nh != null) nh.onEnter();
        if (hover != null) hover.onExit();
        hover = nh;
    }

    public void onMousePressed(MouseEvent event) {
        switch (event.getButton()) {
            case PRIMARY:
                // the user is pressing the primary button on bare canvas, going to drag it to make a selection of
                // entities
                if (hover == null) {
                    type = InteractionType.SELECTION;
                } else if (hover.getId() == Block.ID) {
                    type = InteractionType.BLOCK_DRAG;
                    // the user is clicking on an un-linked pin, going to make a new link between two pins
                } else if (hover.getId() == Pin.ID) {
                    type = InteractionType.LINKER;
                } else if (hover.getId() == Link.ID) {
                    type = InteractionType.LINK_DRAG;
                }
                break;
            case SECONDARY:
                break;
            case MIDDLE:
                type = InteractionType.CAMERA;
                break;
        }

        interactions.get(type).onMousePressed(event);
    }

    public void onMouseReleased(MouseEvent event) {
        interactions.get(type).onMouseReleased(event);

        type = InteractionType.NONE;
    }

    public void onMouseDragged(MouseEvent event) {
        interactions.get(type).onMouseDragged(event);
    }

    public void onScroll(ScrollEvent event) {

    }

    public void onContextMenuRequested(ContextMenuEvent event) {

    }

    public Element getHover() {
        return hover;
    }
}
