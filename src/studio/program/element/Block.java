package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import studio.program.dictionary.BlockDefinition;
import studio.program.dictionary.PinDefinition;
import studio.program.ui.shape.Rectangle;
import studio.program.Program;
import studio.program.Var;
import studio.program.ui.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * a block is an element that performs a function on its pins
 */
public class Block extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String EID = "block";

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
    protected HashMap<String, Var> vars = null;

    /*
     *
     */
    protected String text = "";

    /*
     * a unique name that identifies what type of block this is
     */
    protected String name = "";

    private BlockDefinition def;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Block(BlockDefinition definition) {
        super(EID);
        pins = new ArrayList<>();
        vars = new HashMap<>();

        // TODO: dont default shape to rectangle (ports use circles)
        shape = new Rectangle();

        def = definition;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();

        shape.fill(gc);
        shape.stroke(gc);

        gc.setFill(View.COLOR_DARK);
        gc.fillText(text, shape.x, shape.y);

        gc.save();
        gc.translate(shape.x, shape.y);
        gc.beginPath();
        gc.appendSVGPath(def.symbol);
        gc.stroke();
        gc.closePath();
        gc.restore();

        if (hover) {
            gc.save();
            gc.setFill(View.COLOR_HOVER_MASK);
            shape.fill(gc);
            gc.restore();
        }

        gc.restore();
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
    public void tick(double dt) {

    }

    /*
     *
     */
    public void createPins(Program program) {
        for (PinDefinition pd : def.pins) {
            Pin p = new Pin(pd, this);
        }
    }

    public void addPin(Pin pin) {
        pins.add(pin);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Pin> getPins() {
        return pins;
    }

    public HashMap<String, Var> getVars() {
        return vars;
    }

    public String getName() {
        return name;
    }
}
