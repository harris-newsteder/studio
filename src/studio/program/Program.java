package studio.program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import studio.App;
import studio.gen.Generator;
import studio.interaction.Cursor;
import studio.program.element.*;
import studio.interaction.InteractionManager;
import studio.view.Camera;

import java.util.ArrayList;
import java.util.Iterator;

public class Program {
    private ArrayList<Element> elements = null;

    public Program() {
        elements = new ArrayList<>();

        // TODO: remove
        Block b = new BDiscreteOutput();
        addElement(b);
        b.createPins(this);

        b = new BTimer();
        addElement(b);
        b.createPins(this);

        b = new BAnd();
        addElement(b);
        b.createPins(this);

        b = new BDiscreteInput();
        addElement(b);
        b.createPins(this);

        b = new BNot();
        addElement(b);
        b.createPins(this);
    }

    public void setCanvas(Canvas canvas) {
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

    public void addElement(Element element) {
        elements.add(element);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }
}
