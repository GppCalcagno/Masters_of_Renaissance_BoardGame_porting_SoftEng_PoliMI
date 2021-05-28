package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //stage.setMaximized(true);

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
        Scene scene = new Scene(grid);
        //stage.setMaximized(true);

        return scene;
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
        Scene scene = new Scene(grid);
        //stage.sizeToScene();
        //stage.setMaximized(true);
        return scene;
    }

    public Scene mainboard(){

        //layout comandi bottoni
        TilePane tilePane = new TilePane();
        tilePane.setVgap(3);
        tilePane.setHgap(2);
        tilePane.setPrefColumns(2);

        //bottoni azione
        Button actionMarketTray = new Button("Extraction\nMarble");
        Button actionBuyDevCard = new Button("Buy DevCard");
        Button actionActiveProduction = new Button("Active\nProduction");
        actionActiveProduction.setOnAction(e->{
            activeProductions();
        });
        //bottoni show
        Button  showMarketTray = new Button("Show\nMarket Tray");
        Button showDevDeck = new Button("Show\nDevDeck");
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setOnAction(e->{
            controller.sendMessage(new MessageEndTurn(playerBoard.getNickname()));
        });

        tilePane.getChildren().addAll(actionMarketTray, actionActiveProduction, actionBuyDevCard, showDevDeck, showMarketTray, endTurnButton);

        //layout leadercard
        VBox leadercard = new VBox();
        Image leaderCard1;
        Image leaderCard2;

        if(playerBoard.getLeaderCards().get(0)!=null){
            leaderCard1 = new Image("front/"+playerBoard.getLeaderCards().get(0)+".png");
        }
        else leaderCard1 = new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-49-1.png");

        ImageView leaderCard1View = new ImageView(leaderCard1);
        leaderCard1View.setFitHeight(210);
        leaderCard1View.setFitWidth(170);

        if(playerBoard.getLeaderCards().get(1)!=null) {
            leaderCard2 = new Image("front/"+playerBoard.getLeaderCards().get(1)+".png");
        }
        else leaderCard2 = new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-49-1.png");
        ImageView leaderCard2View = new ImageView(leaderCard2);
        leaderCard2View.setFitHeight(210);
        leaderCard2View.setFitWidth(170);

        leadercard.getChildren().addAll(leaderCard1View, leaderCard2View);

        //################################################################################################################
        //layout for boardMain
        Pane boardMain = new Pane();

        Image board = new Image("board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");

        ImageView boardView = new ImageView(board);
        boardView.setFitHeight(550);
        boardView.setFitWidth(750);
        //boardView.fitHeightProperty().bind(stage.heightProperty());
        //boardView.fitWidthProperty().bind(stage.widthProperty());


        boardMain.getChildren().add(boardView);

        //move the faithmarker
        Image faithMarker = new Image("punchboard/faithMarker.png");
        ImageView faithMarkerView = new ImageView(faithMarker);
        faithMarkerView.maxHeight(45);
        faithMarkerView.maxWidth(50);
        /*faithMarkerView.fitWidthProperty().bind(boardMain.widthProperty());
        faithMarkerView.fitHeightProperty().bind(boardMain.heightProperty());*/
        faithMarkerView.setFitHeight(45);
        faithMarkerView.setFitWidth(50);
        faithMarkerView.setPreserveRatio(true);


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


        //if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[0])
            boardMain.getChildren().add(popeSpaceView1);
        //if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[1])
            boardMain.getChildren().add(popeSpaceView2);
        //if(playerBoard.getPlayersPopFavoriteTile().get(playerBoard.getCurrentPlayer())[2])
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
        //AnchorPane button = new AnchorPane(boardView, faithMarkerView, popeSpaceView1, popeSpaceView2, popeSpaceView3 ,row1, row2, row3, confirmExchange);

        /*AnchorPane.setTopAnchor(row1, 350.0);
        AnchorPane.setLeftAnchor(row1, 40.0);*/
        //button.computeAreaInScreen();
        //boardView.fitHeightProperty().bind(boardMain.heightProperty());

        boardMain.getChildren().addAll(row1, row2, row3, confirmExchange);

        confirmExchange.setOnAction(e->{
            if(row1.isSelected() && row2.isSelected() && !row3.isSelected())
            controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), 0, 1));
            else if(row1.isSelected() && !row2.isSelected() && row3.isSelected())
                controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), 0, 2));
            else if(!row1.isSelected() && row2.isSelected() && row3.isSelected())
                controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), 1, 2));
        });

        //###############################################################################################################

        //layout generale
        VBox left = new VBox();
        left.getChildren().addAll(tilePane, leadercard);

        BorderPane total =new BorderPane();
        total.setLeft(left);
        total.setCenter(boardMain);

        total.prefWidthProperty().bind(total.widthProperty());
        total.prefHeightProperty().bind(total.heightProperty());

       // total.minWidth(tilePane.getWidth()+1000);
        //total.minHeight(800);

        Scene scene = new Scene(total, 918, 550 );
        //(tilePane.getWidth() + 1000);
        //stage.setMaximized(true);


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
            case 0: return 40;
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


    public Scene chooseInitialLeaderCards() {
        HBox box1 = new HBox();
        ImageView[] leaderCardImgView = new ImageView[4];
        int i = 0;

        for (String leaderCard : playerBoard.getLeaderCards()) {
            Image leaderCardImage = new Image("front/" + leaderCard + ".png");
            leaderCardImgView[i] = new ImageView(leaderCardImage);
            leaderCardImgView[i].setX(50 + i*10);
            leaderCardImgView[i].setY(20);
            leaderCardImgView[i].setFitHeight(200);
            leaderCardImgView[i].setFitWidth(200);
            leaderCardImgView[i].setPreserveRatio(true);
            i++;
        }
        box1.getChildren().addAll(leaderCardImgView);

        HBox box2 = new HBox();
        int[] chosenLeaderCards = new int[2];

        Label labelLeaderCard1 = new Label("First Leader card: ");
        ChoiceBox cb1 = new ChoiceBox(FXCollections.observableArrayList(
                "1", "2", "3", "4")
        );

        Label labelLeaderCard2 = new Label("Second Leader card: ");
        ChoiceBox cb2 = new ChoiceBox(FXCollections.observableArrayList(
                "1", "2", "3", "4")
        );
        cb1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                    Number old_value, Number new_val) -> {
                        chosenLeaderCards[0] = new_val.intValue();
                });

        cb2.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    chosenLeaderCards[1] = new_val.intValue();
                });
        Button enterCards = new Button("Enter");
        enterCards.setOnAction(e->{
            controller.sendMessage(new MessageChooseLeaderCards(playerBoard.getNickname(), chosenLeaderCards[0], chosenLeaderCards[1]));
        });
        box2.getChildren().addAll(labelLeaderCard1, cb1, labelLeaderCard2, cb2, enterCards);

        BorderPane total =new BorderPane();
        total.setTop(box1);
        total.setCenter(box2);
        return new Scene(total);
    }

    public Scene chooseInitialResources(){
        HBox boxResources = new HBox();
        List<ImageView> resourcesView = new ArrayList<>();
        String[] resourcePath = {"coin.png", "servant.png", "shield.png", "stone.png"};
        List<String> resourcesToSend = new ArrayList<>();

        for (String r : resourcePath){
            ImageView resourceView = new ImageView(new Image("punchboard/" + r));
            resourceView.setFitWidth(100);
            resourceView.setFitHeight(100);
            resourcesView.add(resourceView);
        }

        boxResources.getChildren().addAll(resourcesView);

        HBox boxChoice = new HBox();
        String[] resourcesVett = {"Coins", "Servants", "Shields", "Stones"};

        Label labelResources1 = new Label("First Resource: ");
        ChoiceBox cb1 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );

        Label labelResources2 = new Label("Second Resource: ");
        ChoiceBox cb2 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        cb1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    resourcesToSend.add(resourcesVett[new_val.intValue()]);
                });

        cb2.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    resourcesToSend.add(resourcesVett[new_val.intValue()]);
                });
        Button enterCards = new Button("Enter");
        enterCards.setOnAction(e->{
            controller.sendMessage(new MessageChooseResourcesFirstTurn(playerBoard.getNickname(), resourcesToSend));
        });
        if (playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 1 || playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 2)
            boxChoice.getChildren().addAll(labelResources1, cb1, enterCards);
        else if (playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 4)
            boxChoice.getChildren().addAll(labelResources1, cb1, labelResources2, cb2, enterCards);

        BorderPane total =new BorderPane();
        total.setTop(boxResources);
        total.setCenter(boxChoice);
        return new Scene(total, 1000, 1000);
    }

    public Scene showMessage(String message) {
        TextFlow textFlow = new TextFlow();
        Text text = new Text(message);
        textFlow.getChildren().addAll(text);
        Group group = new Group(textFlow);
        return new Scene(group, 650, 150);
    }

    public void showErrorMessage(String errorMessage) {
        Stage stage1 = new Stage();
        TextFlow textFlow = new TextFlow();
        Text text = new Text(errorMessage);
        textFlow.getChildren().addAll(text);
        Group group = new Group(textFlow);
        stage1.setTitle("Error");
        stage1.setScene(new Scene(group, 650, 150));
        stage1.show();
    }

    public void activeProductions() {
        Stage stage1 = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label labelChoice = new Label("Choose the type of production: ");
        GridPane.setConstraints(labelChoice, 0, 0);

        Button baseProductionButton = new Button("Base\nProduction");
        GridPane.setConstraints(baseProductionButton, 0, 1);
        baseProductionButton.setOnAction(e->{
            baseProductionScene(stage1);
        });
        Button devCardProductionButton = new Button("Development Card\nProduction");
        /*
        devCardProductionButton.setOnAction(e->{

        });

         */
        GridPane.setConstraints(devCardProductionButton, 1, 1);
        Button leaderCardProductionButton = new Button("Leader Card\nProduction");
        /*
        leaderCardProductionButton.setOnAction(e->{

        });

         */
        GridPane.setConstraints(leaderCardProductionButton, 2, 1);
        grid.getChildren().addAll(labelChoice, baseProductionButton, devCardProductionButton, leaderCardProductionButton);
        Scene scene = new Scene(grid, 1000, 1000);
        stage1.setScene(scene);
        stage1.setTitle("Active Production");
        stage1.setMaximized(true);
        stage1.show();
    }

    public void baseProductionScene(Stage stage1) {
        char[] chosenStructures = new char[2];
        String[] resourcesVett = {"Coins", "Servants", "Shields", "Stones"};
        String[] chosenResources = new String[3];

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label explanationLabel1 = new Label("Choose from which do you want to take Resources: ");
        GridPane.setConstraints(explanationLabel1, 0, 0);

        ChoiceBox cbStructure1 = new ChoiceBox(FXCollections.observableArrayList(
                "Warehouse", "Strongbox", "Extra chest")
        );
        GridPane.setConstraints(cbStructure1, 0, 3);
        cbStructure1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    switch (new_val.intValue()) {
                        case 0 :
                            chosenStructures[0] = 'W';
                            break;
                        case 1 :
                            chosenStructures[0] = 'S';
                            break;
                        case 2 :
                            chosenStructures[0] = 'E';
                            break;
                        default:
                            break;
                    }
                });

        ChoiceBox cbResources1 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        GridPane.setConstraints(cbResources1, 1, 3);
        cbResources1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    chosenResources[0] = resourcesVett[new_val.intValue()];
                });

        ChoiceBox cbStructure2 = new ChoiceBox(FXCollections.observableArrayList(
                "Warehouse", "Strongbox", "Extra chest")
        );
        GridPane.setConstraints(cbStructure2, 0, 5);
        cbStructure2.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    switch (new_val.intValue()) {
                        case 0 :
                            chosenStructures[1] = 'W';
                            break;
                        case 1 :
                            chosenStructures[1] = 'S';
                            break;
                        case 2 :
                            chosenStructures[1] = 'E';
                            break;
                        default:
                            break;
                    }
                });

        ChoiceBox cbResources2 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        GridPane.setConstraints(cbResources2, 1, 5);
        cbResources1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    chosenResources[1] = resourcesVett[new_val.intValue()];
                });

        Label explanationLabel2 = new Label("Choose the produced Resources: ");
        GridPane.setConstraints(explanationLabel2, 0, 7);

        ChoiceBox cbResources3 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        GridPane.setConstraints(cbResources3, 0, 9);
        cbResources3.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    chosenResources[2] = resourcesVett[new_val.intValue()];
                });

        Button enterButton = new Button("Enter");
        GridPane.setConstraints(enterButton, 0, 11);
        enterButton.setOnAction(e->{
            controller.sendMessage(new MessageActiveBaseProduction(playerBoard.getNickname(), chosenResources[2], chosenStructures[0], chosenResources[0], chosenStructures[1], chosenResources[1]));
        });
        grid.getChildren().addAll(explanationLabel1, cbStructure1, cbResources1, cbStructure2, cbResources2, explanationLabel2, cbResources3, enterButton);

        Scene scene = new Scene(grid, 1000, 1000);
        stage1.setScene(scene);
        stage1.setMaximized(true);
        stage1.show();
    }

    public Scene payResourcesScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label labelWarehouse = new Label("Warehouse: ");
        GridPane.setConstraints(labelWarehouse, 0, 0);

        Label labelCoinW = new Label("Coins: ");
        GridPane.setConstraints(labelCoinW, 0, 1);
        TextField numCoinsWarehouse = new TextField();
        GridPane.setConstraints(numCoinsWarehouse, 1, 1);
        Label labelServantW = new Label("Servants: ");
        GridPane.setConstraints(labelServantW, 2, 1);
        TextField numServantsWarehouse = new TextField();
        GridPane.setConstraints(numServantsWarehouse, 3, 1);
        Label labelShieldW = new Label("Shields: ");
        GridPane.setConstraints(labelShieldW, 4, 1);
        TextField numShieldsWarehouse = new TextField();
        GridPane.setConstraints(numShieldsWarehouse, 5, 1);
        Label labelStonesW = new Label("Stones: ");
        GridPane.setConstraints(labelStonesW, 6, 1);
        TextField numStonesWarehouse = new TextField();
        GridPane.setConstraints(numStonesWarehouse, 7, 1);

        Label labelStrongbox = new Label("Strongbox: ");
        GridPane.setConstraints(labelStrongbox, 0, 2);

        Label labelCoinS = new Label("Coins: ");
        GridPane.setConstraints(labelCoinS, 0, 3);
        TextField numCoinsStrongbox = new TextField();
        GridPane.setConstraints(numCoinsStrongbox, 1, 3);
        Label labelServantS = new Label("Servants: ");
        GridPane.setConstraints(labelServantS, 2, 3);
        TextField numServantsStrongbox = new TextField();
        GridPane.setConstraints(numServantsStrongbox, 3, 3);
        Label labelShieldS = new Label("Shields: ");
        GridPane.setConstraints(labelShieldS, 4, 3);
        TextField numShieldsStrongbox = new TextField();
        GridPane.setConstraints(numShieldsStrongbox, 5, 3);
        Label labelStonesS = new Label("Stones: ");
        GridPane.setConstraints(labelStonesS, 6, 3);
        TextField numStonesStrongbox = new TextField();
        GridPane.setConstraints(numStonesStrongbox, 7, 3);

        Label labelExtraChest = new Label("ExtraChest: ");
        GridPane.setConstraints(labelExtraChest, 0, 4);

        Label labelCoinE = new Label("Coins: ");
        GridPane.setConstraints(labelCoinE, 0, 5);
        TextField numCoinsExtraChest = new TextField();
        GridPane.setConstraints(numCoinsExtraChest, 1, 5);
        Label labelServantE = new Label("Servants: ");
        GridPane.setConstraints(labelServantE, 2, 5);
        TextField numServantsExtraChest = new TextField();
        GridPane.setConstraints(numServantsExtraChest, 3, 5);
        Label labelShieldE = new Label("Shields: ");
        GridPane.setConstraints(labelShieldE, 4, 5);
        TextField numShieldsExtraChest = new TextField();
        GridPane.setConstraints(numShieldsExtraChest, 5, 5);
        Label labelStonesE = new Label("Stones: ");
        GridPane.setConstraints(labelStonesE, 6, 5);
        TextField numStonesExtraChest = new TextField();
        GridPane.setConstraints(numStonesExtraChest, 7, 5);

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(e->{
            Map<String, Integer> warehouseMap = new HashMap<>();
            Map<String, Integer> strongboxMap = new HashMap<>();
            Map<String, Integer> extraChestMap = new HashMap<>();
            if (numCoinsWarehouse.getText() != null)
                warehouseMap.put("Coins", Integer.parseInt(numCoinsWarehouse.getText()));
            if (numServantsWarehouse.getText() != null)
                warehouseMap.put("Servants", Integer.parseInt(numServantsWarehouse.getText()));
            if (numShieldsWarehouse.getText() != null)
                warehouseMap.put("Shields", Integer.parseInt(numShieldsWarehouse.getText()));
            if (numStonesWarehouse.getText() != null)
                warehouseMap.put("Stones", Integer.parseInt(numStonesWarehouse.getText()));
            if (numCoinsStrongbox.getText() != null)
                strongboxMap.put("Coins", Integer.parseInt(numCoinsStrongbox.getText()));
            if (numServantsStrongbox.getText() != null)
                strongboxMap.put("Servants", Integer.parseInt(numServantsStrongbox.getText()));
            if (numShieldsStrongbox.getText() != null)
                strongboxMap.put("Shields", Integer.parseInt(numShieldsStrongbox.getText()));
            if (numStonesStrongbox.getText() != null)
                strongboxMap.put("Stones", Integer.parseInt(numStonesStrongbox.getText()));
            if (numCoinsExtraChest.getText() != null)
                extraChestMap.put("Coins", Integer.parseInt(numCoinsExtraChest.getText()));
            if (numServantsExtraChest.getText() != null)
                extraChestMap.put("Servants", Integer.parseInt(numServantsExtraChest.getText()));
            if (numShieldsExtraChest.getText() != null)
                extraChestMap.put("Shields", Integer.parseInt(numShieldsExtraChest.getText()));
            if (numStonesExtraChest.getText() != null)
                extraChestMap.put("Stones", Integer.parseInt(numStonesExtraChest.getText()));
            controller.sendMessage(new MessagePayResources(playerBoard.getNickname(), warehouseMap, strongboxMap, extraChestMap));
        });
        GridPane.setConstraints(enterButton, 0, 6);
        grid.getChildren().addAll(labelWarehouse, labelCoinW, numCoinsWarehouse, labelServantW, numServantsWarehouse,
                labelShieldW, numShieldsWarehouse, labelStonesW, numStonesWarehouse, labelStrongbox,
                labelCoinS, numCoinsStrongbox, labelServantS, numServantsStrongbox, labelShieldS, numShieldsStrongbox,
                labelStonesS, numStonesStrongbox, labelExtraChest, labelCoinE, numCoinsExtraChest, labelServantE,
                numServantsExtraChest, labelShieldE, numShieldsExtraChest, labelStonesE, numStonesExtraChest, enterButton
        );
        return new Scene(grid, 1000, 1000);
    }
}
