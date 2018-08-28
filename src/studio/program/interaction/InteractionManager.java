package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Program;
import studio.program.entity.Block;
import studio.program.entity.Entity;
import studio.program.entity.Link;
import studio.program.entity.Pin;

import java.util.EnumMap;

/*
 *
 */
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

    /*
     *
     */
    private Program program = null;

    /*
     *
     */
    private Collider collider = null;

    /*
     *
     */
    private Cursor cursor = null;

    /*
     *
     */
    private Entity hover = null;

    /*
     *
     */
    private InteractionType type = InteractionType.NONE;

    /*
     *
     */
    private EnumMap<InteractionType, Interactor> interactions = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InteractionManager(Program program) {
        this.program = program;
        this.cursor = new Cursor();

        collider = new Collider();
        collider.setCursor(cursor);

        interactions = new EnumMap<InteractionType, Interactor>(InteractionType.class);
        interactions.put(InteractionType.NONE,       new INone(this));
        interactions.put(InteractionType.BLOCK_DRAG, new IBlockDrag(this));
        interactions.put(InteractionType.LINKER,     new ILinker(this));
        interactions.put(InteractionType.LINK_DRAG,  new ILinkDrag(this));
        interactions.put(InteractionType.SELECTION,  new ISelector(this));
        interactions.put(InteractionType.CAMERA,     new ICamera(this));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void onMouseMoved(MouseEvent event) {
        // check collision between the mouse and any entities
        updateCursor(event.getX(), event.getY());

        Entity coll = collider.checkCollisions(program.getEntities());

        if (coll == hover) return;

        if (coll != null) coll.onEnter();
        if (hover != null) hover.onExit();
        hover = coll;
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
        updateCursor(event.getX(), event.getY());
        interactions.get(type).onMouseDragged(event);
    }

    public void onScroll(ScrollEvent event) {
        interactions.get(InteractionType.CAMERA).onScroll(event);
    }

    public Program getProgram() {
        return program;
    }

    public Entity getHover() {
        return hover;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public Collider getCollider() {
        return collider;
    }

    private void updateCursor(double rx, double ry) {
        cursor.rx = rx;
        cursor.ry = ry;
        cursor.vx = program.getCamera().tx + (rx / program.getCamera().zoom);
        cursor.vy = program.getCamera().ty + (ry / program.getCamera().zoom);
    }
}
