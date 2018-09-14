package studio.program;

/*
 *
 */
public class Var {
    public enum Type {
        DISCRETE_SIGNAL,
        ANALOG_SIGNAL,
        NUMBER,
        U8,
        U16,
        U32,
        I8,
        I16,
        I32,
        F32,
        F64
    }

    private Type type;
    private double value = 0.0;

    public Var(Type type) {
        this.type = type;
    }

    public Var(Type type, double value) {
        this.type = type;
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }
}
