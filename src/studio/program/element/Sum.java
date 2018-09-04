package studio.program.element;

import studio.program.Program;

public class Sum extends Block {
    public Sum() {
        super();
    }

    @Override
    public void createPins(Program program) {
        Pin in1 = new Pin(this);
        in1.setFlow(Pin.Flow.INPUT);
        in1.setSide(Pin.Side.LEFT);
        in1.setAttachmentPoint(-width / 2, -20);
        in1.updatePosition();

        Pin in2 = new Pin(this);
        in2.setFlow(Pin.Flow.INPUT);
        in2.setSide(Pin.Side.LEFT);
        in2.setAttachmentPoint(-width / 2, 20);
        in2.updatePosition();

        Pin out1 = new Pin(this);
        out1.setFlow(Pin.Flow.OUTPUT);
        out1.setSide(Pin.Side.RIGHT);
        out1.setAttachmentPoint(width / 2, 0);
        out1.updatePosition();

        program.addElement(in1);
        program.addElement(in2);
        program.addElement(out1);

        addPin(in1);
        addPin(in2);
        addPin(out1);
    }
}
