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

    private final Logger logger = LoggerFactory.getLogger(TLink.class);
    private Pin start = null;
    private ArrayList<LinkSection> sections = null;
    private LinkSection currentSection = null;

    public TLink(InteractionManager manager) {
        super(manager);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (!active) return;
        gc.save();
        for (LinkSection ls : sections) {
            gc.strokeLine(ls.getStartX(), ls.getStartY(), ls.getEndX(), ls.getEndY());
        }
        gc.strokeLine(currentSection.getStartX(), currentSection.getStartY(), currentSection.getEndX(), currentSection.getEndY());
        gc.restore();
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        if (!active) return;

        double gridSnapX = Math.round(cursor.getGraphX() / Program.GRID_SIZE) * Program.GRID_SIZE;
        double gridSnapY = Math.round(cursor.getGraphY() / Program.GRID_SIZE) * Program.GRID_SIZE;

        double dx = gridSnapX - currentSection.getStartX();
        double dy = gridSnapY - currentSection.getStartY();

        if (Math.abs(dx) < Math.abs(dy)) {
            currentSection.setOrientation(LinkSection.Orientation.VERTICAL);
            currentSection.setEndPosition(currentSection.getStartX(), gridSnapY);
        } else {
            currentSection.setOrientation(LinkSection.Orientation.HORIZONTAL);
            currentSection.setEndPosition(gridSnapX, currentSection.getStartY());
        }
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

    private void attemptStart() {
        Element hover = manager.getHover();

        if (hover == null) return;

        //
        if (hover.getId() != Pin.ID) {
            logger.info("attemping to link an element that is not a pin");
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

        currentSection = new LinkSection();
        currentSection.setStartPosition(start.getX(), start.getY());
        currentSection.setEndPosition(start.getX(), start.getY());

        sections = new ArrayList<>();
    }

    private void attemptNextSection() {
        sections.add(currentSection);

        Element end = manager.getHover();

        double ex = currentSection.getEndX();
        double ey = currentSection.getEndY();
        double gridSnapX = Math.round(cursor.getGraphX() / Program.GRID_SIZE) * Program.GRID_SIZE;
        double gridSnapY = Math.round(cursor.getGraphY() / Program.GRID_SIZE) * Program.GRID_SIZE;

        if (end != null && ex == gridSnapX && ey == gridSnapY) {
            attempLink(end);
            active = false;
        } else {
            currentSection = new LinkSection();

            currentSection.setStartPosition(ex, ey);
            currentSection.setEndPosition(ex, ey);
        }
    }

    private void attempLink(Element end) {
        start.setLinked(false);

        // TODO: add link to link
        if (!(end.getId() == Pin.ID)) {
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
