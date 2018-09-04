package studio.program.interaction;

import studio.program.Cursor;
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

        for (Element e : elements) {
            if (e.containsPoint(cursor.getGraphX(), cursor.getGraphY())) {
                ret = e;
            }
        }

        return ret;
    }
}
