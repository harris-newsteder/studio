package studio.program;

import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import studio.program.entity.Entity;
import studio.program.interaction.InteractionManager;

import java.util.ArrayList;

public class Program {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final double GRID_SIZE = 20;

    public static final Color COLOR_BLACK = Color.rgb(0, 0, 0);
    public static final Color COLOR_WHITE = Color.rgb(255, 255, 255);
    public static final Color COLOR_DARK  = Color.rgb(40, 40, 40);
    public static final Color COLOR_LIGHT = Color.rgb(245, 245, 245);
    public static final Color COLOR_HOVER = Color.rgb(255, 0, 0, 0.1);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private ArrayList<Entity> entities = null;

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
    private GraphicsContext gc = null;

    /*
     *
     */
    private InteractionManager interactionManager = null;

    /*
     *
     */
    private LinkCandidate candidate = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Program(Canvas canvas) {
        entities = new ArrayList<>();
        this.canvas = canvas;
        this.canvas.setCursor(Cursor.CROSSHAIR);
        this.camera = new Camera();
        this.gc = canvas.getGraphicsContext2D();
        interactionManager = new InteractionManager(this);
        candidate = new LinkCandidate();

        canvas.setOnMouseMoved(event -> { onMouseMoved(event); });
        canvas.setOnMousePressed(event -> { onMousePressed(event); });
        canvas.setOnMouseReleased(event -> { onMouseReleased(event); });
        canvas.setOnMouseDragged(event -> { onMouseDragged(event); });
        canvas.setOnScroll(event -> { onScroll(event); });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void tick(double dt) {
        for (Entity entity : entities) {
            entity.tick(dt);
        }
    }

    public void draw() {
        gc.save();

        // clear canvas
        gc.setFill(COLOR_LIGHT);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // camera transform
        camera.calculateTransform(canvas.getWidth(), canvas.getHeight());
        gc.scale(camera.zoom, camera.zoom);
        gc.translate(-camera.tx, -camera.ty);

        drawGrid();

        //
        gc.setFill(COLOR_WHITE);
        gc.setStroke(COLOR_DARK);
        gc.setLineWidth(2.0);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        for (Entity e : entities) {
            e.draw(gc);
        }

        candidate.draw(gc);

        gc.restore();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Camera getCamera() {
        return camera;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void drawGrid() {
        // don't bother drawing a bunch of tiny points if the camera is zoomed out too far
        if (camera.zoom < 0.4) return;

        // start position of the first point
        double sx = Math.ceil(camera.tx / GRID_SIZE) * GRID_SIZE;
        double sy = Math.ceil(camera.ty / GRID_SIZE) * GRID_SIZE;

        // how many points to draw in the x and y directions
        int nx = (int)Math.ceil((canvas.getWidth() / camera.zoom) / GRID_SIZE);
        int ny = (int)Math.ceil((canvas.getWidth() / camera.zoom) / GRID_SIZE);

        // draw all points
        gc.save();
        gc.setFill(COLOR_DARK);
        for (int y = 0; y < ny; ++y) {
            for (int x = 0; x < nx; ++x) {
                gc.fillRect(sx + (x * GRID_SIZE), sy + (y * GRID_SIZE), 1, 1);
            }
        }
        gc.restore();
    }

    private void onMouseMoved(MouseEvent event) {
        interactionManager.onMouseMoved(event);
    }

    private void onMousePressed(MouseEvent event) {
        interactionManager.onMousePressed(event);
    }

    private void onMouseReleased(MouseEvent event) {
        interactionManager.onMouseReleased(event);
    }

    private void onMouseDragged(MouseEvent event) {
        interactionManager.onMouseDragged(event);
    }

    private void onScroll(ScrollEvent event) {
        interactionManager.onScroll(event);
    }
}
