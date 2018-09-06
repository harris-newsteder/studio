package studio.program.element;

public class LinkSection {
    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    private Orientation orientation;
    private double sx = 0;
    private double sy = 0;
    private double ex = 0;
    private double ey = 0;

    public LinkSection() {

    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setStartPosition(double x, double y) {
        sx = x;
        sy = y;
    }

    public void setEndPosition(double x, double y) {
        ex = x;
        ey = y;
    }

    public double getStartX() {
        return sx;
    }

    public double getStartY() {
        return sy;
    }

    public double getEndX() {
        return ex;
    }

    public double getEndY() {
        return ey;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
