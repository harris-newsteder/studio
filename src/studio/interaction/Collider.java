package studio.interaction;

import studio.interaction.shape.Shape;
import studio.program.element.Element;

import java.util.ArrayList;

public class Collider {

    private Cursor cursor = null;
    private ArrayList<Element> elements = null;

    public Collider(Cursor cursor, ArrayList<Element> elements) {
        this.cursor = cursor;
        this.elements = elements;
    }

    public Element checkElements() {
        Element ret = null;
        Shape s = null;

        for (Element e : elements) {
            s = e.getShape();
            if (s.containsPoint(cursor.getViewX(), cursor.getViewY())) {
                ret = e;
            }
        }

        return ret;
    }
}
