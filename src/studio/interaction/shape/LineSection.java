package studio.interaction.shape;

public class LineSection {
    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    public final Orientation orientation;

    /*
     *
     */
    public double startX = 0;
    public double startY = 0;

    /*
     *
     */
    public double endX = 0;
    public double endY = 0;

    public LineSection(Orientation orientation) {
        this.orientation = orientation;
    }

    /*
     * reorder the start and end points so that the start position is always less than the end position;
     * horizontal link sections will now always run from left to right
     * vertical link sections will now always run from top to bottom
     */
    public void reorder() {
        if (orientation == Orientation.HORIZONTAL) {
            if (endX < startX) {
                double swap = endX;
                endX = startX;
                startX = swap;
            }
        } else {
            if (endY < startY) {
                double swap = endY;
                endY = startY;
                startY = swap;
            }
        }
    }

    public double getLength() {
        if (orientation == Orientation.HORIZONTAL) {
            return Math.abs(endX - startX);
        } else {
            return Math.abs(endY - startY);
        }
    }
}
