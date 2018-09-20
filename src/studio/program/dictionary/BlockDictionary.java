package studio.program.dictionary;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public final class BlockDictionary {
    private BlockDictionary() {

    }

    public static void populate() {
        JsonParser jp = new JsonParser();
        try {
            Gson gson = new Gson();
            BlockDefinition bd = gson.fromJson(new BufferedReader(
                            new FileReader(
                                    new File("src/res/blocks/discrete_input.json")
                            )
                    )
            , BlockDefinition.class);

            System.out.println(bd.name);

            for (VarDefinition vd : bd.vars) {
                System.out.println(vd.name);
                System.out.println(vd.type);
            }

            for (PinDefinition pd : bd.pins) {
                System.out.println(pd.index);
                System.out.println(pd.type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
