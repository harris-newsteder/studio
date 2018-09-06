package studio.program.interaction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.Cursor;
import studio.program.Program;
import studio.program.element.Element;

import java.util.EnumMap;

public class InteractionManager {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
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
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final Logger logger = LoggerFactory.getLogger(InteractionManager.class);

    /*
     *
     */
    private Program program = null;

    /*
     *
     */
    private Cursor cursor = null;

    /*
     *
     */
    private Element hover = null;

    /*
     *
     */
    private Collider collider = null;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InteractionManager(Program program) {
        this.program = program;
        this.cursor = program.getCursor();
        this.collider = new Collider(cursor, program.getElements());

        interactions = new EnumMap<InteractionType, Interaction>(InteractionType.class);
        interactions.put(InteractionType.NONE,       new INone(this, cursor));
        interactions.put(InteractionType.CAMERA,     new ICamera(this, cursor));

        tools = new EnumMap<ToolType, Tool>(ToolType.class);
        tools.put(ToolType.GROUP, new TGroup(this));
        tools.put(ToolType.LINK, new TLink(this));
        tools.put(ToolType.MOVE, new TMove(this));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void draw(GraphicsContext gc) {
        tools.get(currentTool).draw(gc);
    }

    public void onMouseMoved(MouseEvent event) {
        Element nh = collider.checkElements();

        if (nh != hover) {
            // a change has occured
            if (nh != null) nh.onEnter();
            if (hover != null) hover.onExit();
            hover = nh;
        }

        tools.get(currentTool).onMouseMoved(event);
    }

    public void onMousePressed(MouseEvent event) {
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

    public void onMouseReleased(MouseEvent event) {
        interactions.get(currentInteraction).onMouseReleased(event);
        tools.get(currentTool).onMouseReleased(event);
        currentInteraction = InteractionType.NONE;
    }

    public void onMouseClicked(MouseEvent event) {
        tools.get(currentTool).onMouseClicked(event);
    }

    public void onMouseDragged(MouseEvent event) {
        interactions.get(currentInteraction).onMouseDragged(event);
        tools.get(currentTool).onMouseDragged(event);
    }

    public void onScroll(ScrollEvent event) {
        // TODO: hmmm
        interactions.get(InteractionType.CAMERA).onScroll(event);
    }

    public void onContextMenuRequested(ContextMenuEvent event) {

    }

    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case L:
                currentTool = ToolType.LINK;
                logger.info("switching to tool: LINK");
                break;
            case M:
                currentTool = ToolType.MOVE;
                logger.info("switching to tool: MOVE");
                break;
        }

        tools.get(currentTool).onKeyPressed(event);
    }

    public Element getHover() {
        return hover;
    }

    public Program getProgram() {
        return program;
    }

    public Collider getCollider() {
        return collider;
    }
}
