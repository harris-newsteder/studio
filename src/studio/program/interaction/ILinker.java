package studio.program.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.program.entity.Entity;
import studio.program.entity.Pin;

/*
 *
 */
public class ILinker extends Interactor {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private Pin e1 = null;

    /*
     *
     */
    private Entity e2 = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ILinker(InteractionManager manager) {
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
        e1 = (Pin)manager.getHover();

        if (e1.linked) {
            System.err.println("linker error: pin already linked!");
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        e2 = manager.getCollider().checkCollisions(manager.getProgram().getEntities());
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }
}