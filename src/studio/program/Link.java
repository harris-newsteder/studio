package studio.program;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.shape.Net;

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
    private Pin source = null;

    /*
     * however, that source may be routed to multiple signal "sinks"
     */
    private ArrayList<Pin> sinks = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Link() {
        super(EID);

        sinks = new ArrayList<>();
        body = new Net();
    }

    @Override
    public void tick(double dt) {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        if (hover) gc.setStroke(Color.RED);
        body.stroke(gc);
        gc.restore();
    }

    public void setSource(Pin source) {
        this.source = source;
    }

    public Pin getSource() {
        return source;
    }

    public ArrayList<Pin> getSinks() {
        return sinks;
    }

    public void addSink(Pin sink) {
        if (sinks.contains(sink)) {
            LOGGER.warn("attempting to add sink pin to link that is already added");
            return;
        }
        sinks.add(sink);
    }
}
