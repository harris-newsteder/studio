package studio.blocklib.definitions

import studio.blocklib.BlockDefinition
import studio.program.Block
import studio.program.Pin
import studio.program.Variable
import studio.shape.Circle

class DiscreteOutput : BlockDefinition() {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        val NAME: String = "discrete_output"
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        genFn = """

        digitalWrite(@VAR(pin), @PIN(0));

        """.trimIndent()

        genInit = """

        pinMode(@VAR(pin), OUTPUT);

        """.trimIndent()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun constructInstance(): Block {
        val ins: Block = Block(NAME)

        ins.body = Circle()
        ins.symbol = "m -20 15 l 7 0 l 0 -30 l 26 0 l 0 30 l 7 0";

        ins.addPin(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(0)
                .ofFlow(Pin.Flow.INPUT)
                .onSide(Pin.Side.LEFT)
                .attachedAt(-40, 0))

        ins.vars.put("pin", Variable(Variable.Type.U8, 6));

        return ins
    }
}