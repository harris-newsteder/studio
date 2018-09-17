package studio.program.element;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.interaction.shape.Polyline;

import java.util.ArrayList;

public class Link extends Element {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String EID = "link";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final Logger LOGGER = LoggerFactory.getLogger(Link.class);

    /*
     *
     */
    private Pin source = null;

    /*
     *
     */
    private ArrayList<Pin> sinks = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Link() {
        super(EID);

        sinks = new ArrayList<>();
        shape = new Polyline();
    }

    @Override
    public void tick(double dt) {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        if (hover) gc.setStroke(Color.RED);
        shape.stroke(gc);
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
