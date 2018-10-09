package studio.program;

import javafx.scene.canvas.GraphicsContext;
import studio.shape.Shape;
import studio.ui.View;

import java.util.ArrayList;
import java.util.HashMap;

public class Block extends Element {
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
    private ArrayList<Pin> pins = null;

    /*
     * all of the internal block variables
     */
    private HashMap<String, Variable> vars = null;

    /*
     * a unique name that identifies each type of block
     */
    private final String name;

    /*
     * an SVGPath of this block's symbol
     */
    private String symbol;

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

        gc.translate(body.getX(), body.getY());

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

    protected void addPin(Pin pin) {
        pins.add(pin.getIndex(), pin);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setBody(Shape body) {
        this.body = body;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ArrayList<Pin> getPins() {
        return pins;
    }

    public HashMap<String, Variable> getVars() {
        return vars;
    }

    public String getName() {
        return name;
    }
}
