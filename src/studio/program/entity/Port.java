package studio.program.entity;

import studio.program.Program;

/*
 * ports are connections between programs and the outside, physical world
 */
public class Port extends Entity {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ID = "port";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * ports only have a single pin
     */
    private Pin pin;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Port(Program program) {
        super(program);
        id = ID;
    }
}
