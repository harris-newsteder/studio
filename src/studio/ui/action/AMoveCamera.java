package studio.ui.action;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.ui.Camera;
import studio.ui.InteractionManager;

public final class AMoveCamera extends Action {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Camera camera = null;

    private double dragStartX = 0.0;
    private double dragStartY = 0.0;

    private double centerStartX = 0.0;
    private double centerStartY = 0.0;

    private boolean doPan = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public AMoveCamera(InteractionManager manager) {
        super(manager);

        camera = manager.ui.view.camera;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onMousePressed(MouseEvent event) {
        if (event.getButton() != MouseButton.MIDDLE) return;

        centerStartX = camera.centerX;
        centerStartY = camera.centerY;
        dragStartX = mouse.realX;
        dragStartY = mouse.realY;

        doPan = true;
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if (!doPan) return;

        double dx = mouse.realX - dragStartX;
        double dy = mouse.realY - dragStartY;

        camera.setCenterPosition(
                centerStartX - (dx / camera.zoom),
                centerStartY - (dy / camera.zoom)
        );
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        doPan = false;
    }

    @Override
    public void onScroll(ScrollEvent event) {
        double zoom = camera.zoom;

        // TODO: better zoom-in / zoom-out control
        // TODO: center to cursor when zooming

        if (event.getDeltaY() > 0) {
            camera.setZoom(zoom * 1.1);
        } else {
            camera.setZoom(zoom * 0.9);
        }
    }
}
