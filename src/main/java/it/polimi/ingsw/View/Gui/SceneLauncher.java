package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageExchangeWarehouse;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageLogin;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageNumPlayers;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class SceneLauncher {
    private ClientController controller;
    private PlayerBoard playerBoard;
    private Stage stage;

    public SceneLauncher(ClientController controller, PlayerBoard playerBoard) {
        this.controller = controller;
        this.playerBoard = playerBoard;
        this.stage=null;
    }

    public void setStage() {
        this.stage = new Stage();
    }

    public Stage getStage() {
        return stage;
    }

    public Scene askServerInfo(){
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

            Thread guiThread= new Thread(()->controller.connect(addressIn.getText(), Integer.parseInt(portIn.getText())));
            guiThread.start();
            });

        grid.getChildren().addAll(address, addressIn, port, portIn, loginButton);

        return new Scene(grid, 600, 400);
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
        Image leaderCard1;
        Image leaderCard2;

        if(playerBoard.getLeaderCards().get(0)!=null){
            leaderCard1 = new Image("front/"+playerBoard.getLeaderCards().get(1)+".png");
        }
        else leaderCard1 = new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-49-1.png");

        ImageView leaderCard1View = new ImageView(leaderCard1);
        leaderCard1View.setFitHeight(350);
        leaderCard1View.setFitWidth(200);

        if(playerBoard.getLeaderCards().get(1)!=null) {
            leaderCard2 = new Image("front/"+playerBoard.getLeaderCards().get(1)+".png");
        }
        else leaderCard2 = new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-49-1.png");
        ImageView leaderCard2View = new ImageView(leaderCard2);
        leaderCard2View.setFitHeight(350);
        leaderCard2View.setFitWidth(200);

        leadercard.getChildren().addAll(leaderCard1View, leaderCard2View);

        //layout for boardMain
        Pane boardMain = new Pane();
        //boardmain in background
        Image board = new Image("board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");

        ImageView boardView = new ImageView(board);
        boardView.setFitWidth(1000);
        boardView.setFitHeight(800);
        boardMain.getChildren().add(boardView);

        //move the faithmarker
        Image faithMarker = new Image("punchboard/faithMarker.png");
        ImageView faithMarkerView = new ImageView(faithMarker);
        faithMarkerView.setFitWidth(45);
        faithMarkerView.setFitHeight(50);
        faithMarkerView.setX(getFaithMarkerWidht());
        faithMarkerView.setY(getFaithMarkerHeight());
        boardMain.getChildren().add(faithMarkerView);

        //popes space
        Image popeSpace = new Image("punchboard/quadrato giallo.png");
        Image popeSpace1 = new Image("punchboard/quadrato arancione.png");
        Image popeSpace2 = new Image("punchboard/quadrato rosso.png");
        ImageView popeSpaceView1 = new ImageView(popeSpace);

        popeSpaceView1.setFitHeight(90);
        popeSpaceView1.setFitWidth(90);
        popeSpaceView1.setX(235);
        popeSpaceView1.setY(112);
        ImageView popeSpaceView2 = new ImageView(popeSpace1);
        popeSpaceView2.setFitHeight(90);
        popeSpaceView2.setFitWidth(90);
        popeSpaceView2.setX(482);
        popeSpaceView2.setY(50);
        ImageView popeSpaceView3 = new ImageView(popeSpace2);
        popeSpaceView3.setFitHeight(90);
        popeSpaceView3.setFitWidth(90);
        popeSpaceView3.setX(773);
        popeSpaceView3.setY(112);


        if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[0])
            boardMain.getChildren().add(popeSpaceView1);
        if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[1])
            boardMain.getChildren().add(popeSpaceView2);
        if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[2])
            boardMain.getChildren().add(popeSpaceView3);


        //WAREHOUSE
        if (playerBoard.getWarehouse()[0][0] != null) {
            Image firstRow = new Image("punchboard/" + playerBoard.getWarehouse()[0][0] + ".png");
            ImageView firstRowView = new ImageView(firstRow);
            firstRowView.setFitWidth(45);
            firstRowView.setFitHeight(50);
            firstRowView.setX(110);
            firstRowView.setY(340);
            boardMain.getChildren().add(firstRowView);
        }
        if (playerBoard.getWarehouse()[1][0] != null) {
            Image secondRowFirstColumn = new Image("punchboard/" + playerBoard.getWarehouse()[1][0] + ".png");
            ImageView secondRowFirstColumnView = new ImageView(secondRowFirstColumn);
            secondRowFirstColumnView.setFitWidth(45);
            secondRowFirstColumnView.setFitHeight(50);
            secondRowFirstColumnView.setX(83);
            secondRowFirstColumnView.setY(410);
            boardMain.getChildren().add(secondRowFirstColumnView);
        }
        if (playerBoard.getWarehouse()[1][1] != null) {
            Image secondRowSecondColumn = new Image("punchboard/" + playerBoard.getWarehouse()[1][1] + ".png");
            ImageView secondRowSecondColumnView = new ImageView(secondRowSecondColumn);
            secondRowSecondColumnView.setFitWidth(45);
            secondRowSecondColumnView.setFitHeight(50);
            secondRowSecondColumnView.setX(135);
            secondRowSecondColumnView.setY(410);
            boardMain.getChildren().add(secondRowSecondColumnView);
        }
        if (playerBoard.getWarehouse()[2][0] != null) {
            Image thirdRowFirstColumn = new Image("punchboard/" + playerBoard.getWarehouse()[2][0] + ".png");
            ImageView thirdRowFirstColumnView = new ImageView(thirdRowFirstColumn);
            thirdRowFirstColumnView.setFitWidth(45);
            thirdRowFirstColumnView.setFitHeight(50);
            thirdRowFirstColumnView.setX(60);
            thirdRowFirstColumnView.setY(485);
            boardMain.getChildren().add(thirdRowFirstColumnView);
        }
        if (playerBoard.getWarehouse()[2][1] != null) {
            Image thirdRowSecondColumn = new Image("punchboard/" + playerBoard.getWarehouse()[2][1] + ".png");
            ImageView thirdRowSecondColumnView = new ImageView(thirdRowSecondColumn);
            thirdRowSecondColumnView.setFitWidth(45);
            thirdRowSecondColumnView.setFitHeight(50);
            thirdRowSecondColumnView.setX(107);
            thirdRowSecondColumnView.setY(485);
            boardMain.getChildren().add(thirdRowSecondColumnView);
        }
        if (playerBoard.getWarehouse()[2][2] != null) {
            Image thirdRowThirdColumn = new Image("punchboard/" + playerBoard.getWarehouse()[2][2] + ".png");
            ImageView thirdRowThirdColumnView = new ImageView(thirdRowThirdColumn);
            thirdRowThirdColumnView.setFitWidth(45);
            thirdRowThirdColumnView.setFitHeight(50);
            thirdRowThirdColumnView.setX(150);
            thirdRowThirdColumnView.setY(485);
            boardMain.getChildren().add(thirdRowThirdColumnView);
        }
        //WAREHOUSE EXCHANGE
        CheckBox row1 = new CheckBox();
        CheckBox row2 = new CheckBox();
        CheckBox row3 = new CheckBox();
        Button confirmExchange = new Button("Exchange");

        row1.setLayoutX(40);
        row1.setLayoutY(350);
        row2.setLayoutX(40);
        row2.setLayoutY(420);
        row3.setLayoutX(40);
        row3.setLayoutY(495);
        confirmExchange.setLayoutX(40);
        confirmExchange.setLayoutY(540);
        boardMain.getChildren().addAll(row1, row2, row3, confirmExchange);
        confirmExchange.setOnAction(e->{
            if(row1.isSelected() && row2.isSelected() && !row3.isSelected())
            controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), 0, 1));
            else if(row1.isSelected() && !row2.isSelected() && row3.isSelected())
                controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), 0, 2));
            else if(!row1.isSelected() && row2.isSelected() && row3.isSelected())
                controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), 1, 2));
        });



        //layout generale
        VBox left = new VBox();
        left.getChildren().addAll(tilePane, leadercard);

        BorderPane total =new BorderPane();
        total.setLeft(left);
        total.setCenter(boardMain);


        Scene scene = new Scene(total);
        return scene;
    }

    public double getFaithMarkerHeight(){
        switch(playerBoard.getFaithMarker()){
            case 0:
            case 1:
            case 2:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return 157;
            case 3:
            case 10:
            case 17:
                return 102;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24: return 47;
            default: return 0;
        }
    }

    public double getFaithMarkerWidht(){
        switch(playerBoard.getFaithMarker()){
            case 0: return 35;
            case 1: return 87;
            case 2:
            case 3:
            case 4: return 137;

            case 5: return 187;
            case 6: return 235;
            case 7: return 283;
            case 8: return 331;
            case 9:
            case 10:
            case 11: return 379;
            case 12: return 427;
            case 13: return 478;
            case 14: return 528;
            case 15: return 578;
            case 16:
            case 17:
            case 18: return 626;
            case 19: return 674;
            case 20: return 724;
            case 21: return 772;
            case 22: return 821;
            case 23: return 870;
            case 24: return 920;
            default: return 0;
        }
    }

}
