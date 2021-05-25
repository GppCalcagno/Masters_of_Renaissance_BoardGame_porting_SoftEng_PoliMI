package it.polimi.ingsw.View.Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gui extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        var label = new Label("Hello, JavaFX " + ", running on Java " + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
