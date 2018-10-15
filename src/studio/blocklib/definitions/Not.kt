package studio.blocklib.definitions

import studio.blocklib.BlockDefinition
import studio.program.Block
import studio.program.Pin
import studio.program.Variable
import studio.shape.Rectangle

class Not : BlockDefinition() {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        val NAME: String = "not"
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        // I literally just want to use kotlin for the string literals
        genFn = """

        @PIN(1) = !@PIN(0);

        """.trimIndent()

        genInit = """



        """.trimIndent()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun constructInstance(): Block {
        val ins: Block = Block(NAME)

        ins.body = Rectangle()
        ins.symbol = "m 20 0 l -55 25 l 0 -50 l 55 25 M 0 0 m 20 0 a 5 5 0 1 0 10 0 a 5 5 0 1 0 -10 0";

        ins.pins.add(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(0)
                .ofFlow(Pin.Flow.INPUT)
                .onSide(Pin.Side.LEFT)
                .attachedAt(-40, 0))

        ins.pins.add(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(1)
                .ofFlow(Pin.Flow.OUTPUT)
                .onSide(Pin.Side.RIGHT)
                .attachedAt(40, 0))

        return ins
    }
}