package studio.program.entity;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;

/*
 * pins are inherently bound to their parent entity
 */
public class Pin extends Entity {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ID = "pin";

    public static final double LENGTH = 40;

    public static final int SIDE_UNASSIGNED = -1;
    public static final int SIDE_TOP = 0;
    public static final int SIDE_RIGHT = 1;
    public static final int SIDE_BOTTOM = 2;
    public static final int SIDE_LEFT = 3;

    public static final int FLOW_UNASSIGNED = -1;
    public static final int FLOW_INPUT = 0;
    public static final int FLOW_OUTPUT = 1;

    public static final int SIGNAL_TYPE_DISCRETE = 0;
    public static final int SIGNAL_TYPE_ANALOG = 1;
    public static final int SIGNAL_TYPE_NUMBER = 2;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * the x coordinate of the center point of the pin's grab point
     */
    public double x = 0.0;

    /*
     * the y coordinate of the center point of the pin's grab point
     */
    public double y = 0.0;

    /*
     * the radius of the pin's grab point
     */
    public double r = 10;

    /*
     * which side of the attached block this pin is on
     */
    public int side = SIDE_UNASSIGNED;

    /*
     * whether the pin is an input or an output
     */
    public int flow = FLOW_UNASSIGNED;

    /*
     *
     */
    public int signalType = 0;

    /*
     * TODO: explain this varible
     */
    public double offset = 0;

    /*
     * whether the pin is linked to other pins (through a link lol)
     */
    public boolean linked = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private Entity parent = null;

    /*
     *
     */
    private double value = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Pin(Program program, Entity parent, int flow, int side) {
        super(program);
        id = ID;
        this.parent = parent;
        this.flow = flow;
        this.side = side;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void tick(double dt) {
        super.tick(dt);

        switch (side) {
            case SIDE_TOP:
                x = ((Block)parent).x + offset;
                y = ((Block)parent).y - (((Block)parent).h / 2) - Pin.LENGTH;
                break;
            case SIDE_RIGHT:
                x = ((Block)parent).x + (((Block)parent).w / 2) + Pin.LENGTH;
                y = ((Block)parent).y + offset;
                break;
            case SIDE_BOTTOM:
                x = ((Block)parent).x + offset;
                y = ((Block)parent).y + (((Block)parent).h / 2) + Pin.LENGTH;
                break;
            case SIDE_LEFT:
                x = ((Block)parent).x - (((Block)parent).w / 2) - Pin.LENGTH;
                y = ((Block)parent).y + offset;
                break;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.save();
        gc.translate(x, y);



        switch (side) {
            case SIDE_TOP:
                gc.rotate(90);
                break;
            case SIDE_RIGHT:
                gc.rotate(180);
                break;
            case SIDE_BOTTOM:
                gc.rotate(-90);
                break;
            case SIDE_LEFT:
                gc.rotate(0);
                break;
        }

        gc.strokeLine(0, 0, Pin.LENGTH, 0);

        if (flow == FLOW_INPUT) {
            gc.strokeLine(Pin.LENGTH - 1, 0, Pin.LENGTH - 7, 5);
            gc.strokeLine(Pin.LENGTH - 1, 0, Pin.LENGTH - 7, -5);
        }

        if (!linked) {
            gc.fillOval(-r / 2, -r / 2, r, r);
            gc.strokeOval(-r / 2, -r / 2, r, r);
        }

        if (hover) {
            gc.setFill(Program.COLOR_HOVER);
            gc.fillOval(-r / 2, -r / 2, r, r);
            gc.setStroke(Program.COLOR_DARK);
            gc.strokeOval(-(r + 15) / 2, -(r + 15) / 2, (r + 15), (r + 15));
        }

        gc.restore();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
