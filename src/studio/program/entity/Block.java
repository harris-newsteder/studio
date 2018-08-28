package studio.program.entity;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;

import java.util.ArrayList;

public class Block extends Entity {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ID = "block";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * the x coordinate of the center point of the block
     */
    public double x = 0.0;

    /*
     * the y coordinate of the center point of the block
     */
    public double y = 0.0;

    /*
     * the block's width
     */
    public double w = 80.0;

    /*
     * the block's height
     */
    public double h = 80.0;

    /*
     *
     */
    public String text = "BLOCK";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private ArrayList<Pin> pins = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Block(Program program) {
        super(program);
        id = ID;

        pins = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.save();
        gc.translate(x, y);
        gc.fillRect(-w / 2, -h / 2, w, h);
        gc.strokeRect(-w / 2, -h / 2, w, h);
        gc.setFill(Program.COLOR_DARK);
        gc.fillText(text, 0, 0);

        if (hover) {
            gc.save();
            gc.setFill(Program.COLOR_HOVER);
            gc.fillRect(-w / 2, -h / 2, w, h);
            gc.restore();
        }
        gc.restore();
    }

    public void addPin(Pin pin) {
        // add pin to this block
        pins.add(pin);
        program.addEntity(pin);

        // count the number of pins on the side of the pin being added
        int n = 0;
        for (Pin p : pins) {
            if (p.side == pin.side){
                n++;
            }
        }

        //
        double delta = 0;
        double offset = 0;

        switch (pin.side) {
            case Pin.SIDE_TOP:
            case Pin.SIDE_BOTTOM:
                delta = w / (double)n;
                offset = -(w / 2) + (delta / 2);
                break;
            case Pin.SIDE_RIGHT:
            case Pin.SIDE_LEFT:
                delta = h / (double)n;
                offset = -(h / 2) + (delta / 2);
                break;
        }

        //
        for (Pin p : pins) {
            if (p.side == pin.side){
                p.offset = offset;
                offset += delta;
            }
        }
    }

    public ArrayList<Pin> getPins() {
        return pins;
    }

    @Override
    public void kill() {
        super.kill();

        for (Pin pin : pins) {
            pin.kill();
        }
    }
}
