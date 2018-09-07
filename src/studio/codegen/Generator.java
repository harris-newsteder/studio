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

    private final Logger logger = LoggerFactory.getLogger(Generator.class);
    private String outputFilePath = "C:\\Users\\\\Desktop\\out.c";
    private PrintWriter writer = null;

    /*
     *
     */
    private HashMap<String, Block> blockDictionary = null;

    /*
     *
     */
    private ArrayList<Block> blocks = null;

    /*
     *
     */
    private ArrayList<Link> links = null;

    public Generator() {

    }

    public void generate(Program program) throws Exception {
        writer = new PrintWriter(outputFilePath);

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

        for (Block b : blockDictionary.values()) {
            logger.info(b.getName());
        }

        generateConstants();
        generateTypes();
        generateFunctions();
        generateVariables();
        generateSetup();
        generateLoop();

        writer.close();
    }

    private void generateConstants() {
        //
    }

    private void generateTypes() {
        // generate block structures
    }

    private void generateFunctions() {
        // need to generate a function for each type of block in the program
    }

    private void generateVariables() {
        // need to generate a variable for every link in the program



        // need to generate a variable for every block in the program
    }

    private void generateSetup() {
        puts("void setup()");
        puts("{");

        //

        puts("}");
        puts("");
    }

    private void generateLoop() {
        puts("void loop()");
        puts("{");


        puts("}");
    }

    private void puts(String string) {
        writer.write(string + "\n");
    }
}
