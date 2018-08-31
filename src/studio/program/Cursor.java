package studio.program;

public class Cursor {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * the real position of the cursor on the canvas
     * x : [0, canvas.getWidth()]
     * y : [0, canvas.getHeight()]
     */
    private double rx = 0;
    private double ry = 0;

    /*
     * the position of the cursor in the graph
     * x : [-inf, +inf]
     * y : [-inf, +inf]
     */
    private double gx = 0;
    private double gy = 0;

    /*
     * the real position of the cursor on the canvas
     * x : [0, canvas.getWidth()]
     * y : [0, canvas.getHeight()]
     */
    private Point real = null;

    /*
     * the position of the cursor on the graph
     * x : [-inf, +inf]
     * y : [-inf, +inf]
     */
    private Point graph = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Cursor() {
        real = new Point();
        graph = new Point();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double getGraphX() {
        return gx;
    }

    public void setGraphX(double gx) {
        this.gx = gx;
    }

    public double getGraphY() {
        return gy;
    }

    public void setGraphY(double gy) {
        this.gy = gy;
    }

    public double getRealX() {
        return rx;
    }

    public void setRealX(double rx) {
        this.rx = rx;
    }

    public double getRealY() {
        return ry;
    }

    public void setRealY(double ry) {
        this.ry = ry;
    }

    // TODO: remove all below here

    public Point getReal() {
        return real;
    }

    public void setReal(Point real) {
        this.real = real;
    }

    public Point getGraph() {
        return graph;
    }

    public void setGraph(Point graph) {
        this.graph = graph;
    }
}
