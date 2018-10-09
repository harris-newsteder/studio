package studio.ui;

import javafx.scene.input.MouseEvent;
import studio.program.Block;

public class ABlockDrag extends Action {
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

    public ABlockDrag(ActionManager manager) {
        super(manager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivate() {
        drag = (Block)manager.getHover();

        originalX = drag.getBody().getX();
        originalY = drag.getBody().getY();

        dox = mouse.getViewX() - drag.getBody().getX();
        doy = mouse.getViewY() - drag.getBody().getY();
    }

    @Override
    public void undo() {
        drag.getBody().setPosition(originalX, originalY);
    }

    @Override
    public void redo() {
        drag.getBody().setPosition(finalX, finalY);
    }

    @Override
    public void onMouseDragged(MouseEvent event) {

        int nx = mouse.getViewX() - dox;
        int ny = mouse.getViewY() - doy;

        int gx = (int)(Math.round(nx / View.GRID_SIZE) * View.GRID_SIZE);
        int gy = (int)(Math.round(ny / View.GRID_SIZE) * View.GRID_SIZE);

        drag.getBody().setPosition(gx, gy);
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        finalX = drag.getBody().getX();
        finalY = drag.getBody().getY();

        finish();
    }
}
