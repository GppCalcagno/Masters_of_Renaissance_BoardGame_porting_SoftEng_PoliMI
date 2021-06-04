package it.polimi.ingsw.View.Gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class GuiJavaFX extends Application {


    @Override
    public void start(Stage stage) {
        /*stage.setMinHeight(300);
        stage.setMinWidth(400);

        /*stage.setMaxHeight(1000);
        stage.setMaxWidth(3000);*/
        //stage.sizeToScene();
        //stage.setMaximized(true);
        stage.setResizable(false);
        //stage.setMaxHeight(800);
        //stage.setMaxWidth(5000);
        stage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage.setWidth(910);
        stage.setHeight(589);
        stage.show();
    }




}
