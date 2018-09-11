package studio.program.interaction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.Program;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.LinkSection;
import studio.program.element.Pin;

import java.util.ArrayList;

public class TLink extends Tool {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private final Logger logger = LoggerFactory.getLogger(TLink.class);
    private Pin start = null;
    private ArrayList<LinkSection> sections = null;

    /*
     * the horizontal link section that is currently being placed by the user
     */
    private LinkSection csHorizontal = null;

    /*
     * the vertical link section that is currently being placed by the user
     */
    private LinkSection csVertical = null;

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
        for (LinkSection ls : sections) {
            gc.strokeLine(ls.getStartX(), ls.getStartY(), ls.getEndX(), ls.getEndY());
        }
        gc.strokeLine(csVertical.getStartX(),
                      csVertical.getStartY(),
                      csVertical.getEndX(),
                      csVertical.getEndY()
        );
        gc.strokeLine(csHorizontal.getStartX(),
                      csHorizontal.getStartY(),
                      csHorizontal.getEndX(),
                      csHorizontal.getEndY()
        );
        gc.restore();
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        if (!active) return;

        double gridSnapX = Math.round(cursor.getGraphX() / Program.GRID_SIZE) * Program.GRID_SIZE;
        double gridSnapY = Math.round(cursor.getGraphY() / Program.GRID_SIZE) * Program.GRID_SIZE;

        double dx = gridSnapX - csHorizontal.getStartX();
        double dy = gridSnapY - csHorizontal.getStartY();

        csHorizontal.setEndPosition(gridSnapX, csHorizontal.getStartY());
        csVertical.setEndPosition(csHorizontal.getEndX(), gridSnapY);
        csVertical.setStartPosition(csHorizontal.getEndX(), csHorizontal.getEndY());
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
        if (hover.getEID() != Pin.EID) {
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

        //
        csHorizontal = new LinkSection();
        csHorizontal.setStartPosition(start.getX(), start.getY());
        csHorizontal.setEndPosition(start.getX(), start.getY());
        csHorizontal.setOrientation(LinkSection.Orientation.HORIZONTAL);

        //
        csVertical = new LinkSection();
        csVertical.setStartPosition(start.getX(), start.getY());
        csVertical.setEndPosition(start.getX(), start.getY());
        csVertical.setOrientation(LinkSection.Orientation.VERTICAL);

        sections = new ArrayList<>();
    }

    private void attemptNextSection() {
        if (csVertical.getLength() > 0)
            sections.add(csVertical);

        if (csHorizontal.getLength() > 0)
            sections.add(csHorizontal);

        Element end = manager.getHover();

        double ex = csVertical.getEndX();
        double ey = csVertical.getEndY();
        double gridSnapX = Math.round(cursor.getGraphX() / Program.GRID_SIZE) * Program.GRID_SIZE;
        double gridSnapY = Math.round(cursor.getGraphY() / Program.GRID_SIZE) * Program.GRID_SIZE;

        if (end != null && ex == gridSnapX && ey == gridSnapY) {
            attempLink(end);
            active = false;
        } else {
            //
            csHorizontal = new LinkSection();
            csHorizontal.setStartPosition(ex, ey);
            csHorizontal.setEndPosition(ex, ey);
            csHorizontal.setOrientation(LinkSection.Orientation.HORIZONTAL);

            //
            csVertical = new LinkSection();
            csVertical.setStartPosition(ex, ey);
            csVertical.setEndPosition(ex, ey);
            csVertical.setOrientation(LinkSection.Orientation.VERTICAL);
        }
    }

    private void attempLink(Element end) {
        start.setLinked(false);

        // TODO: add link to link
        if (!(end.getEID() == Pin.EID)) {
            return;
        }

        Pin endPin = (Pin)end;

        if (endPin == start) {
            logger.info("attempting to link pin to itself");
            return;
        }

        if (start.getFlow() == endPin.getFlow()) {
            logger.info("both pins have the same flow (input / output)");
            return;
        }

        // TODO: data type checking

        for (LinkSection ls : sections) {
            ls.reorder();
        }

        Link link = new Link();

        link.setSectionList(sections);

        if (start.getFlow() == Pin.Flow.OUTPUT) {
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
