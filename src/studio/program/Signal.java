package studio.program;

public class Signal {
    public enum Type {
        DISCRETE,
        ANALOG,
        NUMBER
    }

    private double value = 0.0;
    private Type type;

    public Signal() {

    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
