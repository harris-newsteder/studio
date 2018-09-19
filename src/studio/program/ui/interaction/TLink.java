package studio.program.ui.interaction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.dictionary.PinDefinition;
import studio.program.ui.shape.LineSection;
import studio.program.ui.shape.Polyline;
import studio.program.ui.shape.Shape;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.Pin;
import studio.program.ui.view.View;

import java.util.ArrayList;

public class TLink extends Tool {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private final Logger logger = LoggerFactory.getLogger(TLink.class);
    private Pin start = null;
    private ArrayList<LineSection> sections = null;

    /*
     * the horizontal link section that is currently being placed by the user
     */
    private LineSection csHorizontal = null;

    /*
     * the vertical link section that is currently being placed by the user
     */
    private LineSection csVertical = null;

    /*
     * this one is a bit hard to explain TODO: explain this
     */
    private boolean horizontalBias = true;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public TLink(InteractionManager manager) {
        super(manager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void draw(GraphicsContext gc) {
        if (!active) return;

        gc.save();
        gc.setStroke(View.COLOR_DARK);
        gc.setLineWidth(2.0);
        for (LineSection ls : sections) {
            gc.strokeLine(ls.startX, ls.startY, ls.endX, ls.endY);
        }
        gc.strokeLine(csVertical.startX,
                      csVertical.startY,
                      csVertical.endX,
                      csVertical.endY
        );
        gc.strokeLine(csHorizontal.startX,
                csHorizontal.startY,
                csHorizontal.endX,
                csHorizontal.endY
        );
        gc.restore();
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        if (!active) return;

        double gridSnapX = Math.round(cursor.getViewX() / View.GRID_SIZE) * View.GRID_SIZE;
        double gridSnapY = Math.round(cursor.getViewY() / View.GRID_SIZE) * View.GRID_SIZE;

        double dx = gridSnapX - csHorizontal.startX;
        double dy = gridSnapY - csHorizontal.startY;

        csHorizontal.endX = gridSnapX;
        csHorizontal.endY = csHorizontal.startY;

        csVertical.endX = csHorizontal.endX;
        csVertical.endY = gridSnapY;

        csVertical.startX = csHorizontal.endX;
        csVertical.startY = csHorizontal.endY;
    }

    @Override
    public void onMousePressed(MouseEvent event) {

    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void onMouseDragged(MouseEvent event) {

    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        //
        if (event.getButton() != MouseButton.PRIMARY) return;

        if (!active)
            attemptStart();
        else
            attemptNextSection();
    }

    @Override
    public void onScroll(ScrollEvent event) {

    }

    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && active) {
            active = false;
            start.setLinked(false);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void attemptStart() {
        Element hover = manager.getHover();

        if (hover == null) return;

        //
        if (hover.eid != Pin.EID) {
            logger.info("new links must start at a pin");
            return;
        }

        //
        if (((Pin)hover).isLinked()) {
            logger.info("attempting to link pin that is already linked");
            return;
        }

        active = true;

        start = (Pin)manager.getHover();
        start.setLinked(true);

        Shape ss = start.shape;

        //
        csHorizontal = new LineSection(LineSection.Orientation.HORIZONTAL);
        csHorizontal.startX = ss.x;
        csHorizontal.startY = ss.y;
        csHorizontal.endX = ss.x;
        csHorizontal.endY = ss.y;

        //
        csVertical = new LineSection(LineSection.Orientation.VERTICAL);
        csVertical.startX = ss.x;
        csVertical.startY = ss.y;
        csVertical.endX = ss.x;
        csVertical.endY = ss.y;

        sections = new ArrayList<>();
    }

    private void attemptNextSection() {
        if (csVertical.getLength() > 0)
            sections.add(csVertical);

        if (csHorizontal.getLength() > 0)
            sections.add(csHorizontal);

        Element end = manager.getHover();

        double ex = csVertical.endX;
        double ey = csVertical.endY;
        double gridSnapX = Math.round(cursor.getViewX() / View.GRID_SIZE) * View.GRID_SIZE;
        double gridSnapY = Math.round(cursor.getViewY() / View.GRID_SIZE) * View.GRID_SIZE;

        if (end != null && ex == gridSnapX && ey == gridSnapY) {
            attempLink(end);
            active = false;
        } else {
            //
            csHorizontal = new LineSection(LineSection.Orientation.HORIZONTAL);
            csHorizontal.startX = ex;
            csHorizontal.startY = ey;
            csHorizontal.endX = ex;
            csHorizontal.endY = ey;

            //
            csVertical = new LineSection(LineSection.Orientation.VERTICAL);
            csVertical.startX = ex;
            csVertical.startY = ey;
            csVertical.endX = ex;
            csVertical.endY = ey;
        }
    }

    private void attempLink(Element end) {
        start.setLinked(false);

        // TODO: add link to link
        if (!(end.eid == Pin.EID)) {
            return;
        }

        Pin endPin = (Pin)end;

        if (endPin == start) {
            logger.info("attempting to link pin to itself");
            return;
        }

        if (start.flow == endPin.flow) {
            logger.info("both pins have the same flow (input / output)");
            return;
        }

        // TODO: data type checking

        for (LineSection ls : sections) {
            ls.reorder();
        }

        Link link = new Link();
        ((Polyline)link.shape).sections = sections;

        if (start.flow == PinDefinition.Flow.OUTPUT) {
            link.setSource(start);
            link.addSink(endPin);
        } else {
            link.setSource(endPin);
            link.addSink(start);
        }

        start.link(link);
        endPin.link(link);

        manager.getProgram().addElement(link);
    }
}
