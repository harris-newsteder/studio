package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Link extends Element {
    public static final String ID = "link";

    public static final double INTERACTION_DISTANCE = 2;

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
    private ArrayList<LinkSection> sections = null;

    public Link() {
        super();
        id = Link.ID;

        sinks = new ArrayList<>();
        sections = new ArrayList<>();
    }

    @Override
    public void tick(double dt) {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        if (hover) gc.setStroke(Color.CORNFLOWERBLUE);
        for (LinkSection ls : sections) {
            gc.strokeLine(ls.getStartX(), ls.getStartY(), ls.getEndX(), ls.getEndY());
        }
        gc.restore();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        // go through every section and do a simple comparison to see if the requested point is "near" any on the of the
        // sections
        for (LinkSection ls : sections) {
            if (ls.getOrientation() == LinkSection.Orientation.HORIZONTAL) {
                if (x < ls.getStartX() || x > ls.getEndX()) continue;
                if (Math.abs(y - ls.getStartY()) < INTERACTION_DISTANCE) return true;
            } else {
                if (y < ls.getStartY() || y > ls.getEndY()) continue;
                if (Math.abs(x - ls.getStartX()) < INTERACTION_DISTANCE) return true;
            }
        }
        return false;
    }

    public void setSource(Pin source) {
        this.source = source;
    }

    public Pin getSource() {
        return source;
    }

    public void addSink(Pin sink) {
        if (sinks.contains(sink)) {
            logger.warn("attempting to add sink pin to link that is already added");
            return;
        }
        sinks.add(sink);
    }

    public void setSectionList(ArrayList<LinkSection> sectionList) {
        sections = sectionList;
    }
}
