package studio.program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.App;
import studio.program.element.Element;
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

    public Program() {
        elements = new ArrayList<>();
        camera = new Camera();
        cursor = new Cursor();
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

        canvas.setOnMouseMoved(          event -> {onMouseMoved(event);});
        canvas.setOnMousePressed(        event -> {onMousePressed(event);});
        canvas.setOnMouseReleased(       event -> {onMouseReleased(event);});
        canvas.setOnMouseDragged(        event -> {onMouseDragged(event);});
        canvas.setOnScroll(              event -> {onScroll(event);});
        canvas.setOnContextMenuRequested(event -> {onContextMenuRequested(event);});
    }

    public void tick(double dt) {
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

        gc.setFill(App.COLOR_WHITE);
        gc.setStroke(App.COLOR_DARK);
        gc.setLineWidth(2.0);

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

    private void onMouseMoved(MouseEvent event) {
        updateCursorPosition(event.getX(), event.getY());
        interactionManager.onMouseMoved(event);
    }

    private void onMousePressed(MouseEvent event) {
        interactionManager.onMousePressed(event);
    }

    private void onMouseReleased(MouseEvent event) {
        interactionManager.onMouseReleased(event);
    }

    private void onMouseDragged(MouseEvent event) {
        updateCursorPosition(event.getX(), event.getY());
        interactionManager.onMouseDragged(event);
    }

    private void onScroll(ScrollEvent event) {
        interactionManager.onScroll(event);
    }

    private void onContextMenuRequested(ContextMenuEvent event) {
        interactionManager.onContextMenuRequested(event);
    }

    private void updateCursorPosition(double realX, double realY) {
        cursor.setRealX(realX);
        cursor.setRealY(realY);
        cursor.setGraphX(camera.getTranslateX() + (realX / camera.getZoom()));
        cursor.setGraphY(camera.getTranslateY() + (realY / camera.getZoom()));
    }
}
