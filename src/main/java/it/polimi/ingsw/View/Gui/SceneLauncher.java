package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageLogin;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageNumPlayers;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class SceneLauncher {
    private ClientController controller;
    private PlayerBoard playerBoard;

    public SceneLauncher(ClientController controller, PlayerBoard playerBoard) {
        this.controller = controller;
        this.playerBoard = playerBoard;
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

        Button nickButton = new Button("Go");
        GridPane.setConstraints(nickButton, 1, 2);
        nickButton.setOnAction(e->{
            playerBoard.setNickname(nickname.getText());
            controller.sendMessage(new MessageLogin(nickname.getText()));
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


        Button numButton = new Button("Go");
        GridPane.setConstraints(numButton, 1, 2);
        numButton.setOnAction(e->{
            try{
                controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), Integer.parseInt(numPlayersField.getText())));
            }catch (NumberFormatException ignore){}
        });

        grid.getChildren().addAll(numPlayersLabel, numPlayersField, numButton);

        return new Scene(grid, 600, 400);
    }

    public Scene askServerInfo() {
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
            controller.connect(addressIn.getText(), Integer.parseInt(portIn.getText()));
        });

        grid.getChildren().addAll(address, addressIn, port, portIn, loginButton);
        return new Scene(grid, 600, 400);
    }

    public Scene mainboard(){

        //layout comandi bottoni
        TilePane tilePane = new TilePane();
        tilePane.setVgap(10);
        tilePane.setHgap(20);
        tilePane.setPrefColumns(2);

        //bottoni azione
        Button actionMarketTray = new Button("Extraction\nMarble");
        Button actionBuyDevCard = new Button("Buy DevCard");
        Button actionActiveProduction = new Button("Active\nProduction");
        //bottoni show
        Button  showMarketTray = new Button("Show\nMarket Tray");
        Button showDevDeck = new Button("Show\nDevDeck");

        tilePane.getChildren().addAll(actionMarketTray, actionActiveProduction, actionBuyDevCard, showDevDeck, showMarketTray);

        //layout leadercard
        VBox leadercard = new VBox();
        Image leaderCard1 =  new Image("front/LCDL4.png");
        Image leaderCard2 = new Image("front/LCTL1.png");

        /*if(playerBoard.getLeaderCards().get(0)!=null){
            leaderCard1 =
        }*/
        ImageView leaderCard1View = new ImageView(leaderCard1);
        leaderCard1View.setFitHeight(350);
        leaderCard1View.setFitWidth(200);

        /*if(playerBoard.getLeaderCards().get(1)!=null) {
            leaderCard2 =
        }*/
        ImageView leaderCard2View = new ImageView(leaderCard2);
        leaderCard2View.setFitHeight(350);
        leaderCard2View.setFitWidth(200);

        leadercard.getChildren().addAll(leaderCard1View, leaderCard2View);

        //layout for boardMain
        StackPane boardMain = new StackPane();
        //boardmain in background
        Image board = new Image("board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
        /*BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.AUTO(-10.0));

        Background background = new Background(backgroundImage);
        boardMain.setBackground(background);*/
        ImageView boardView = new ImageView(board);
        boardView.setFitWidth(1000);
        boardView.setFitHeight(800);
        boardMain.getChildren().add(boardView);

        //move the faithmarker
        Image faithMarker = new Image("punchboard/faithMarker.png");
        ImageView faithMarkerView = new ImageView(faithMarker);
        faithMarkerView.setX(20);
        faithMarkerView.setY(30);

        boardMain.getChildren().add(faithMarkerView);

        //popes space
        Image popeSpace = new Image("punchboard/quadrato giallo.png");
        Image popeSpace1 = new Image("punchboard/quadrato arancione.png");
        Image popeSpace2 = new Image("punchboard/quadrato rosso.png");
        ImageView popeSpaceView1 = new ImageView(popeSpace);
        popeSpaceView1.setX(30);
        popeSpaceView1.setY(50);
        ImageView popeSpaceView2 = new ImageView(popeSpace1);
        popeSpaceView2.setX(30);
        popeSpaceView2.setY(50);
        ImageView popeSpaceView3 = new ImageView(popeSpace2);
        popeSpaceView3.setX(30);
        popeSpaceView3.setY(50);

        //if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[0])
            boardMain.getChildren().add(popeSpaceView1);
        //if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[1])
            boardMain.getChildren().add(popeSpaceView2);
        //if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[2])
            boardMain.getChildren().add(popeSpaceView3);


        //layout generale
        VBox left = new VBox();
        left.getChildren().addAll(tilePane, leadercard);

        BorderPane total =new BorderPane();
        total.setLeft(left);
        total.setCenter(boardMain);


        Scene scene = new Scene(total);
        return scene;
    }
}
