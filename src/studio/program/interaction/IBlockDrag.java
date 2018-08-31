package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Cursor;
import studio.program.Program;
import studio.program.element.Block;
import studio.program.shape.Rectangle;

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
    private Rectangle dragShape = null;

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
        dragShape = (Rectangle)drag.getShape();
        dragOffsetX = cursor.getGraphX() - dragShape.getX();
        dragOffsetY = cursor.getGraphY() - dragShape.getY();
        System.out.println("hooooooooweeeeeeeeee");
    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        dragShape.setX(cursor.getGraphX() - dragOffsetX);
        dragShape.setY(cursor.getGraphY() - dragOffsetY);
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
