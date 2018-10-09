package studio.gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.blocklib.BlockDefinition;
import studio.blocklib.BlockLibrary;
import studio.program.*;

import java.io.*;
import java.util.*;

public class Generator {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTANTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAB = "    ";
    private static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String outputFilePath = "C:\\Users\\Harris\\Desktop\\gen\\gen.ino";
    private PrintWriter writer = null;

    /*
     * a list containing one entry for each type of block in the program
     */
    private ArrayList<Block> catalog = null;

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
    private EnumMap<Variable.Type, String> typeStringMap = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Generator() {
        typeStringMap = new EnumMap<Variable.Type, String>(Variable.Type.class);
        typeStringMap.put(Variable.Type.UNSET,          "UNSET");
        typeStringMap.put(Variable.Type.DISCRETE_SIGNAL, "bool");
        typeStringMap.put(Variable.Type.ANALOG_SIGNAL,    "u16");
        typeStringMap.put(Variable.Type.NUMBER,           "f32");
        typeStringMap.put(Variable.Type.BOOLEAN,         "bool");
        typeStringMap.put(Variable.Type.U8,                "u8");
        typeStringMap.put(Variable.Type.U16,              "u16");
        typeStringMap.put(Variable.Type.U32,              "u32");
        typeStringMap.put(Variable.Type.I8,                "i8");
        typeStringMap.put(Variable.Type.I16,              "i16");
        typeStringMap.put(Variable.Type.I32,              "i32");
        typeStringMap.put(Variable.Type.F32,              "f32");
        typeStringMap.put(Variable.Type.F64,              "f64");

        catalog = new ArrayList<>();
        blocks = new ArrayList<>();
        links = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FUNCTIONS
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
        catalog.clear();
        blocks.clear();
        links.clear();

        for (Element e : program.getElements()) {
            //
            if (e.getEID() == Link.EID) {
                if (links.contains(e)) {
                    // this should not be possible
                    LOGGER.error("DUPLICATE LINK DETECTED IN PROGRAM ELEMENT LIST");
                    continue;
                }

                links.add((Link)e);
            }

            //
            if (e.getEID() == Block.EID) {
                if (blocks.contains(e)) {
                    // this should not be possible
                    LOGGER.error("DUPLICATE BLOCK DETECTED IN PROGRAM ELEMENT LIST");
                    continue;
                }

                blocks.add((Block)e);

                if (!catalog.contains(e)) {
                    catalog.add((Block)e);
                }
            }
        }
    }

    private void generateIncludes() {
        state("#include <stdint.h>");
        nl();
    }

    private void generateConstants() {
        //
    }

    private void generateTypes() {
        state("typedef uint8_t   u8");
        // for some reason the arduino libraries already have u16 defined...
        // state("typedef uint16_t u16");
        state("typedef uint32_t u32");
        state("typedef int8_t    i8");
        state("typedef int16_t  i16");
        state("typedef int32_t  i32");
        state("typedef float    f32");
        state("typedef double   f64");

        nl();

        // generate block structures
        for (Block b : catalog) {
            puts("typedef struct");
            puts("{");

            // generate all the variable for the block pins
            puts(TAB + "// pins");
            for (Pin p : b.getPins()) {
                state(TAB + typeStringMap.get(p.getVariable().getType()) + " *p" + p.getIndex());
            }

            // generate all the internal block variables
            puts(TAB + "// variables");
            for (String name : b.getVars().keySet()) {
                Variable var = b.getVars().get(name);
                state(TAB + typeStringMap.get(var.getType()) + " " + name);
            }

            state("} b_"  + b.getName() + "_t");
            nl();
        }
    }

    private void generateFunctions() throws Exception {
        // need to generate a function for each type of block in the program
        for (Block b : catalog) {
            String bn = b.getName();
            BlockDefinition bd = BlockLibrary.lookup(bn);

            String[] lines = bd.getGenFn().split("\n");

            puts("void b_" + bn + "_fn(b_" + bn + "_t *b)");
            puts("{");

            for (String line : lines) {
                // don't write out empty lines
                if (line.length() == 0) continue;

                line = line.replace("@VAR(", "(b->");
                line = line.replace("@PIN(", "*(b->p");

                puts(TAB + line);
            }

            puts("}");

            nl();
        }
    }

    private void generateVariables() {
        for (Link l : links) {
            state(typeStringMap.get(l.getSource().getVariable().getType()) + " l" + l.getUID());
        }

        nl();

        for (Block b : blocks) {
            state("b_" + b.getName() + "_t b" + b.getUID());
        }

        nl();
    }

    private void generateSetup() {
        puts("void setup()");
        puts("{");

        state(TAB + "Serial.begin(9600)");
        nl();

        for (Block b : blocks) {
            // pins
            for (Pin p : b.getPins()) {
                if (!p.isLinked()) continue;
                state(TAB + "b" + b.getUID() + ".p" + p.getIndex() + " = &l" + p.getLink().getUID());
            }

            // vars
            for (String name : b.getVars().keySet()) {
                Variable var = b.getVars().get(name);
                state(TAB + "(b" + b.getUID() +  "." + name + ") = " + var.getValue().toString());
            }



            BlockDefinition bd = BlockLibrary.lookup(b.getName());

            String[] lines = bd.getGenInit().split("\n");
            for (String line : lines) {
                // don't write out empty lines
                if (line.length() == 0) continue;

                line = line.replace("@VAR(", "(b.");
                line = line.replace("@PIN(", "*(b.p");

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
            state(TAB + "b_" + b.getName() + "_fn(&b" + b.getUID() + ")");
        }
        puts("}");
    }

    private void puts(String string) {
        writer.write(string + "\n");
    }

    private void nl() {
        puts("");
    }

    private void state(String string) {
        puts(string + ";");
    }
}
