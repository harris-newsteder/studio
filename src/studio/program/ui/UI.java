package studio.program.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import studio.program.Program;
import studio.program.ui.interaction.InteractionManager;
import studio.program.ui.view.View;

public class UI {
    /*
     *
     */
    private Program program = null;

    /*
     *
     */
    private View view = null;

    /*
     *
     */
    private InteractionManager interactionManager = null;

    public UI(Program program, Canvas canvas) {
        this.program = program;

        view = new View(program);
        interactionManager = new InteractionManager(program, view, canvas);
        view.interactionManager = interactionManager;
    }

    public void tick(double dt) {

    }

    public void draw(GraphicsContext gc) {
        view.draw(gc);
    }
}
