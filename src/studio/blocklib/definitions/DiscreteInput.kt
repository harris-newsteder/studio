package studio.blocklib.definitions

import studio.blocklib.BlockDefinition
import studio.program.Block
import studio.program.Pin
import studio.program.Variable
import studio.shape.Circle

class DiscreteInput : BlockDefinition() {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        val NAME: String = "discrete_input"
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        genFn = """

        @PIN(0) = digitalRead(@VAR(pin));

        """.trimIndent()

        genInit = """

        pinMode(@VAR(pin), INPUT);

        """.trimIndent()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun constructInstance(): Block {
        val ins: Block = Block(NAME)

        ins.body = Circle()
        ins.symbol = "m -20 15 l 7 0 l 0 -30 l 26 0 l 0 30 l 7 0";

        ins.pins.add(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(0)
                .ofFlow(Pin.Flow.OUTPUT)
                .onSide(Pin.Side.RIGHT)
                .attachedAt(40, 0))

        ins.vars.put("pin", Variable(Variable.Type.U8, 5));

        return ins
    }
}