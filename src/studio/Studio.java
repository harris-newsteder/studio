package studio;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import studio.program.Program;
import studio.program.entity.block.BAnd;
import studio.program.entity.block.BSum;

public class Studio extends Application {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     *
     */
    private boolean running = true;

    /*
     *
     */
    private Program program = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas();

        Pane pane = new Pane();
        pane.getChildren().add(canvas);

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        program = new Program(canvas);
        program.addEntity(new BAnd(program));
        program.addEntity(new BSum(program));

        // start the backend thread
        Task<Void> backendTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (running) {
                    // TODO: dt
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
                program.draw();
            }
        };
        drawTask.start();

        primaryStage.setScene(new Scene(pane));
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setTitle("Logica Studio");
        primaryStage.setOnCloseRequest(event -> {
            running = false;
        });
        primaryStage.show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        launch(args);
    }
}
