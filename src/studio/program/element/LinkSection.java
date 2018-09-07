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

    /*
     * reorder the start and end points so that the start position is always less than the end position;
     * horizontal link sections will now always run from left to right
     * vertical link sections will now always run from top to bottom
     */
    public void reorder() {
        if (orientation == Orientation.HORIZONTAL) {
            if (ex < sx) {
                double swap = ex;
                ex = sx;
                sx = swap;
            }
        } else {
            if (ey < sy) {
                double swap = ey;
                ey = sy;
                sy = swap;
            }
        }
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

    public double getLength() {
        if (orientation == Orientation.HORIZONTAL) {
            return Math.abs(ex - sx);
        } else {
            return Math.abs(ey - sy);
        }
    }
}
