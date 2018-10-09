package studio.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import studio.program.Block;
import studio.program.Element;
import studio.program.Program;

import java.util.ArrayList;

public class ActionManager {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    enum ActionType {
        NONE,
        BLOCK_DRAG,
        LINK_DRAG,
        LINK,
    }

    private final KeyCombination KC_UNDO = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private final KeyCombination KC_REDO = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    private final KeyCombination KC_SAVE = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private UI ui = null;

    /*
     *
     */
    private Program program = null;

    /*
     *
     */
    private Canvas canvas = null;

    /*
     *
     */
    private Camera camera = null;

    /*
     *
     */
    private Mouse mouse = null;

    /*
     *
     */
    private Collider collider = null;

    /*
     *
     */
    private Element hover = null;

    /*
     *
     */
    private ArrayList<Action> actionTimeline = null;

    /*
     *
     */
    private int timelinePosition = 0;

    /*
     *
     */
    private ArrayList<Action> specialActions = null;

    /*
     *
     */
    private Action activeAction = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ActionManager(UI ui) {
        program = ui.getProgram();
        canvas = ui.getCanvas();
        camera = ui.getView().getCamera();
        mouse = new Mouse();
        collider = new Collider(this);
        actionTimeline = new ArrayList<>();
        specialActions = new ArrayList<>();

        registerCanvasEvents(canvas);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void tick(double dt) {

        // the current action is finished, add it to the actionTimeline and increment the timeline position
        if (activeAction != null) {
            if (activeAction.isFinished()) {
                actionTimeline.add(activeAction);
                timelinePosition++;
                activeAction = null;
            }
        }
    }

    void draw(GraphicsContext gc) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void onMouseMoved(MouseEvent event) {
        updateMouse((int)event.getX(), (int)event.getY());

        if (activeAction != null) {
            activeAction.onMouseMoved(event);
            return;
        }

        updateHover();
    }

    private void onMousePressed(MouseEvent event) {
        // ~_~
        canvas.requestFocus();
        if (activeAction != null) {
            activeAction.onMousePressed(event);
            return;
        }
    }

    private void onMouseReleased(MouseEvent event) {
        if (activeAction != null) {
            activeAction.onMouseReleased(event);
            return;
        }
    }

    private void onMouseClicked(MouseEvent event) {
        if (activeAction != null) {
            activeAction.onMouseClicked(event);
            return;
        }
    }

    private void onMouseDragged(MouseEvent event) {
        updateMouse((int)event.getX(), (int)event.getY());

        if (activeAction != null) {
            activeAction.onMouseDragged(event);
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            if (hover == null) {

            } else {
                switch (hover.getEID()) {
                    case Block.EID:
                        activate(new ABlockDrag(this));
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
            actionTimeline.get(timelinePosition - 1).undo();
            timelinePosition--;
        }

        if (KC_REDO.match(event)) {
            // at the front of the timeline, can't redo any more
            if (timelinePosition == actionTimeline.size()) return;

            //
            actionTimeline.get(timelinePosition).redo();
            timelinePosition++;
        }

        if (activeAction != null) {
            activeAction.onKeyPressed(event);
            return;
        }
    }

    private void onKeyReleased(KeyEvent event) {
        if (activeAction != null) {
            activeAction.onKeyReleased(event);
            return;
        }
    }

    private void onKeyTyped(KeyEvent event) {
        if (activeAction != null) {
            activeAction.onKeyReleased(event);
            return;
        }
    }

    private void onScroll(ScrollEvent event) {
        if (activeAction != null) {
            activeAction.onScroll(event);
            return;
        }
    }

    /*
     *
     */
    private void activate(Action action) {
        // we are starting a new action somewhere in the timeline's history, we need to clear all action after where
        // we currently are in the timeline
        if (timelinePosition != actionTimeline.size()) {
            for (int i = actionTimeline.size(); i > timelinePosition; --i) {
                actionTimeline.remove(i - 1);
            }
        }

        activeAction = action;
        activeAction.onActivate();
    }

    /*
     * updates the mouses real position and position within the view
     */
    private void updateMouse(int x, int y) {
        mouse.setRealPosition(x, y);
        mouse.setViewPosition(
                (int)(camera.getTranslateX() + (x / camera.getZoom())),
                (int)(camera.getTranslateY() + (y / camera.getZoom()))
        );
    }

    /*
     * checks to see if the mouse's new position interacts with any on screen elements
     */
    private void updateHover() {
        Element nh = collider.check(mouse.getViewX(), mouse.getViewY());
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
    private void registerCanvasEvents(Canvas canvas) {
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Element getHover() {
        return hover;
    }

    public Program getProgram() {
        return program;
    }

    public Mouse getMouse() {
        return mouse;
    }
}
