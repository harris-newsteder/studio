package studio.program.dictionary;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.program.element.Link;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public final class BlockDictionary {

    /*
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockDictionary.class);

    /*
     *
     */
    private static HashMap<String, BlockDefinition> definitions = null;

    private BlockDictionary() {

    }

    public static void init() {
        definitions = new HashMap<>();

        try {
            populate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BlockDefinition lookup(String name) {
        return definitions.get(name);
    }

    /*
     *
     */
    private static void populate() throws Exception {

        Gson gson = new Gson();

        File[] definitionFiles = loadDefinitionFiles();

        for (File file : definitionFiles) {
            BlockDefinition def = gson.fromJson(new BufferedReader(new FileReader(file)), BlockDefinition.class);

            if (definitions.containsKey(def.name)) {
                LOGGER.error(
                    "found block definition with identical name \"" + def.name + "\" in file \"" + file.getName() + "\""
                );
            }

            definitions.put(def.name, def);
        }
    }

    /*
     *
     */
    private static File[] loadDefinitionFiles() {
        File directory = new File("src/res/blocks");

        File[] definitionFiles = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

        return definitionFiles;
    }
}
