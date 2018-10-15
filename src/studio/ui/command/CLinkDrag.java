package studio.ui.command;

import javafx.scene.input.MouseEvent;
import studio.program.Link;
import studio.program.LinkSection;
import studio.ui.InteractionManager;
import studio.ui.View;

public final class CLinkDrag extends Command {

    private LinkSection drag = null;

    private int dsx = 0;
    private int dsy = 0;

    public CLinkDrag(InteractionManager manager) {
        super(manager);
    }

    @Override
    public void onActivate() {
        drag = ((Link)manager.hover).hitSection;

        if (drag == null) abort();

        dsx = mouse.viewX;
        dsy = mouse.viewY;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        int gx = (int)(Math.round(mouse.viewX  / View.GRID_SIZE) * View.GRID_SIZE);
        int gy = (int)(Math.round(mouse.viewY  / View.GRID_SIZE) * View.GRID_SIZE);

        if (drag.orientation == LinkSection.Orientation.HORIZONTAL) {
            drag.moveY(gy);
        } else {
            drag.moveX(gx);
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        finish();
    }
}
