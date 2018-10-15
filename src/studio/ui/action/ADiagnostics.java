package studio.ui.action;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import studio.program.Block;
import studio.program.Element;
import studio.program.Link;
import studio.ui.InteractionManager;

public class ADiagnostics extends Action {

    private final KeyCombination KC_DIAGNOSTICS = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);

    public ADiagnostics(InteractionManager manager) {
        super(manager);
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        if (KC_DIAGNOSTICS.match(event)) {
            int nBlocks = 0;
            int nLinks = 0;

            for (Element e : manager.program.getElements()) {
                if (e.eid == Block.EID) nBlocks++;
                if (e.eid == Link.EID) nLinks++;
            }

            System.out.println("BLOCK COUNT: " + nBlocks);
            System.out.println("LINK COUNT : " + nLinks);
            System.out.println("");
        }
    }
}
