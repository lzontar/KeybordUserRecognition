package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("KeyboardUserRecognition");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene mainScene = new Scene(root, screenSize.getWidth()/2, screenSize.getHeight()/2);

        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);

        primaryStage.show();
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
        });
    }

    /*long timePassed = System.nanoTime() - start;
    double seconds = (double) timePassed / 1_000_000_000.0;
		System.out.println("Time passed: " + seconds + " seconds");*/
    public static void main(String[] args) {
        launch(args);
    }
}
