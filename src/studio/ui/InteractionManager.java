package studio.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import studio.program.*;
import studio.ui.action.ADiagnostics;
import studio.ui.action.AGenerateProgram;
import studio.ui.action.AMoveCamera;
import studio.ui.action.Action;
import studio.ui.command.CBlockDrag;
import studio.ui.command.CLink;
import studio.ui.command.CLinkDrag;
import studio.ui.command.Command;

import java.util.ArrayList;

public final class InteractionManager {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final KeyCombination KC_UNDO = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private final KeyCombination KC_REDO = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    private final KeyCombination KC_SAVE = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    public UI ui = null;

    /*
     *
     */
    public Program program = null;

    /*
     *
     */
    public Canvas canvas = null;

    /*
     *
     */
    public Camera camera = null;

    /*
     *
     */
    public Mouse mouse = null;

    /*
     *
     */
    public Collider collider = null;

    /*
     *
     */
    public Element hover = null;

    /*
     *
     */
    private ArrayList<Command> commandTimeline = null;

    /*
     *
     */
    private int timelinePosition = 0;

    /*
     *
     */
    private ArrayList<Action> actions = null;

    /*
     *
     */
    private Command activeCommand = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InteractionManager(UI ui) {
        this.ui = ui;
        program = ui.program;
        canvas = ui.canvas;
        camera = ui.view.camera;
        mouse = new Mouse();
        collider = new Collider(this);
        commandTimeline = new ArrayList<>();

        actions = new ArrayList<>();
        actions.add(new AMoveCamera(this));
        actions.add(new AGenerateProgram(this));
        actions.add(new ADiagnostics(this));

        registerEvents(canvas);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void tick(double dt) {
        for (Action a : actions) {
            a.tick(dt);
        }

        // the current action is finished, add it to the actionTimeline and increment the timeline position
        if (activeCommand != null) {

            activeCommand.tick(dt);

            if (activeCommand.isFinished()) {
                if (!activeCommand.isAborted()) {
                    commandTimeline.add(activeCommand);
                    timelinePosition++;
                }

                activeCommand = null;
            }
        }
    }

    void draw(GraphicsContext gc) {
        for (Action a : actions) {
            a.draw(gc);
        }

        if (activeCommand != null) {
            activeCommand.draw(gc);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void onMouseMoved(MouseEvent event) {
        updateMouse((int)event.getX(), (int)event.getY());

        for (Action a : actions) {
            a.onMouseMoved(event);
        }

        if (activeCommand != null) {
            activeCommand.onMouseMoved(event);
            return;
        }

        updateHover();
    }

    private void onMousePressed(MouseEvent event) {
        // ~_~
        canvas.requestFocus();

        for (Action a : actions) {
            a.onMousePressed(event);
        }

        if (activeCommand != null) {
            activeCommand.onMousePressed(event);
            return;
        }
    }

    private void onMouseReleased(MouseEvent event) {
        for (Action a : actions) {
            a.onMouseReleased(event);
        }

        if (activeCommand != null) {
            activeCommand.onMouseReleased(event);
            return;
        }
    }

    private void onMouseClicked(MouseEvent event) {
        for (Action a : actions) {
            a.onMouseClicked(event);
        }

        if (activeCommand != null) {
            activeCommand.onMouseClicked(event);
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            if (hover != null) {
                if (hover.eid == Pin.EID) {
                    activate(new CLink(this));
                }
            } else {

            }
        }
    }

    private void onMouseDragged(MouseEvent event) {
        updateMouse((int)event.getX(), (int)event.getY());

        for (Action a : actions) {
            a.onMouseDragged(event);
        }

        if (activeCommand != null) {
            activeCommand.onMouseDragged(event);
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            if (hover == null) {

            } else {
                switch (hover.eid) {
                    case Block.EID:
                        activate(new CBlockDrag(this));
                        break;
                    case Link.EID:
                        activate(new CLinkDrag(this));
                        break;
                }
            }
        }
    }

    private void onKeyPressed(KeyEvent event) {
        if (KC_UNDO.match(event)) {
            // there are no actions to undo
            if (timelinePosition == 0) return;

            //
            commandTimeline.get(timelinePosition - 1).undo();
            timelinePosition--;
        }

        if (KC_REDO.match(event)) {
            // at the front of the timeline, can't redo any more
            if (timelinePosition == commandTimeline.size()) return;

            //
            commandTimeline.get(timelinePosition).redo();
            timelinePosition++;
        }

        for (Action a : actions) {
            a.onKeyPressed(event);
        }

        if (activeCommand != null) {
            activeCommand.onKeyPressed(event);
            return;
        }
    }

    private void onKeyReleased(KeyEvent event) {
        for (Action a : actions) {
            a.onKeyReleased(event);
        }

        if (activeCommand != null) {
            activeCommand.onKeyReleased(event);
            return;
        }
    }

    private void onKeyTyped(KeyEvent event) {
        for (Action a : actions) {
            a.onKeyTyped(event);
        }

        if (activeCommand != null) {
            activeCommand.onKeyReleased(event);
            return;
        }
    }

    private void onScroll(ScrollEvent event) {
        for (Action a : actions) {
            a.onScroll(event);
        }

        if (activeCommand != null) {
            activeCommand.onScroll(event);
            return;
        }
    }

    /*
     *
     */
    private void activate(Command action) {
        // we are starting a new action somewhere in the timeline's history, we need to clear all action after where
        // we currently are in the timeline
        if (timelinePosition != commandTimeline.size()) {
            for (int i = commandTimeline.size(); i > timelinePosition; --i) {
                commandTimeline.remove(i - 1);
            }
        }

        activeCommand = action;
        activeCommand.onActivate();
    }

    /*
     * updates the mouses real position and position within the view
     */
    private void updateMouse(int x, int y) {
        mouse.setRealPosition(x, y);
        mouse.setViewPosition(
                (int)(camera.translateX + (x / camera.zoom)),
                (int)(camera.translateY + (y / camera.zoom))
        );
        mouse.setGridPosition(
                (int)(Math.round(mouse.viewX / View.GRID_SIZE) * View.GRID_SIZE),
                (int)(Math.round(mouse.viewY / View.GRID_SIZE) * View.GRID_SIZE)
        );
    }

    /*
     * checks to see if the mouse's new position interacts with any on screen elements
     */
    private void updateHover() {
        Element nh = collider.check(mouse.viewX, mouse.viewY);
        Element oh = hover;

        if (nh != oh) {
            if (oh != null) oh.onExit();
            if (nh != null) nh.onEnter();
            hover = nh;
        }
    }

    /*
     *
     */
    private void registerEvents(Canvas canvas) {
        this.canvas = canvas;

        canvas.setOnMouseMoved(   event -> {onMouseMoved(event);});
        canvas.setOnMousePressed( event -> {onMousePressed(event);});
        canvas.setOnMouseReleased(event -> {onMouseReleased(event);});
        canvas.setOnMouseClicked( event -> {onMouseClicked(event);});
        canvas.setOnMouseDragged( event -> {onMouseDragged(event);});
        canvas.setOnKeyPressed(   event -> {onKeyPressed(event);});
        canvas.setOnKeyReleased(  event -> {onKeyReleased(event);});
        canvas.setOnKeyTyped(     event -> {onKeyTyped(event);});
        canvas.setOnScroll(       event -> {onScroll(event);});
    }
}
