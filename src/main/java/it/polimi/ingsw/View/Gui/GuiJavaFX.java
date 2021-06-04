package it.polimi.ingsw.View.Gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class GuiJavaFX extends Application {


    @Override
    public void start(Stage stage) {
        stage.setResizable(false);

        stage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage.setWidth(910);
        stage.setHeight(589);
        stage.show();
    }




}
