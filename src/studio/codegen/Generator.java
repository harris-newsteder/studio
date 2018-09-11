package studio.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.Program;
import studio.program.Signal;
import studio.program.element.Block;
import studio.program.element.Element;
import studio.program.element.Link;
import studio.program.element.Pin;

import java.io.*;
import java.util.*;

public class Generator {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final String TAB = "    ";
    private final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    private String outputFilePath = "C:\\Users\\Harris\\Desktop\\gen\\gen.ino";
    private PrintWriter writer = null;

    /*
     * a dictionary with one entry for every type of block in the program
     */
    private HashMap<String, Block> blockDictionary = null;

    /*
     *
     */
    private HashMap<String, GenFile> genFileDictionary = null;

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

        generateIncludes();
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

    private void organizeElements(Program program) throws Exception {
        blockDictionary = new HashMap<>();
        genFileDictionary = new HashMap<>();
        blocks = new ArrayList<>();
        links = new ArrayList<>();

        for (Element e : program.getElements()) {
            switch (e.getEID()) {
                case Link.EID:
                    Link l = (Link)e;

                    if (!links.contains(l)) {
                        links.add(l);
                    } else {
                        LOGGER.error("duplicate link in element list!");
                    }

                    break;
                case Pin.EID:
                    break;
                case Block.EID:
                    Block b = (Block)e;

                    if (!blocks.contains(b)) {
                        blocks.add(b);
                    } else {
                        LOGGER.error("duplicate block in element list!");
                    }

                    if (!blockDictionary.containsValue(b)) {
                        blockDictionary.put(b.getName(), b);
                        genFileDictionary.put(b.getName(), new GenFile(b.getName()));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void generateIncludes() {
        statement("#include <stdint.h>");
        nl();
    }

    private void generateConstants() {
        //
    }

    private void generateTypes() {

        statement("typedef uint8_t   u8");
        // for some reason the arduino libraries already have u16 defined...
        // statement("typedef uint16_t u16");
        statement("typedef uint32_t u32");
        statement("typedef int8_t    i8");
        statement("typedef int16_t  i16");
        statement("typedef int32_t  i32");
        statement("typedef float    f32");
        statement("typedef double   f64");

        nl();

        // generate block structures
        for (Block b : blockDictionary.values()) {
            puts("typedef struct");
            puts("{");

            puts(TAB + "// pins");

            for (Pin p : b.getPins()) {
                statement(TAB + typeStringMap.get(p.getSignal().getType()) + " *p" + p.getIndex());
            }

            puts(TAB + "// variables");

            for (String line : genFileDictionary.get(b.getName()).getDefLines()) {
                puts(TAB + line);
            }

            statement("} b_"  + b.getName() + "_t");
            nl();
        }
    }

    private void generateFunctions() throws Exception {
        // need to generate a function for each type of block in the program
        for (Block b : blockDictionary.values()) {
            String bn = b.getName();

            puts("void b_" + bn + "_fn(b_" + bn + "_t *b)");
            puts("{");

            for (String line : genFileDictionary.get(b.getName()).getFnLines()) {
                line = line.replace("$VAR(", "(b->");
                line = line.replace("$PIN(", "*(b->p");
                puts(TAB + line);
            }

            puts("}");

            nl();
        }
    }

    private void generateVariables() {
        for (Link l : links) {
            statement(typeStringMap.get(l.getSource().getSignal().getType()) + " l" + l.getUID());
        }

        nl();

        for (Block b : blocks) {
            statement("b_" + b.getName() + "_t b" + b.getUID());
        }

        nl();
    }

    private void generateSetup() {
        puts("void setup()");
        puts("{");

        for (Block b : blocks) {
            //
            for (Pin p : b.getPins()) {
                if (!p.isLinked()) continue;
                statement(TAB + "b" + b.getUID() + ".p" + p.getIndex() + " = &l" + p.getLink().getUID());
            }
            //
            for (String line : genFileDictionary.get(b.getName()).getInitLines()) {
                line = line.replace("$VAR(", "(b" + b.getUID() + ".");
                puts(TAB + line);
            }
            nl();
        }

        puts("}");
        nl();
    }

    private void generateLoop() {
        puts("void loop()");
        puts("{");
        for (Block b : blocks) {
            statement(TAB + "b_" + b.getName() + "_fn(&b" + b.getUID() + ")");
        }
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
