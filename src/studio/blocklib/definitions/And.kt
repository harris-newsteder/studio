package studio.blocklib.definitions

import studio.blocklib.BlockDefinition
import studio.program.Block
import studio.program.Pin
import studio.program.Variable
import studio.shape.Rectangle

class And : BlockDefinition() {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        val NAME: String = "and"
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        // I literally just want to use kotlin for the string literals
        genFn = """

        @PIN(2) = @PIN(0) && @PIN(1);

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
        ins.symbol = "M -35 -35 l 35 0 c 45 0 45 70 0 70 l -35 0 l 0 -70";

        ins.addPin(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(0)
                .ofFlow(Pin.Flow.INPUT)
                .onSide(Pin.Side.LEFT)
                .attachedAt(-40, -20))

        ins.addPin(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(1)
                .ofFlow(Pin.Flow.INPUT)
                .onSide(Pin.Side.LEFT)
                .attachedAt(-40, 20))

        ins.addPin(Pin.create()
                .withParent(ins)
                .withVariable(Variable(Variable.Type.DISCRETE_SIGNAL))
                .atIndex(2)
                .ofFlow(Pin.Flow.OUTPUT)
                .onSide(Pin.Side.RIGHT)
                .attachedAt(40, 0))

        return ins
    }
}