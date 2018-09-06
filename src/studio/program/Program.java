package studio.program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.App;
import studio.program.element.Element;
import studio.program.element.LinkCandidate;
import studio.program.element.Pin;
import studio.program.element.Sum;
import studio.program.interaction.InteractionManager;

import java.util.ArrayList;
import java.util.Iterator;

public class Program {
    public static final double GRID_SIZE = 20.0;

    private ArrayList<Element> elements = null;
    private Camera camera = null;
    private Cursor cursor = null;
    private InteractionManager interactionManager = null;
    private Canvas canvas = null;
    private GraphicsContext gc = null;
    private LinkCandidate linkCandidate = null;

    public Program() {
        elements = new ArrayList<>();
        camera = new Camera();
        cursor = new Cursor();
        linkCandidate = new LinkCandidate();

        interactionManager = new InteractionManager(this);


        // TODO: remove
        Sum s = new Sum();
        addElement(s);
        s.createPins(this);

        s = new Sum();
        addElement(s);
        s.createPins(this);
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);

        canvas.setOnMouseMoved(          event -> {updateCursorPosition(event.getX(), event.getY());
                                                   interactionManager.onMouseMoved(event);});
        canvas.setOnMousePressed(        event -> {interactionManager.onMousePressed(event);});
        canvas.setOnMouseReleased(       event -> {interactionManager.onMouseReleased(event);});
        canvas.setOnMouseDragged(        event -> {updateCursorPosition(event.getX(), event.getY());
                                                   interactionManager.onMouseDragged(event);});
        canvas.setOnScroll(              event -> {interactionManager.onScroll(event);});
        canvas.setOnContextMenuRequested(event -> {interactionManager.onContextMenuRequested(event);});
        canvas.setOnKeyPressed(          event -> {interactionManager.onKeyPressed(event);});
        canvas.setOnMouseClicked(        event -> {interactionManager.onMouseClicked(event);});
    }

    public void tick(double dt) {
        linkCandidate.tick(dt);

        Iterator i = elements.iterator();

        while (i.hasNext()) {
            Element e = (Element)i.next();
            e.tick(dt);

            // remove all dead elements from the list
            if (!e.isAlive()) {
                // TODO: make sure this doesn't happen while a javafx thread update is happening, it will null pointer
                // something that is trying to draw to the screen
                i.remove();
            }
        }
    }

    public void draw() {
        if (canvas == null) return;

        gc.save();
        gc.setFill(App.COLOR_LIGHT);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        camera.calculateTranslation(canvas.getWidth(), canvas.getHeight());

        // camera transformation
        gc.scale(
                camera.getZoom(),
                camera.getZoom()
        );

        gc.translate(
                -camera.getTranslateX(),
                -camera.getTranslateY()
        );

        drawGrid();

        gc.setFill(App.COLOR_WHITE);
        gc.setStroke(App.COLOR_DARK);
        gc.setLineWidth(2.0);

        interactionManager.draw(gc);

        for (Element e : elements) {
            e.draw(gc);
        }

        gc.restore();
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public Cursor getCursor() {
        return cursor;
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public LinkCandidate getLinkCandidate() {
        return linkCandidate;
    }

    private void updateCursorPosition(double realX, double realY) {
        cursor.setRealX(realX);
        cursor.setRealY(realY);
        cursor.setGraphX(camera.getTranslateX() + (realX / camera.getZoom()));
        cursor.setGraphY(camera.getTranslateY() + (realY / camera.getZoom()));
    }

    private void drawGrid() {
        // don't bother drawing a bunch of tiny points if the camera is zoomed out too far
        if (camera.getZoom() < 0.4) return;

        // start position of the first point
        double sx = Math.ceil(camera.getTranslateX() / GRID_SIZE) * GRID_SIZE;
        double sy = Math.ceil(camera.getTranslateY() / GRID_SIZE) * GRID_SIZE;

        // how many points to draw in the x and y directions
        int nx = (int)Math.ceil((canvas.getWidth() / camera.getZoom()) / GRID_SIZE);
        int ny = (int)Math.ceil((canvas.getWidth() / camera.getZoom()) / GRID_SIZE);

        // draw all points
        gc.save();
        gc.setFill(App.COLOR_DARK);
        for (int y = 0; y < ny; ++y) {
            for (int x = 0; x < nx; ++x) {
                gc.fillRect(sx + (x * GRID_SIZE), sy + (y * GRID_SIZE), 1, 1);
            }
        }
        gc.restore();
    }
}
