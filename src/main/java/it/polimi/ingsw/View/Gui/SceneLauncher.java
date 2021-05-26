package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageLogin;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageNumPlayers;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static java.lang.Integer.parseInt;

public class SceneLauncher {
    private ClientController controller;
    private PlayerBoard playerBoard;

    public SceneLauncher(ClientController controller) {
        this.controller = controller;
    }

    public Scene askLogin(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label nickLabel = new Label("Nickname: ");
        GridPane.setConstraints(nickLabel, 0, 0);

        TextField nickname = new TextField();
        GridPane.setConstraints(nickname, 1,0);
        String nic = nickname.getText();

        Button nickButton = new Button("Go");
        GridPane.setConstraints(nickButton, 1, 2);
        nickButton.setOnAction(e->{
            controller.sendMessage(new MessageLogin(nic));
        });

        grid.getChildren().addAll(nickLabel, nickname, nickButton);

        return new Scene(grid, 600, 400);
    }

    public Scene askNumPlayer() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label numPlayersLabel = new Label("Number of players: ");
        GridPane.setConstraints(numPlayersLabel, 0, 0);

        TextField numPlayersField = new TextField();
        GridPane.setConstraints(numPlayersField, 1,0);
        int numPlayers = parseInt(numPlayersField.getText());

        Button nickButton = new Button("Go");
        GridPane.setConstraints(nickButton, 1, 2);
        nickButton.setOnAction(e->{
            controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), numPlayers));
        });

        grid.getChildren().addAll(numPlayersLabel, numPlayersField, nickButton);

        return new Scene(grid, 600, 400);
    }
}
