package studio.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.Program;
import studio.program.Signal;
import studio.program.element.Block;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.Pin;

import java.io.PrintWriter;
import java.util.*;

public class Generator {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final String TAB = "    ";
    private final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    private String outputFilePath = "C:\\Users\\family\\Desktop\\out.c";
    private PrintWriter writer = null;

    /*
     * a dictionary with one entry for every type of block in the program
     */
    private HashMap<String, Block> blockDictionary = null;

    /*
     * a list of all blocks in the program
     */
    private ArrayList<Block> blocks = null;

    /*
     * a list of all links in the program
     */
    private ArrayList<Link> links = null;

    /*
     *
     */
    private EnumMap<Signal.Type, String> typeStringMap = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Generator() {
        typeStringMap = new EnumMap<Signal.Type, String>(Signal.Type.class);
        typeStringMap.put(Signal.Type.DISCRETE, "u8");
        typeStringMap.put(Signal.Type.ANALOG, "f32");
        typeStringMap.put(Signal.Type.NUMBER, "f32");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void generate(Program program) throws Exception {
        writer = new PrintWriter(outputFilePath);

        organizeElements(program);

        generateConstants();
        generateTypes();
        generateFunctions();
        generateVariables();
        generateSetup();
        generateLoop();

        writer.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void organizeElements(Program program) {
        blockDictionary = new HashMap<>();
        blocks = new ArrayList<>();
        links = new ArrayList<>();

        for (Element e : program.getElements()) {
            switch (e.getID()) {
                case Link.ID:
                    break;
                case Pin.ID:
                    break;
                case Block.ID:
                    Block block = (Block)e;
                    if (!blocks.contains(block)) {
                        blocks.add(block);
                    } else {
                        LOGGER.error("duplicate block in element list!");
                    }

                    if (!blockDictionary.containsValue(block)) {
                        blockDictionary.put(block.getName(), block);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void generateConstants() {
        //
    }

    private void generateTypes() {
        // generate block structures
        for (Block b : blockDictionary.values()) {
            puts("typedef struct");
            puts("{");

            for (Pin p : b.getPins()) {
                statement(TAB + typeStringMap.get(p.getSignal().getType()) + " *p" + p.getIndex());
            }

            statement("} b_"  + b.getName() + "_t");
            nl();
        }
    }

    private void generateFunctions() {
        // need to generate a function for each type of block in the program
        for (Block b : blockDictionary.values()) {
            String bn = b.getName();

            puts("void b_" + bn + "_fn(b_" + bn + "_t *b)");
            puts("{");
            puts("}");
            nl();
        }
    }

    private void generateVariables() {
        for (Block b : blocks) {
            statement("b_" + b.getName() + "_t b" + b.getUID());
        }

        nl();
    }

    private void generateSetup() {
        puts("void setup()");
        puts("{");

        for (Block b : blocks) {

        }

        puts("}");
        nl();
    }

    private void generateLoop() {
        puts("void loop()");
        puts("{");

        puts("}");
    }

    private void puts(String string) {
        writer.write(string + "\n");
    }

    private void nl() {
        puts("");
    }

    private void statement(String string) {
        puts(string + ";");
    }
}
