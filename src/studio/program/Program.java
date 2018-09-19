package studio.program;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import studio.program.dictionary.BlockDictionary;
import studio.program.dictionary.def.AnalogInput;
import studio.program.dictionary.def.And;
import studio.program.dictionary.def.Not;
import studio.program.element.*;
import studio.program.ui.UI;

import java.util.ArrayList;
import java.util.Iterator;

public class Program {
    private ArrayList<Element> elements = null;

    private UI ui = null;

    public Program(Canvas canvas) {
        elements = new ArrayList<>();

        ui = new UI(this, canvas);

        // TODO: remove
        Block b = new Block(BlockDictionary.lookup(Not.NAME));
        addElement(b);
        b.createPins(this);
    }

    public void tick(double dt) {
        ui.tick(dt);

        Iterator i = elements.iterator();

        while (i.hasNext()) {
            Element e = (Element)i.next();
            e.tick(dt);

            // remove all dead elements from the list
            if (!e.isAlive()) {
                // TODO: make sure this doesn't happen while a javafx thread update is happening, it will nullify
                // something that is trying to draw to the screen
                i.remove();
            }
        }
    }

    public void draw(GraphicsContext gc) {
        ui.draw(gc);
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }
}
