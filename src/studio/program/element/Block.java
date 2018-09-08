package studio.program.element;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import studio.App;
import studio.program.Program;

import java.util.ArrayList;

/*
 * a block is a rectangular element that has pins
 */
public abstract class Block extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ID = "block";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    protected ArrayList<Pin> pins = null;

    /*
     *
     */
    protected double width = 80;

    /*
     *
     */
    protected double height = 80;

    /*
     *
     */
    protected String text = "";

    /*
     * a unique name that identifies each block definition
     */
    protected String name = "";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Block() {
        super();
        id = ID;
        pins = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        gc.fillRect(-width / 2, -height / 2, width, height);
        gc.strokeRect(-width / 2, -height / 2, width, height);
        if (hover) {
            gc.setFill(App.COLOR_HOVER_MASK);
            gc.fillRect(-width / 2, -height / 2, width, height);
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(App.COLOR_DARK);
        gc.fillText(text, 0, 0);
        gc.restore();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        if (x >= this.x - (width  / 2) &&
            x <= this.x + (width  / 2) &&
            y >= this.y - (height / 2) &&
            y <= this.y + (height / 2)) {
            return true;
        }

        return false;
    }

    @Override
    public void kill() {
        super.kill();

        // kill all pins attached to this block, we won't be needing them anymore
        for (Pin pin : pins) {
            pin.kill();
        }
    }

    @Override
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;

        // when the block is moved all the pins "attached" to it need to update their own positions relative to their
        // parent (i.e. this block)
        for (Pin pin : pins) {
            pin.updatePosition();
        }
    }

    public void addPin(Pin pin) {
        pins.add(pin);
    }

    public ArrayList<Pin> getPins() {
        return pins;
    }

    public String getName() {
        return name;
    }

    /*
     *
     */
    public abstract void createPins(Program program);
}
