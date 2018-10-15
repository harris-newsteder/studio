package studio.program;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public final class Link extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String EID = "link";

    private final static Logger LOGGER = LoggerFactory.getLogger(Link.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * a link may only have a single signal source
     */
    public Pin source = null;

    /*
     *
     */
    public Variable.Type type = Variable.Type.UNSET;

    /*
     * however, that source may be routed to multiple signal "sinks"
     */
    public ArrayList<Pin> sinks = null;

    /*
     *
     */
    public ArrayList<LinkSection> sections = null;

    /*
     *
     */
    public LinkSection hitSection = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Link() {
        super(EID);

        sinks = new ArrayList<>();
        sections = new ArrayList<>();
    }

    @Override
    public void tick(double dt) {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        if (hover) {
            gc.setStroke(Color.RED);
        }
        for (LinkSection ls : sections) {
            ls.stroke(gc);
        }
        gc.restore();
    }

    @Override
    public boolean hitTest(int x, int y) {
        hitSection = null;
        for (LinkSection ls : sections) {
            if (ls.hitTest(x, y)) {
                hitSection = ls;
                return true;
            }
        }
        return false;
    }

    public boolean addPin(Pin pin) {
        if (type != Variable.Type.UNSET) {
            if (type != pin.variable.type) {
                LOGGER.error("ATTEMPTED TO ADD PIN WITH INCOMPATIBLE VARIABLE TYPE");
                return false;
            }
        }

        if (pin.flow == Pin.Flow.OUTPUT) {
            if (source != null) {
                LOGGER.error("ATTEMPTED TO ADD SOURCE PIN TO LINK THAT ALREADY HAS ONE");
                return false;
            } else {
                source = pin;
                type = pin.variable.type;
                return true;
            }
        } else {
            if (sinks.contains(pin)) {
                LOGGER.error("ATTEMPTED TO ADD SINK PIN TO LINK THAT ALREADY HAS IT ADDED");
                return false;
            } else {
                sinks.add(pin);
                type = pin.variable.type;
                return true;
            }
        }
    }

    public void addSection(LinkSection section) {
        sections.add(section);
    }
}
