package studio.program;

import javafx.scene.canvas.GraphicsContext;
import studio.shape.Shape;
import studio.ui.View;

import java.util.ArrayList;
import java.util.HashMap;

public final class Block extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String EID = "block";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * a list of all the pins attached to this block
     */
    public ArrayList<Pin> pins = null;

    /*
     * all of the internal block variables
     */
    public HashMap<String, Variable> vars = null;

    /*
     * a unique name that identifies each type of block
     */
    public final String name;

    /*
     * an SVGPath of this block's symbol
     */
    public String symbol;

    /*
     * the block's position
     */
    public int x;
    public int y;

    /*
     * this block's body shape (usually just a rectangle or a circle)
     */
    public Shape body;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Block(String name) {
        super(EID);
        this.name = name;
        pins = new ArrayList<>();
        vars = new HashMap<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void kill() {
        super.kill();

        // kill all pins attached to this block
        for (Pin pin : pins) {
            pin.kill();
        }
    }

    @Override
    public void tick(double dt) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(x, y);

        body.fill(gc);
        body.stroke(gc);

        gc.beginPath();
        gc.appendSVGPath(symbol);
        gc.stroke();
        gc.closePath();

        if (hover) {
            gc.setFill(View.COLOR_HOVER_MASK);
            body.fill(gc);
        }

        gc.restore();
    }

    @Override
    public boolean hitTest(int x, int y) {
        return body.hitTest(x - this.x, y - this.y);
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;

        for (Pin pin : pins) {
            pin.updatePosition();
        }
    }

    public void addPin(Pin pin) {
        pins.add(pin.index, pin);
        pin.updatePosition();
    }
}
