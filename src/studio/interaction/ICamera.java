package studio.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.view.Camera;

public class ICamera extends Interaction {

    private Camera camera = null;

    private double sx = 0;
    private double sy = 0;
    private double dsx = 0;
    private double dsy = 0;
    private double dx = 0;
    private double dy = 0;

    public ICamera(InteractionManager manager, Cursor cursor) {
        super(manager, cursor);
        camera = manager.getView().getCamera();
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        sx = camera.getCenterX();
        sy = camera.getCenterY();
        dsx = cursor.getRealX();
        dsy = cursor.getRealY();
    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        dx = cursor.getRealX() - dsx;
        dy = cursor.getRealY() - dsy;

        camera.setCenter(
                sx - (dx / camera.getZoom()),
                sy - (dy / camera.getZoom())
        );
    }

    @Override
    public void onScroll(ScrollEvent event) {
        // TODO: better interaction here (center on zoom location)
        double z = camera.getZoom();

        if (event.getDeltaY() > 0) {
            camera.setZoom(z * 1.1);
        } else {
            camera.setZoom(z * 0.9);
        }
    }
}
