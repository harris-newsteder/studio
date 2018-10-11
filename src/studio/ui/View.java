package studio.ui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import studio.program.Program;
import studio.program.Element;

/*
 * this class draws a program with a given graphics context
 */
public final class View {
    public static final Color COLOR_DARK = Color.rgb(40, 40, 40);
    public static final Color COLOR_LIGHT = Color.rgb(245, 245, 245);
    public static final Color COLOR_WHITE = Color.rgb(255, 255, 255);
    public static final Color COLOR_BLACK = Color.rgb(0, 0, 0);
    public static final Color COLOR_HOVER_MASK = Color.rgb(255, 0, 0, 0.1);
    public static final Color COLOR_FOCUS_MASK = Color.rgb(255, 255, 255, 0.4);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final double GRID_SIZE = 20;

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
    private Program program;

    /*
     *
     */
    public InteractionManager interactionManager = null;

    /*
     *
     */
    private Camera camera = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public View(UI ui) {
        this.ui = ui;

        program = ui.getProgram();
        interactionManager = ui.getInteractionManager();
        camera = new Camera();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void draw(GraphicsContext gc) {
        double cw = gc.getCanvas().getWidth();
        double ch = gc.getCanvas().getHeight();

        gc.save();
        gc.clearRect(0, 0, cw, ch);
        gc.setFill(COLOR_LIGHT);
        gc.fillRect(0, 0, cw, ch);

        // camera transformation

        camera.calculateTranslation(cw, ch);

        gc.scale(
                camera.getZoom(),
                camera.getZoom()
        );

        gc.translate(
                -camera.getTranslateX(),
                -camera.getTranslateY()
        );

        drawGrid(gc);

        // draw all elements

        gc.setStroke(COLOR_DARK);
        gc.setFill(COLOR_WHITE);
        gc.setLineWidth(2.0);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        for (Element e : program.getElements()) {
            e.draw(gc);
        }

        interactionManager.draw(gc);

        gc.restore();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void drawGrid(GraphicsContext gc) {
        double cw = gc.getCanvas().getWidth();
        double ch = gc.getCanvas().getHeight();

        // don't bother drawing a bunch of tiny points if the camera is zoomed out too far
        if (camera.getZoom() < 0.4) return;

        // start position of the first point (top left)
        double sx = Math.ceil(camera.getTranslateX() / GRID_SIZE) * GRID_SIZE;
        double sy = Math.ceil(camera.getTranslateY() / GRID_SIZE) * GRID_SIZE;

        // how many points to draw in the x and y directions
        int nx = (int)Math.ceil((cw / camera.getZoom()) / GRID_SIZE);
        int ny = (int)Math.ceil((ch / camera.getZoom()) / GRID_SIZE);

        // draw all points
        // TODO: more efficient way of doing this?
        gc.save();
        gc.setFill(COLOR_DARK);
        for (int y = 0; y < ny; ++y) {
            for (int x = 0; x < nx; ++x) {
                gc.fillRect((sx + (x * GRID_SIZE)) - 0.5, (sy + (y * GRID_SIZE)) - 0.5, 1, 1);
            }
        }
        gc.restore();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Camera getCamera() {
        return camera;
    }
}
