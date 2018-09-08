package studio.program.element;

import studio.program.Program;

public class And extends Block {
    public And() {
        super();
        text = "AND";
        name = "and";
    }

    @Override
    public void tick(double dt) {

    }

    @Override
    public void createPins(Program program) {
        Pin p0 = new Pin(this, Pin.SignalType.DISCRETE, Pin.Flow.INPUT);
        p0.setSide(Pin.Side.LEFT);
        p0.setAttachmentPoint(-width / 2, -20);
        p0.updatePosition();

        Pin p1 = new Pin(this, Pin.SignalType.DISCRETE, Pin.Flow.INPUT);
        p1.setSide(Pin.Side.LEFT);
        p1.setAttachmentPoint(-width / 2, 20);
        p1.updatePosition();

        Pin p2 = new Pin(this, Pin.SignalType.DISCRETE, Pin.Flow.OUTPUT);
        p2.setSide(Pin.Side.RIGHT);
        p2.setAttachmentPoint(width / 2, 0);
        p2.updatePosition();

        program.addElement(p0);
        program.addElement(p1);
        program.addElement(p2);

        addPin(p0);
        addPin(p1);
        addPin(p2);
    }
}
