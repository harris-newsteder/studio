package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * TODO: remove this class entirely and implement all linking logic in the ILinker interaction
 */
public class LinkCandidate extends Element {
    public static final String ID = "link_candidate";

    private final Logger logger = LoggerFactory.getLogger(LinkCandidate.class);

    private Pin start = null;
    private Element end = null;
    private boolean active = false;
    private double endX = 0;
    private double endY = 0;

    public LinkCandidate() {
        super();
        id = ID;
    }

    @Override
    public void tick(double dt) {
        if (!active) return;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (!active) return;

        gc.strokeLine(start.getX(), start.getY(), endX, endY);
    }

    public Link createLink() {
        if (start == null || end == null) {
            logger.warn("not enough elements specified for valid link");
            return null;
        }

        switch (end.getId()) {
            case Pin.ID:
                return linkToPin();
            case Link.ID:
                return linkToLink();
            default:
                logger.warn("attemping to link from pin to invalid element (check your code)");
                return null;
        }
    }

    public void repath() {
        double dx = endX - start.getX();
        double dy = endY - start.getY();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStart(Pin start) {
        this.start = start;
    }

    public boolean isValidEnd(Element element) {
        if (element == null) return false;
        if (element == start) return false;
        if (!(element.getId() == Pin.ID || element.getId() == Link.ID)) return false;
        return true;
    }

    public void setEnd(Element end) {
        // this shouldn't be possible but still...
        if (!isValidEnd(end)) {
            logger.warn("attempted to set end link with an invalid element (check your code dude)");
            return;
        }

        this.end = end;
    }

    public void setEndPosition(double x, double y) {
        endX = x;
        endY = y;
    }

    private Link linkToPin() {
        Pin endPin = (Pin)end;

        // TODO: check for pin data type match

        // TODO: handle pins that are already connected? linked?

        if (start.getFlow() == endPin.getFlow()) {
            logger.warn("source and sink pins have the same flow");
            return null;
        }

        Link link = new Link();

        if (start.getFlow() == Pin.Flow.OUTPUT) {
            link.setSource(start);
            link.addSink(endPin);
        } else {
            link.setSource(endPin);
            link.addSink(start);
        }

        start.link(link);
        endPin.link(link);

        return link;
    }

    private Link linkToLink() {
        logger.info("unimplemented");
        return null;
    }
}
