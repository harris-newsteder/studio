package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Program;
import studio.program.entity.Block;

public class IBlockDrag extends Interactor {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private Block drag = null;

    /*
     *
     */
    private double dragStartX = 0;

    /*
     *
     */
    private double dragStartY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public IBlockDrag(InteractionManager manager) {
        super(manager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onMouseMoved(MouseEvent event) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {
        drag = (Block)manager.getHover();
        dragStartX = drag.x;
        dragStartY = drag.y;
        cursor.beginViewDrag();
    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        drag.x = Math.round((dragStartX + cursor.getViewDeltaX()) / Program.GRID_SIZE) * Program.GRID_SIZE;
        drag.y = Math.round((dragStartY + cursor.getViewDeltaY()) / Program.GRID_SIZE) * Program.GRID_SIZE;
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
