package studio.program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import studio.App;
import studio.program.element.Element;
import studio.program.element.Sum;
import studio.program.interaction.InteractionManager;

import java.util.ArrayList;

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
        addElement(new Sum());
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
        for (Element e : elements) {
            e.tick(dt);
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
                -camera.getTranslate().getX(),
                -camera.getTranslate().getY()
        );

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
        cursor.setGraphX(camera.getTranslate().getX() + (realX / camera.getZoom()));
        cursor.setGraphY(camera.getTranslate().getY() + (realY / camera.getZoom()));

        cursor.getReal().setX(realX);
        cursor.getReal().setX(realY);
        cursor.getGraph().setX(camera.getTranslate().getX() + (realX / camera.getZoom()));
        cursor.getGraph().setY(camera.getTranslate().getY() + (realY / camera.getZoom()));
    }
}
