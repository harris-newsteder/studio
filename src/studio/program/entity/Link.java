package studio.program.entity;

import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;

import java.util.ArrayList;

/*
 *
 */
public class Link extends Entity {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ID = "link";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * a link may only have a single source
     */
    private Pin source = null;

    /*
     *
     */
    private ArrayList<Pin> sinks = null;

    /*
     *
     */
    private ArrayList<LinkSection> sections = null;

    /*
     * whether or not this link already has a signal source
     */
    private boolean sourced = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Link(Program program) {
        super(program);
        id = ID;
        sinks = new ArrayList<>();
        sections = new ArrayList<>();
    }

    @Override
    public void tick(double dt) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        for (LinkSection section : sections) {
            gc.strokeLine(section.sx, section.sy, section.ex, section.ey);
        }
        gc.restore();
    }

    public ArrayList<LinkSection> getSections() {
        return sections;
    }

    public boolean isSourced() {
        return sourced;
    }

    public boolean registerPin(Pin pin) {
        if (pin.flow == Pin.FLOW_INPUT) {
            sinks.add(pin);
            return true;
        } else if (pin.flow == Pin.FLOW_OUTPUT) {
            if (sourced) {
                System.err.println("link already has a source signal");
                return false;
            }

            source = pin;
            sourced = true;

            return true;
        }

        return false;
    }
}
