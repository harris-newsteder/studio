package studio.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class GenFile {
    private ArrayList<String> init = null;
    private ArrayList<String> fn = null;

    private ArrayList<String> listPtr = null;

    public GenFile(String blockName) throws Exception {
        init = new ArrayList<>();
        fn = new ArrayList<>();

        File file = new File("src/gen/" + blockName + ".gen");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";

        while ((line = br.readLine()) != null) {
            // ignore empty lines
            if (line.length() == 0) continue;

            // start of a region
            if (line.charAt(0) == '#') {
                String region = line.substring(1);

                switch (region) {
                    case "init":
                        listPtr = init;
                        break;
                    case "fn":
                        listPtr = fn;
                        break;
                    default:
                        // TODO: error out
                        break;
                }
            } else {
                listPtr.add(line);
            }
        }
    }

    public ArrayList<String> getInitLines() {
        return init;
    }

    public ArrayList<String> getFnLines() {
        return fn;
    }
}
