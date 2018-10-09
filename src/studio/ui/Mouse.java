package studio.ui;

public final class Mouse {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private int realX = 0;
    private int realY = 0;

    /*
     *
     */
    private int viewX = 0;
    private int viewY = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Mouse() {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS & SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getRealX() {
        return realX;
    }

    public void setRealX(int realX) {
        this.realX = realX;
    }

    public int getRealY() {
        return realY;
    }

    public void setRealY(int realY) {
        this.realY = realY;
    }

    public void setRealPosition(int realX, int realY) {
        this.realX = realX;
        this.realY = realY;
    }

    public int getViewX() {
        return viewX;
    }

    public void setViewX(int viewX) {
        this.viewX = viewX;
    }

    public int getViewY() {
        return viewY;
    }

    public void setViewY(int viewY) {
        this.viewY = viewY;
    }

    public void setViewPosition(int viewX, int viewY) {
        this.viewX = viewX;
        this.viewY = viewY;
    }
}
