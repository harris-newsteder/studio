package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.Camera;

/*
 *
 */
public class ICamera extends Interactor {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private double dragStartX = 0;

    /*
     *
     */
    private double dragStartY = 0;

    /*
     *
     */
    private Camera camera = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ICamera(InteractionManager manager) {
        super(manager);
        camera = manager.getProgram().getCamera();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onMouseMoved(MouseEvent event) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {
        dragStartX = camera.x;
        dragStartY = camera.y;
        cursor.beginRealDrag();
    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        camera.x = dragStartX - (cursor.getRealDeltaX() / camera.zoom);
        camera.y = dragStartY - (cursor.getRealDeltaY() / camera.zoom);
    }

    @Override
    public void onScroll(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            camera.zoom *= 1.1;
        } else {
            camera.zoom *= 0.9;
        }
    }
}
