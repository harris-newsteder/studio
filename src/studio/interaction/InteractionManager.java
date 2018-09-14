package studio.interaction;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.gen.Generator;
import studio.program.Program;
import studio.program.element.Element;
import studio.view.View;

import java.util.EnumMap;

public class InteractionManager {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum InteractionType {
        NONE,
        CAMERA
    };

    public enum ToolType {
        GROUP,
        MOVE,
        LINK
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private final Logger LOGGER = LoggerFactory.getLogger(InteractionManager.class);

    /*
     *
     */
    private Program program = null;

    /*
     *
     */
    private View view = null;

    /*
     *
     */
    private Cursor cursor = null;

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
    private InteractionType currentInteraction = InteractionType.NONE;

    /*
     *
     */
    private EnumMap<InteractionType, Interaction> interactions = null;

    /*
     *
     */
    private ToolType currentTool = ToolType.MOVE;

    /*
     *
     */
    private EnumMap<ToolType, Tool> tools = null;

    /*
     *
     */
    private Generator generator = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InteractionManager(Program program, View view, Canvas canvas) {
        this.program = program;
        this.view = view;
        this.cursor = new Cursor();
        this.collider = new Collider(cursor, program.getElements());

        generator = new Generator();

        interactions = new EnumMap<InteractionType, Interaction>(InteractionType.class);
        interactions.put(InteractionType.NONE,       new INone(this, cursor));
        interactions.put(InteractionType.CAMERA,     new ICamera(this, cursor));

        tools = new EnumMap<ToolType, Tool>(ToolType.class);
        tools.put(ToolType.GROUP, new TGroup(this));
        tools.put(ToolType.LINK, new TLink(this));
        tools.put(ToolType.MOVE, new TMove(this));

        canvas.setFocusTraversable(true);
        canvas.setOnMouseMoved(          event -> {onMouseMoved(event);});
        canvas.setOnMousePressed(        event -> {onMousePressed(event);});
        canvas.setOnMouseReleased(       event -> {onMouseReleased(event);});
        canvas.setOnMouseDragged(        event -> {onMouseDragged(event);});
        canvas.setOnScroll(              event -> {onScroll(event);});
        canvas.setOnContextMenuRequested(event -> {onContextMenuRequested(event);});
        canvas.setOnKeyPressed(          event -> {onKeyPressed(event);});
        canvas.setOnMouseClicked(        event -> {onMouseClicked(event);});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void draw(GraphicsContext gc) {
        tools.get(currentTool).draw(gc);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // EVENT CALLBACKS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void onMouseMoved(MouseEvent event) {
        updateCursorPosition(event.getX(), event.getY());

        Element nh = collider.checkElements();

        if (nh != hover) {
            // a change has occured
            if (nh != null) nh.onEnter();
            if (hover != null) hover.onExit();
            hover = nh;
        }

        tools.get(currentTool).onMouseMoved(event);
    }

    private void onMousePressed(MouseEvent event) {
        switch (event.getButton()) {
            case MIDDLE:
                currentInteraction = InteractionType.CAMERA;
                break;
            default:
                break;
        }

        tools.get(currentTool).onMousePressed(event);
        interactions.get(currentInteraction).onMousePressed(event);
    }

    private void onMouseReleased(MouseEvent event) {
        interactions.get(currentInteraction).onMouseReleased(event);
        tools.get(currentTool).onMouseReleased(event);
        currentInteraction = InteractionType.NONE;
    }

    private void onMouseClicked(MouseEvent event) {
        tools.get(currentTool).onMouseClicked(event);
    }

    private void onMouseDragged(MouseEvent event) {
        updateCursorPosition(event.getX(), event.getY());
        interactions.get(currentInteraction).onMouseDragged(event);
        tools.get(currentTool).onMouseDragged(event);
    }

    private void onScroll(ScrollEvent event) {
        // TODO: hmmm
        interactions.get(InteractionType.CAMERA).onScroll(event);
    }

    private void onContextMenuRequested(ContextMenuEvent event) {

    }

    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case L:
                currentTool = ToolType.LINK;
                LOGGER.info("switching to tool: LINK");
                break;
            case M:
                currentTool = ToolType.MOVE;
                LOGGER.info("switching to tool: MOVE");
                break;
            case ENTER:
                try {
                    generator.generate(program);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        tools.get(currentTool).onKeyPressed(event);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void updateCursorPosition(double rx, double ry) {
        cursor.setRealX(rx);
        cursor.setRealY(ry);
        cursor.setViewX(view.getCamera().getTranslateX() + (rx / view.getCamera().getZoom()));
        cursor.setViewY(view.getCamera().getTranslateY() + (ry / view.getCamera().getZoom()));
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

    public View getView() {
        return view;
    }

    public Cursor getCursor() {
        return cursor;
    }


}
