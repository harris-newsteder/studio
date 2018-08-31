package studio.program.element;

import studio.program.shape.Circle;

public class Pin extends Element {
    public static final String ID = "pin";

    private Block parent = null;
    private boolean linked = false;

    public Pin() {
        super();
        id = ID;
        shape = new Circle();
    }
}
