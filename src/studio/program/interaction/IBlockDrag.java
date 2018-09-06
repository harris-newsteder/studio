package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Cursor;
import studio.program.Program;
import studio.program.element.Block;

public class IBlockDrag extends Interaction {
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
    private double dragOffsetX = 0;

    /*
     *
     */
    private double dragOffsetY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public IBlockDrag(InteractionManager manager, Cursor cursor) {
        super(manager, cursor);
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
        dragOffsetX = cursor.getGraphX() - drag.getX();
        dragOffsetY = cursor.getGraphY() - drag.getY();
    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        drag.setPosition(
                Math.round((cursor.getGraphX() - dragOffsetX) / Program.GRID_SIZE) * Program.GRID_SIZE,
                Math.round((cursor.getGraphY() - dragOffsetY) / Program.GRID_SIZE) * Program.GRID_SIZE
        );
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
