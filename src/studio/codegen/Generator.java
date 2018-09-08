package studio.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.Program;
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
    private final Logger logger = LoggerFactory.getLogger(Generator.class);
    private String outputFilePath = "C:\\Users\\\\Desktop\\out.c";
    private PrintWriter writer = null;

    /*
     * a collection with one entry for every type of block in the program
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
    private EnumMap<Pin.SignalType, String> typeStringMap = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Generator() {
        typeStringMap = new EnumMap<Pin.SignalType, String>(Pin.SignalType.class);
        typeStringMap.put(Pin.SignalType.DISCRETE, "uint8_t");
        typeStringMap.put(Pin.SignalType.NUMBER, "float");
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
            switch (e.getId()) {
                case Block.ID:

                    Block b = (Block)e;
                    String name = b.getName();

                    if (blocks.contains(b)) {
                        logger.info("found duplicate block in program: " + b);
                        continue;
                    }

                    blocks.add(b);

                    if (!blockDictionary.containsKey(name))
                        blockDictionary.put(name, b);

                    break;
                case Link.ID:

                    Link l = (Link)e;

                    if (links.contains(l)) {
                        logger.info("found duplicate link in program: " + l);
                        continue;
                    }

                    links.add(l);

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
            puts("typedef struct {");

            int i = 0;

            for (Pin p : b.getPins()) {
                statement(TAB + typeStringMap.get(p.getType()) + " *p" + i++);
            }

            puts("} b_" + b.getName() + "_t;");
            nl();
        }
    }

    private void generateFunctions() {
        // need to generate a function for each type of block in the program
        for (Block b : blockDictionary.values()) {

        }
    }

    private void generateVariables() {
        // need to generate a variable for every link in the program
        int i = 0;

        for (Link link : links) {
            switch (link.getSource().getType()) {
                case ANALOG:
                    break;
                case NUMBER:
                    break;
                case DISCRETE:
                    statement("uint8_t l" + i);
                    break;
            }

            i++;
        }
        nl();



        // need to generate a variable for every block in the program
        i = 0;

        for (Block block : blocks) {
            puts("b_" + block.getName() + "_t b" + i + ";");
            i++;
        }
        nl();
    }

    private void generateSetup() {
        puts("void setup()");
        puts("{");

        //

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
