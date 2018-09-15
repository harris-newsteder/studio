package studio.interaction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.interaction.shape.Rectangle;
import studio.interaction.shape.Shape;
import studio.program.Program;
import studio.program.element.Block;
import studio.view.View;

public class TMove extends Tool {
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

    public TMove(InteractionManager manager) {
        super(manager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(GraphicsContext gc) {

    }

    @Override
    public void onMouseMoved(MouseEvent event) {

    }

    @Override
    public void onMouseClicked(MouseEvent event) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {
        active = false;
        if (event.getButton() != MouseButton.PRIMARY) return;
        if (manager.getHover() == null) return;
        if (manager.getHover().eid != Block.EID) return;
        active = true;

        drag = (Block)manager.getHover();
        Shape ds = ((Block)manager.getHover()).getShape();
        dragOffsetX = cursor.getViewX() - ds.x;
        dragOffsetY = cursor.getViewY() - ds.y;
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        if (!active) return;
    }

    @Override
    public void onKeyPressed(KeyEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if (!active) return;

        Rectangle r = (Rectangle)drag.getShape();

        r.setPosition(
                Math.round((cursor.getViewX() - dragOffsetX) / View.GRID_SIZE) * View.GRID_SIZE,
                Math.round((cursor.getViewY() - dragOffsetY) / View.GRID_SIZE) * View.GRID_SIZE
        );
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}
