package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Link extends Element {
    public static final String EID = "link";

    public static final double INTERACTION_DISTANCE = 2;

    private final Logger LOGGER = LoggerFactory.getLogger(Link.class);

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
        if (hover) gc.setStroke(Color.CORNFLOWERBLUE);
        for (LinkSection ls : sections) {
            gc.strokeLine(ls.getStartX(), ls.getStartY(), ls.getEndX(), ls.getEndY());
        }
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

    public void setSectionList(ArrayList<LinkSection> sectionList) {
        sections = sectionList;
    }
}
