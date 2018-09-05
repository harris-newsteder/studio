package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Link extends Element {
    public static final String ID = "link";

    private final Logger logger = LoggerFactory.getLogger(Link.class);

    /*
     *
     */
    private Pin source = null;

    /*
     *
     */
    private ArrayList<Pin> sinks = null;

    /*
     *
     */
    private boolean floating = true;

    /*
     *
     */
    private double startX = 0;
    private double startY = 0;

    /*
     *
     */
    private double endX = 0;
    private double endY = 0;

    public Link() {
        super();
        id = Link.ID;

        sinks = new ArrayList<>();
    }

    @Override
    public void tick(double dt) {
        if (floating) return;

        startX = source.getX();
        startY = source.getY();

        endX = sinks.get(0).getX();
        endY = sinks.get(0).getY();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.strokeLine(startX, startY, endX, endY);
        gc.restore();
    }

    public void setStartPosition(double x, double y) {
        startX = x;
        startY = y;
    }

    public void setEndPosition(double x, double y) {
        endX = x;
        endY = y;
    }

    public void setSource(Pin source) {
        this.source = source;
    }

    public void addSink(Pin sink) {
        if (sinks.contains(sink)) {
            logger.warn("attempting to add sink pin to link that is already added");
            return;
        }
        sinks.add(sink);
    }

    /*
     * this function only needs to be called one time for each link; the very first link is between two pins of which
     * one must be and input and one must be an output
     */
    public boolean place(Pin a, Pin b) {
        // TODO: add element recognition

        if (!floating) {
            logger.warn("pin has already been placed");
            return false;
        }

        if (a == null || b == null) {
            logger.warn("not enough pins specified for valid placement");
            return false;
        }

        if (a.getFlow() == b.getFlow()) {
            logger.warn("source and sink pins have the same flow");
            return false;
        }

        // assign the this link's source pin and sink pins accordingly
        if (a.getFlow() == Pin.Flow.OUTPUT) {
            setSource(a);
            addSink(b);
        } else {
            setSource(b);
            addSink(a);
        }

        // TODO: check for pin data type match
        // TODO: check the flow for source and sink and swap the variables if necessary

        source.link(this);
        sinks.get(0).link(this);

        floating = false;

        return true;
    }
}
