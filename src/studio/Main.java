package studio;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import studio.interaction.InteractionManager;
import studio.program.Program;
import studio.view.View;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Global.running = true;

        Parent root = FXMLLoader.load(getClass().getResource("/res/main.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setTitle("Logica Studio");
        primaryStage.setOnCloseRequest(event -> {
            Global.running = false;
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
