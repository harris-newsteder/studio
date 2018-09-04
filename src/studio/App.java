package studio;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import studio.program.Program;

public class App extends Application {
    public static final Color COLOR_DARK = Color.rgb(40, 40, 40);
    public static final Color COLOR_LIGHT = Color.rgb(245, 245, 245);
    public static final Color COLOR_WHITE = Color.rgb(255, 255, 255);
    public static final Color COLOR_BLACK = Color.rgb(0, 0, 0);
    public static final Color COLOR_HOVER_MASK = Color.rgb(255, 0, 0, 0.1);

    private boolean running = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Canvas canvas = new Canvas();

        root.getChildren().add(canvas);

        Program program = new Program();
        program.setCanvas(canvas);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        Scene primaryScene = new Scene(root);

        Task<Void> backendTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (running) {
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
                program.draw();
            }
        };
        drawTask.start();

        primaryStage.setScene(primaryScene);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setTitle("Logica Studio");
        primaryStage.setOnCloseRequest(event -> {
            running = false;
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
