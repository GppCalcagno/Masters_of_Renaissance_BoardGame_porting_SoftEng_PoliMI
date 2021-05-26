package it.polimi.ingsw.View.Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GuiJavaFX extends Application {
    private Gui gui;
    private static Stage window;

    public static Stage getWindow() {
        return window;
    }


    @Override
    public void start(Stage stage) {
        gui= new Gui();
        window = stage;
        window.setScene(ServerInfo());
        window.show();
    }

    public Scene ServerInfo(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label address = new Label("Server address: ");
        GridPane.setConstraints(address, 0, 0);

        TextField addressIn = new TextField("127.0.0.1");
        GridPane.setConstraints(addressIn, 1,0);

        Label port = new Label("Port: ");
        GridPane.setConstraints(port, 0,1);

        TextField portIn = new TextField("1234");
        GridPane.setConstraints(portIn, 1,1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e->{
            Thread guiThread= new Thread(gui);
            gui.getController().connect(addressIn.getText(), Integer.parseInt(portIn.getText()));
            guiThread.start();
        });

        grid.getChildren().addAll(address, addressIn, port, portIn, loginButton);


        return new Scene(grid, 600, 400);
    }
}
