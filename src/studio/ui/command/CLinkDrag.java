package studio.ui.command;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import studio.program.Link;
import studio.program.Section;
import studio.program.Terminal;
import studio.ui.InteractionManager;

public final class CLinkDrag extends Command {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * the link this command is modifying
     */
    private Link link = null;

    /*
     * the section in the link being moved
     */
    private Section drag = null;

    /*
     * new sections a and b; these are needed if dragging a the drag section off of a pin
     */
    private Section nsa = null;
    private Section nsb = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public CLinkDrag(InteractionManager manager) {
        super(manager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONSs
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivate() {
        link = ((Link)manager.hover);
        drag = link.hitSection;

        if (drag == null) abort();
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        if (nsa != null) nsa.stroke(gc);
        if (nsb != null) nsb.stroke(gc);
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if (drag.orientation == Section.Orientation.HORIZONTAL) {
            hdrag();
        } else {
            vdrag();
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        cleanLink();

        if (nsa != null) {
            link.addSection(nsa);
        }

        if (nsb != null) {
            link.addSection(nsb);
        }

        finish();
    }

    private void hdrag() {
        if (repositionTerminalVertically(drag.a, mouse.gridY)) {
            nsa = new Section();

            drag.a.type = Terminal.Type.CORNER;
            drag.a.next = nsa.a;

            nsa.a.type = Terminal.Type.CORNER;
            nsa.a.next = drag.a;

            nsa.a.x = drag.a.x;
            nsa.a.y = drag.a.y;

            nsa.b.type = Terminal.Type.ANCHOR;
            nsa.b.x = drag.a.x;
            nsa.b.y = drag.a.y;

            nsa.orientation = Section.Orientation.VERTICAL;
        }

        if (repositionTerminalVertically(drag.b, mouse.gridY)) {
            nsb = new Section();

            drag.b.type = Terminal.Type.CORNER;
            drag.b.next = nsb.a;

            nsb.a.type = Terminal.Type.CORNER;
            nsb.a.next = drag.b;

            nsb.a.x = drag.b.x;
            nsb.a.y = drag.b.y;

            nsb.b.type = Terminal.Type.ANCHOR;
            nsb.b.x = drag.b.x;
            nsb.b.y = drag.b.y;

            nsb.orientation = Section.Orientation.VERTICAL;
        }

        for (Terminal junction : drag.junctions) {
            junction.y = mouse.gridY;
        }
    }

    private void vdrag() {
        if (repositionTerminalHorizontally(drag.a, mouse.gridX)) {
            nsa = new Section();

            drag.a.type = Terminal.Type.CORNER;
            drag.a.next = nsa.a;

            nsa.a.type = Terminal.Type.CORNER;
            nsa.a.next = drag.a;

            nsa.a.x = drag.a.x;
            nsa.a.y = drag.a.y;

            nsa.b.type = Terminal.Type.ANCHOR;
            nsa.b.x = drag.a.x;
            nsa.b.y = drag.a.y;

            nsa.orientation = Section.Orientation.HORIZONTAL;
        }

        if (repositionTerminalHorizontally(drag.b, mouse.gridX)) {
            nsb = new Section();

            drag.b.type = Terminal.Type.CORNER;
            drag.b.next = nsb.a;

            nsb.a.type = Terminal.Type.CORNER;
            nsb.a.next = drag.b;

            nsb.a.x = drag.b.x;
            nsb.a.y = drag.b.y;

            nsb.b.type = Terminal.Type.ANCHOR;
            nsb.b.x = drag.b.x;
            nsb.b.y = drag.b.y;

            nsb.orientation = Section.Orientation.HORIZONTAL;
        }
    }

    private void cleanLink() {

    }

    private boolean repositionTerminalVertically(Terminal t, int ny) {
        t.y = ny;

        switch (t.type) {
            case CORNER:
                t.next.y = ny;
                break;
            case JUNCTION:
                break;
            case ANCHOR:
                return true;
        }

        return false;
    }

    private boolean repositionTerminalHorizontally(Terminal t, int nx) {
        t.x = nx;

        switch (t.type) {
            case CORNER:
                t.next.x = nx;
                break;
            case JUNCTION:
                break;
            case ANCHOR:
                return true;
        }

        return false;
    }
}
