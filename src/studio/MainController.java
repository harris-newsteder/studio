package studio;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import studio.interaction.InteractionManager;
import studio.program.Program;
import studio.view.View;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    Pane centerPane;

    private Canvas canvas = null;
    private Program program = null;
    private View view = null;
    private InteractionManager interactionManager = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas = new Canvas();
        program = new Program();
        view = new View(program);
        interactionManager = new InteractionManager(program, view, canvas);
        view.interactionManager = interactionManager;

        centerPane.getChildren().add(canvas);

        canvas.widthProperty().bind(centerPane.widthProperty());
        canvas.heightProperty().bind(centerPane.heightProperty());

        Task<Void> backendTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (Global.running) {
                    // TODO: delta time calculation
                    program.tick(0.0);
                    Thread.sleep(0, 500000);
                }
                return null;
            }
        };
        new Thread(backendTask).start();

        // start the thread which updates the javafx canvas at ~60 fps
        AnimationTimer drawTask = new AnimationTimer() {
            @Override
            public void handle(long now) {
                view.draw(canvas.getGraphicsContext2D());
            }
        };
        drawTask.start();
    }
}