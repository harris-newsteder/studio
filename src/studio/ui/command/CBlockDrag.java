package studio.ui.command;

import javafx.scene.input.MouseEvent;
import studio.program.Block;
import studio.ui.InteractionManager;
import studio.ui.View;

public final class CBlockDrag extends Command {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * the block affected by this action
     */
    private Block drag = null;

    /*
     * drag offset
     */
    private int dox = 0;
    private int doy = 0;

    /*
     * the original position of the block (used for undo)
     */
    private int originalX = 0;
    private int originalY = 0;

    /*
     * the final position of the block (used for redo)
     */
    private int finalX = 0;
    private int finalY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public CBlockDrag(InteractionManager manager) {
        super(manager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivate() {
        drag = (Block)manager.hover;

        originalX = drag.x;
        originalY = drag.y;

        dox = mouse.viewX - drag.x;
        doy = mouse.viewY - drag.y;
    }

    @Override
    public void undo() {
        drag.moveTo(originalX, originalY);
    }

    @Override
    public void redo() {
        drag.moveTo(finalX, finalY);
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        int nx = mouse.viewX - dox;
        int ny = mouse.viewY - doy;

        int gx = (int)(Math.round(nx / View.GRID_SIZE) * View.GRID_SIZE);
        int gy = (int)(Math.round(ny / View.GRID_SIZE) * View.GRID_SIZE);

        drag.moveTo(gx, gy);
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        finalX = drag.x;
        finalY = drag.y;

        finish();
    }
}
