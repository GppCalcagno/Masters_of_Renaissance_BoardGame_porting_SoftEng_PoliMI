package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.*;
import java.util.stream.Collectors;

public class SceneLauncher {
    private ClientController controller;
    private PlayerBoard playerBoard;
    private Stage stage;
    private Stage activeBuyDevCardStage;
    private Stage payResourcesStage;
    private Stage otherPlayerStage;
    private Stage extractionMarbleStage;
    private Stage manageMarbleStage;
    private Stage productionsStage;

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

        Image maestri = new Image("punchboard/logoMaestri.png");
        ImageView maestriView = new ImageView(maestri);

        Pane grid = new Pane();

        maestriView.setLayoutX(290);
        maestriView.setLayoutY(100);
        maestriView.setFitHeight(120);
        maestriView.setFitWidth(340);

        grid.getChildren().add(maestriView);

        Label address = new Label("Server address: ");
        address.setFont(new Font("Arial", 24));
        address.setTextFill(Color.BLACK);
        address.setLayoutX(240);
        address.setLayoutY(300);


        TextField addressIn = new TextField("127.0.0.1");
        addressIn.setLayoutX(430);
        addressIn.setLayoutY(300);

        Label port = new Label("Port: ");
        port.setFont(new Font("Arial", 24));
        port.setTextFill(Color.BLACK);
        port.setLayoutX(240);
        port.setLayoutY(350);

        TextField portIn = new TextField("1234");
        portIn.setLayoutX(430);
        portIn.setLayoutY(350);

        Button loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", 24));
        loginButton.setTextFill(Color.BLACK);
        loginButton.setLayoutX(400);
        loginButton.setLayoutY(400);

        loginButton.setOnAction(e->{

            Thread guiThread= new Thread(()->controller.connect(addressIn.getText(), Integer.parseInt(portIn.getText())));
            guiThread.start();
            });

        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.getChildren().addAll(address, addressIn, port, portIn, loginButton);

        return new Scene(grid, 897, 550);
    }

    public Scene askLogin(){

        Image maestri = new Image("punchboard/logoMaestri.png");
        ImageView maestriView = new ImageView(maestri);

        Pane grid = new Pane();



        maestriView.setLayoutX(290);
        maestriView.setLayoutY(100);
        maestriView.setFitHeight(120);
        maestriView.setFitWidth(340);

        grid.getChildren().add(maestriView);

        Label nickLabel = new Label("Nickname: ");
        nickLabel.setFont(new Font("Arial", 24));
        nickLabel.setTextFill(Color.BLACK);
        nickLabel.setLayoutX(300);
        nickLabel.setLayoutY(300);

        TextField nickname = new TextField();
        nickname.setLayoutX(430);
        nickname.setLayoutY(300);

        Button nickButton = new Button("Play");
        nickButton.setFont(new Font("Arial", 26));
        nickButton.setTextFill(Color.BLACK);
        nickButton.setLayoutX(400);
        nickButton.setLayoutY(360);

        nickButton.setOnAction(e->{
            playerBoard.setNickname(nickname.getText());
            controller.sendMessage(new MessageLogin(nickname.getText()));
        });

        grid.getChildren().addAll(nickLabel, nickname, nickButton);
        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(grid, 897, 550);
    }

    public Scene askNumPlayer() {

        Image maestri = new Image("punchboard/logoMaestri.png");
        ImageView maestriView = new ImageView(maestri);

        Pane grid = new Pane();

        maestriView.setLayoutX(290);
        maestriView.setLayoutY(100);
        maestriView.setFitHeight(120);
        maestriView.setFitWidth(340);

        grid.getChildren().add(maestriView);

        Label numPlayersLabel = new Label("Number of players: ");
        numPlayersLabel.setFont(new Font("Arial", 30));
        numPlayersLabel.setTextFill(Color.BLACK);
        numPlayersLabel.setLayoutX(190);
        numPlayersLabel.setLayoutY(300);

        TextField numPlayersField = new TextField();
        numPlayersField.setLayoutX(460);
        numPlayersField.setLayoutY(310);

        Button numButton = new Button("Play");
        numButton.setFont(new Font("Arial", 24));
        numButton.setTextFill(Color.BLACK);
        numButton.setLayoutX(400);
        numButton.setLayoutY(360);
        numButton.setOnAction(e->{
            try{
                controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), Integer.parseInt(numPlayersField.getText())));
            }catch (NumberFormatException ignore){}
        });

        grid.getChildren().addAll(numPlayersLabel, numPlayersField, numButton);
        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        return new Scene(grid, 897, 550);
    }



    public Scene mainboard(){

        Pane left = new Pane();
        left.setPrefWidth(150);
        left.getChildren().addAll(buttons());
        left.getChildren().addAll(leaderCardBoard(playerBoard.getLeaderCards()));
        //EXTRACHEST
        left.getChildren().addAll(extraChest(playerBoard.getExtrachest(), playerBoard.getLeaderCards()));
        //BUTTON ACTIVE DISCARD LEADERCARDS
        //left.getChildren().addAll(prove());

        Image imageBoardMain = new Image("board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
        ImageView imageBoardView = new ImageView(imageBoardMain);
        imageBoardView.setFitWidth(747);
        imageBoardView.setFitHeight(550);

        Pane boardMain = new Pane();
        boardMain.getChildren().addAll(imageBoardView);
        boardMain.getChildren().addAll(faithMArkerBoard(playerBoard.getNickname()));

        //POPE SPACE
        boardMain.getChildren().addAll(popeSpace(playerBoard.getPlayersPopFavoriteTile(), playerBoard.getNickname()));


        //WAREHOUSE
        boardMain.getChildren().addAll(exchangeButton());
        if(playerBoard.getWarehouse()[0][0]!=null){
            Image resource = new Image("punchboard/" + playerBoard.getWarehouse()[0][0] + ".png");
            ImageView resourceView = new ImageView(resource);
            resourceView.setFitWidth(30);
            resourceView.setFitHeight(30);
            resourceView.setLayoutX(83);
            resourceView.setLayoutY(235);
            boardMain.getChildren().add(resourceView);
        }

        if(playerBoard.getWarehouse()[1][0]!=null){
            Image resource1 = new Image("punchboard/"+playerBoard.getWarehouse()[1][0]+".png");
            ImageView resource1View = new ImageView(resource1);
            resource1View.setFitWidth(30);
            resource1View.setFitHeight(30);
            resource1View.setLayoutX(66);
            resource1View.setLayoutY(285);
            boardMain.getChildren().add(resource1View);
        }

        if(playerBoard.getWarehouse()[1][1]!=null){
            Image resource2 = new Image("punchboard/"+playerBoard.getWarehouse()[1][1]+".png");
            ImageView resource2View = new ImageView(resource2);
            resource2View.setFitWidth(30);
            resource2View.setFitHeight(30);
            resource2View.setLayoutX(99);
            resource2View.setLayoutY(285);
            boardMain.getChildren().add(resource2View);
        }

        if(playerBoard.getWarehouse()[2][0]!=null){
            Image resource3 = new Image("punchboard/"+playerBoard.getWarehouse()[2][0]+".png");
            ImageView resource3View = new ImageView(resource3);
            resource3View.setFitWidth(30);
            resource3View.setFitHeight(30);
            resource3View.setLayoutX(50);
            resource3View.setLayoutY(335);
            boardMain.getChildren().add(resource3View);
        }

        if(playerBoard.getWarehouse()[2][1]!=null){
            Image resource4 = new Image("punchboard/"+playerBoard.getWarehouse()[2][1]+".png");
            ImageView resource4View = new ImageView(resource4);
            resource4View.setFitWidth(30);
            resource4View.setFitHeight(30);
            resource4View.setLayoutX(81);
            resource4View.setLayoutY(335);
            boardMain.getChildren().add(resource4View);
        }

        if(playerBoard.getWarehouse()[2][2]!=null){
            Image resource5 = new Image("punchboard/"+playerBoard.getWarehouse()[2][2]+".png");
            ImageView resource5View = new ImageView(resource5);
            resource5View.setFitWidth(30);
            resource5View.setFitHeight(30);
            resource5View.setLayoutX(112);
            resource5View.setLayoutY(335);
            boardMain.getChildren().add(resource5View);
        }

        //STRONGBOX
        boardMain.getChildren().addAll(strongboxPane(playerBoard.getStrongbox()));
        //DEVCARD
        boardMain.getChildren().addAll(devCardBoard(playerBoard.getSlotDevCard()));

        //ENDTURN
        boardMain.getChildren().add(endTurn());


        Button cheat = new Button("cheat");
        if(!playerBoard.isMyturn()) cheat.setDisable(true);
        else cheat.setDisable(false);
        cheat.setOnAction(e-> controller.sendMessage(new MessageFake(playerBoard.getNickname())));

        boardMain.getChildren().add(cheat);

        //CALAMAIO
        Image inkwell = new Image("punchboard/calamaio.png");
        ImageView inkwellview = new ImageView(inkwell);
        inkwellview.setFitHeight(60);
        inkwellview.setFitWidth(60);
        inkwellview.setLayoutY(100);
        inkwellview.setLayoutX(660);

        if(playerBoard.getPlayerList().get(0).equals(playerBoard.getNickname())) boardMain.getChildren().add(inkwellview);

        HBox allBoard = new HBox();
        allBoard.getChildren().addAll(left, boardMain);

        return new Scene(allBoard);
    }

    public Node[] exchangeButton(){
        Node[] exchange = new Node[4];

        //to not let select more of 2 checkboxes
        final int maxCount = 2;
        List<CheckBox> exchangeCheck = new ArrayList<>();
        List<CheckBox> checkBoxList = new ArrayList<>();

        ChangeListener<Boolean> listener = new ChangeListener<>() {

            private int activeCount = 0;

            public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    activeCount++;
                    for (CheckBox cb : checkBoxList) {
                        if (cb.isSelected()) {
                            if (!exchangeCheck.contains(cb))
                                exchangeCheck.add(cb);
                        }
                    }
                    if (activeCount == maxCount) {
                        // disable unselected CheckBoxes
                        for (CheckBox cb : checkBoxList) {
                            if (!cb.isSelected()) {
                                cb.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (activeCount == maxCount) {
                        for (CheckBox cb : checkBoxList) {
                            if (!cb.isDisable() && !cb.isSelected())
                                exchangeCheck.remove(cb);
                        }
                        // re-enable CheckBoxes
                        for (CheckBox cb : checkBoxList) {
                            cb.setDisable(false);
                        }
                    }
                    activeCount--;
                }
            }
        };

        CheckBox row1 = new CheckBox();
        checkBoxList.add(row1);
        row1.selectedProperty().addListener(listener);
        CheckBox row2 = new CheckBox();
        checkBoxList.add(row2);
        row2.selectedProperty().addListener(listener);
        CheckBox row3 = new CheckBox();
        checkBoxList.add(row3);
        row3.selectedProperty().addListener(listener);

        Button exchangeButton = new Button("Exchange");
        exchangeButton.setOnAction(e->{
            if (exchangeCheck.size() > 1) {
                int index1 = checkBoxList.indexOf(exchangeCheck.get(0));
                int index2 = checkBoxList.indexOf(exchangeCheck.get(1));
                controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), index1, index2));
            }
        });

        exchange[0] = row1;
        exchange[0].setLayoutY(242);
        exchange[0].setLayoutX(17);
        exchange[1] = row2;
        exchange[1].setLayoutY(288);
        exchange[1].setLayoutX(17);
        exchange[2] = row3;
        exchange[2].setLayoutY(337);
        exchange[2].setLayoutX(17);
        exchange[3] = exchangeButton;
        exchange[3].setLayoutY(370);
        exchange[3].setLayoutX(17);

        if(playerBoard.isMyturn()){
            for(int i=0; i<4; i++) exchange[i].setDisable(false);
        }
        else for(int i=0; i<4; i++) exchange[i].setDisable(true);

        return exchange;
    }

    public Node[] popeSpace(Map<String, boolean[]> popeSpace, String name){

        Node[] popespace = new Node[3];

        boolean flag1=false;
        boolean flag2=false;
        boolean flag3=false;

        Image popeSpace1 = new Image("punchboard/quadrato giallo.png");
        ImageView popeSpace1View = new ImageView(popeSpace1);
        popeSpace1View.setFitHeight(60);
        popeSpace1View.setFitWidth(60);
        popeSpace1View.setLayoutX(179);
        popeSpace1View.setLayoutY(79);
        if(playerBoard.getPlayerList().size()>1){
            for(String player : playerBoard.getPlayerList()) if(popeSpace.get(player)[0]) flag1 = true;
            if(flag1){
                if(popeSpace.get(name)[0]){
                    popeSpace1 = new Image("punchboard/pope_favor1_front.png");
                    popeSpace1View.setImage(popeSpace1);
                    popeSpace1View.setOpacity(1.0);
                }
                else popeSpace1View.setOpacity(1.0);
            }
            else popeSpace1View.setOpacity(0.0);
        }
        else{
            if(playerBoard.getBlackCrossToken()>=8){
                if(popeSpace.get(name)[0]){
                    popeSpace1 = new Image("punchboard/pope_favor1_front.png");
                    popeSpace1View.setImage(popeSpace1);
                    popeSpace1View.setOpacity(1.0);
                }
                else popeSpace1View.setOpacity(1.0);
            }
            else popeSpace1View.setOpacity(0.0);
        }



        Image popeSpace2 = new Image("punchboard/quadrato arancione.png");
        ImageView popeSpace2View = new ImageView(popeSpace2);
        popeSpace2View.setFitHeight(60);
        popeSpace2View.setFitWidth(60);
        popeSpace2View.setLayoutX(363);
        popeSpace2View.setLayoutY(35);
        if(playerBoard.getPlayerList().size()>1){
            for(String player : playerBoard.getPlayerList()) if(popeSpace.get(player)[1]) flag2 = true;
            if(flag2){
                if(popeSpace.get(name)[1]){
                    popeSpace2 = new Image("punchboard/pope_favor2_front.png");
                    popeSpace2View.setImage(popeSpace2);
                    popeSpace2View.setOpacity(1.0);
                }
                else popeSpace2View.setOpacity(1.0);
            }
            else popeSpace2View.setOpacity(0.0);
        }
        else{
            if(playerBoard.getBlackCrossToken()>=16){
                if(popeSpace.get(name)[1]){
                    popeSpace2 = new Image("punchboard/pope_favor2_front.png");
                    popeSpace2View.setImage(popeSpace2);
                    popeSpace2View.setOpacity(1.0);
                }
                else popeSpace2View.setOpacity(1.0);
            }
            else popeSpace2View.setOpacity(0.0);
        }

        Image popeSpace3 = new Image("punchboard/quadrato rosso.png");
        ImageView popeSpace3View = new ImageView(popeSpace3);
        popeSpace3View.setFitHeight(60);
        popeSpace3View.setFitWidth(60);
        popeSpace3View.setLayoutX(582);
        popeSpace3View.setLayoutY(78);
        if(playerBoard.getPlayerList().size()>1){
            for(String player : playerBoard.getPlayerList()) if(popeSpace.get(player)[2]) flag3 = true;
            if(flag3){
                if(popeSpace.get(name)[2]){
                    popeSpace3 = new Image("punchboard/pope_favor3_front.png");
                    popeSpace3View.setImage(popeSpace3);
                    popeSpace3View.setOpacity(1.0);
                }
                else popeSpace3View.setOpacity(1.0);
            }
            else popeSpace3View.setOpacity(0.0);
        }
        else{
            if(playerBoard.getBlackCrossToken()>=24){
                if(popeSpace.get(name)[2]){
                    popeSpace3 = new Image("punchboard/pope_favor3_front.png");
                    popeSpace3View.setImage(popeSpace3);
                    popeSpace3View.setOpacity(1.0);
                }
                else popeSpace3View.setOpacity(1.0);
            }
            else popeSpace3View.setOpacity(0.0);
        }

        popespace[0]=popeSpace1View;
        popespace[1]=popeSpace2View;
        popespace[2]=popeSpace3View;

        return popespace;
    }

    public Node[] strongboxPane(Map<String,Integer> strongbox){
        Node[] resources = new Node[8];

        Image coins = new Image("punchboard/Coins.png");
        ImageView coinsView = new ImageView(coins);
        coinsView.setFitWidth(30);
        coinsView.setFitHeight(30);
        coinsView.setLayoutX(20);
        coinsView.setLayoutY(430);
        resources[0] = coinsView;

        Image shields = new Image("punchboard/Shields.png");
        ImageView shieldsView = new ImageView(shields);
        shieldsView.setFitWidth(30);
        shieldsView.setFitHeight(30);
        shieldsView.setLayoutX(20);
        shieldsView.setLayoutY(470);
        resources[1] = shieldsView;

        Image stones = new Image("punchboard/Stones.png");
        ImageView stonesView = new ImageView(stones);
        stonesView.setFitWidth(30);
        stonesView.setFitHeight(30);
        stonesView.setLayoutX(90);
        stonesView.setLayoutY(430);
        resources[2] = stonesView;

        Image servants = new Image("punchboard/Servants.png");
        ImageView servantsView = new ImageView(servants);
        servantsView.setFitWidth(30);
        servantsView.setFitHeight(30);
        servantsView.setLayoutX(90);
        servantsView.setLayoutY(470);
        resources[3] = servantsView;


        Label numberofCoins = new Label();
        if(strongbox.get("Coins")!=null)
        numberofCoins.setText(String.valueOf(strongbox.get("Coins")));
        else numberofCoins.setText(String.valueOf(0));
        numberofCoins.setFont(new Font("Arial", 20));
        numberofCoins.setTextFill(Color.WHITE);
        numberofCoins.setLayoutX(50);
        numberofCoins.setLayoutY(435);
        resources[4] = numberofCoins;

        Label numberofShields = new Label();
        if(strongbox.get("Shields")!=null)
        numberofShields.setText(String.valueOf(strongbox.get("Shields")));
        else numberofShields.setText(String.valueOf(0));
        numberofShields.setFont(new Font("Arial", 20));
        numberofShields.setTextFill(Color.WHITE);
        numberofShields.setLayoutX(50);
        numberofShields.setLayoutY(475);
        resources[5] = numberofShields;

        Label numberofStones = new Label();
        if(strongbox.get("Stones")!=null)
        numberofStones.setText(String.valueOf(strongbox.get("Stones")));
        else numberofStones.setText(String.valueOf(0));
        numberofStones.setFont(new Font("Arial", 20));
        numberofStones.setTextFill(Color.WHITE);
        numberofStones.setLayoutX(120);
        numberofStones.setLayoutY(435);
        resources[6] = numberofStones;

        Label numberofServants = new Label();
        if(strongbox.get("Servants")!=null)
        numberofServants.setText(String.valueOf(strongbox.get("Servants")));
        else numberofServants.setText(String.valueOf(0));
        numberofServants.setFont(new Font("Arial", 20));
        numberofServants.setTextFill(Color.WHITE);
        numberofServants.setLayoutX(120);
        numberofServants.setLayoutY(475);
        resources[7] = numberofServants;

        return resources;

    }

    public List<Node> devCardBoard(String [][] slotDevCard){
        List<Node> devcards = new ArrayList<>();

        if(slotDevCard[0][0]!=null) {
            Image leadercard1 = new Image("front/"+slotDevCard[0][0]+".png");
            ImageView leadercard1View = new ImageView(leadercard1);
            leadercard1View.setFitHeight(250);
            leadercard1View.setFitWidth(145);
            leadercard1View.setLayoutX(280);
            leadercard1View.setLayoutY(290);
            devcards.add(leadercard1View);
        }
        if(slotDevCard[0][1]!=null) {
            Image leadercard2 = new Image("front/"+slotDevCard[0][1]+".png");
            ImageView leadercard2View = new ImageView(leadercard2);
            leadercard2View.setFitHeight(250);
            leadercard2View.setFitWidth(145);
            leadercard2View.setLayoutX(427);
            leadercard2View.setLayoutY(290);
            devcards.add(leadercard2View);
        }
        if(slotDevCard[0][2]!=null) {
            Image leadercard3 = new Image("front/"+slotDevCard[0][2]+".png");
            ImageView leadercard3View = new ImageView(leadercard3);
            leadercard3View.setFitHeight(250);
            leadercard3View.setFitWidth(145);
            leadercard3View.setLayoutX(574);
            leadercard3View.setLayoutY(290);
            devcards.add(leadercard3View);
        }
        if(slotDevCard[1][0]!=null) {
            Image leadercard4 = new Image("front/"+slotDevCard[1][0]+".png");
            ImageView leadercard4View = new ImageView(leadercard4);
            leadercard4View.setFitHeight(250);
            leadercard4View.setFitWidth(145);
            leadercard4View.setLayoutX(280);
            leadercard4View.setLayoutY(245);
            devcards.add(leadercard4View);
        }
        if(slotDevCard[1][1]!=null) {
            Image leadercard5 = new Image("front/"+slotDevCard[1][1]+".png");
            ImageView leadercard5View = new ImageView(leadercard5);
            leadercard5View.setFitHeight(250);
            leadercard5View.setFitWidth(145);
            leadercard5View.setLayoutX(427);
            leadercard5View.setLayoutY(245);
            devcards.add(leadercard5View);
        }
        if(slotDevCard[1][2]!=null) {
            Image leadercard6 = new Image("front/"+slotDevCard[1][2]+".png");
            ImageView leadercard6View = new ImageView(leadercard6);
            leadercard6View.setFitHeight(250);
            leadercard6View.setFitWidth(145);
            leadercard6View.setLayoutX(574);
            leadercard6View.setLayoutY(245);
            devcards.add(leadercard6View);
        }
        if(slotDevCard[2][0]!=null) {
            Image leadercard7 = new Image("front/"+slotDevCard[2][0]+".png");
            ImageView leadercard7View = new ImageView(leadercard7);
            leadercard7View.setFitHeight(250);
            leadercard7View.setFitWidth(145);
            leadercard7View.setLayoutX(280);
            leadercard7View.setLayoutY(200);
            devcards.add(leadercard7View);
        }
        if(slotDevCard[2][1]!=null) {
            Image leadercard8 = new Image("front/"+slotDevCard[2][1]+".png");
            ImageView leadercard8View = new ImageView(leadercard8);
            leadercard8View.setFitHeight(250);
            leadercard8View.setFitWidth(145);
            leadercard8View.setLayoutX(427);
            leadercard8View.setLayoutY(200);
            devcards.add(leadercard8View);
        }
        if(slotDevCard[2][2]!=null) {
            Image leadercard9 = new Image("front/"+slotDevCard[2][2]+".png");
            ImageView leadercard9View = new ImageView(leadercard9);
            leadercard9View.setFitHeight(250);
            leadercard9View.setFitWidth(145);
            leadercard9View.setLayoutX(427);
            leadercard9View.setLayoutY(200);
            devcards.add(leadercard9View);
        }

        return devcards;

    }

    public Node[] faithMArkerBoard(String name){
        Node[] faithMarker;
        if(playerBoard.getPlayerList().size()==1) faithMarker = new Node[2];
        else faithMarker = new Node[1];

        Image imageFaith = new Image("punchboard/faithMarker.png");
        ImageView imageFaithView = new ImageView(imageFaith);
        imageFaithView.setFitWidth(30);
        imageFaithView.setFitHeight(37);
        faithMarker[0] = imageFaithView;
        faithMarker[0].setLayoutY(getFaithMarkerY(playerBoard.getPlayersFaithMarkerPosition().get(name)));
        faithMarker[0].setLayoutX(getFaithMarkerX(playerBoard.getPlayersFaithMarkerPosition().get(name)));

        if(playerBoard.getPlayerList().size()==1){
            Image imageLorenzo = new Image("punchboard/croce.png");
            ImageView imageLorenzoView = new ImageView(imageLorenzo);
            imageLorenzoView.setFitWidth(28);
            imageLorenzoView.setFitHeight(35);
            faithMarker[1] = imageLorenzoView;
            faithMarker[1].setLayoutY(getFaithMarkerY(playerBoard.getBlackCrossToken()));
            faithMarker[1].setLayoutX(getFaithMarkerX(playerBoard.getBlackCrossToken())-2);
        }

        return faithMarker;
    }

    public List<Node> leaderCardBoard(List<String> playerLeaderCards){

        List<Node> leaderCards = new ArrayList<>();

        Image[] leadercard = new Image[2];
        leadercard[0] = new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-49-1.png");
        leadercard[1] = new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-49-1.png");

        Button activefirst = new Button("✓");
        Button activesecond = new Button("✓");
        Button discardfirst  = new Button("✗");
        Button discardsecond = new Button("✗");
        for(int i=0; i < playerLeaderCards.size(); i++) {
            if (playerLeaderCards.get(0) != null) {
                leadercard[i] = new Image("front/" + playerLeaderCards.get(i) + ".png");
            }
        }

        ImageView leadercard1View = new ImageView(leadercard[0]);
        leadercard1View.setFitHeight(195);
        leadercard1View.setFitWidth(150);

        ImageView leadercard2View = new ImageView(leadercard[1]);
        leadercard2View.setFitHeight(195);
        leadercard2View.setFitWidth(150);

        leadercard1View.setLayoutY(160);
        leaderCards.add(leadercard1View);
        leadercard2View.setLayoutY(355);
        leaderCards.add(leadercard2View);

        if(!leadercard[0].getUrl().contains("Masters")){
            discardfirst = new Button("✗");
            discardfirst.setFont(new Font("Arial", 7));
            discardfirst.setTextFill(Color.RED);
            discardfirst.setMaxWidth(17);
            discardfirst.setMaxHeight(17);
            discardfirst.setLayoutY(340);
            discardfirst.setLayoutX(133);
            discardfirst.setOnAction(e-> controller.sendMessage(new MessageUpdateStateLeaderActionClient(playerBoard.getNickname(), playerLeaderCards.get(0), 0)));
            leaderCards.add(discardfirst);


                activefirst.setFont(new Font("Arial", 7));
                activefirst.setTextFill(Color.GREEN);
                activefirst.setMaxWidth(17);
                activefirst.setMaxHeight(17);
                activefirst.setLayoutY(340);
                activefirst.setLayoutX(0);
                activefirst.setOnAction(e-> controller.sendMessage(new MessageUpdateStateLeaderActionClient(playerBoard.getNickname(), playerLeaderCards.get(0), 1)));
                leaderCards.add(activefirst);
        }

        if(!leadercard[1].getUrl().contains("Masters")){
            discardsecond.setFont(new Font("Arial", 7));
            discardsecond.setTextFill(Color.RED);
            discardsecond.setMaxWidth(17);
            discardsecond.setMaxHeight(17);
            discardsecond.setLayoutY(532);
            discardsecond.setLayoutX(133);
            discardsecond.setOnAction(e-> controller.sendMessage(new MessageUpdateStateLeaderActionClient(playerBoard.getNickname(), playerLeaderCards.get(1), 0)));
            leaderCards.add(discardsecond);

            activesecond.setFont(new Font("Arial", 7));
            activesecond.setTextFill(Color.GREEN);
            activesecond.setMaxWidth(17);
            activesecond.setMaxHeight(17);
            activesecond.setLayoutY(532);
            activesecond.setLayoutX(0);
            activesecond.setOnAction(e-> controller.sendMessage(new MessageUpdateStateLeaderActionClient(playerBoard.getNickname(), playerLeaderCards.get(1), 1)));
            leaderCards.add(activesecond);
        }

        if(playerBoard.isMyturn()){
            activefirst.setDisable(false);
            activesecond.setDisable(false);
            discardfirst.setDisable(false);
            discardsecond.setDisable(false);
        }
        else{
            activefirst.setDisable(true);
            activesecond.setDisable(true);
            discardfirst.setDisable(true);
            discardsecond.setDisable(true);
        }
        return leaderCards;
    }

    public List<Node> extraChest(Map<String,Integer> playerExtraChest, List<String> playerLeaderCards){
        List<Node> resourceAdded = new ArrayList<>();

        for(String resource : playerExtraChest.keySet()){
            for (int i = 0; i < playerExtraChest.get(resource); i++) {
                ImageView resourceExtraViewSingle = new ImageView(new Image("punchboard/" + resource + ".png"));
                resourceExtraViewSingle.setLayoutY(322 + indexLeaderCardFromResource(resource, playerLeaderCards) * 195);
                resourceExtraViewSingle.setLayoutX(38 + i*57);
                resourceExtraViewSingle.setFitWidth(20);
                resourceExtraViewSingle.setFitHeight(20);
                resourceAdded.add(resourceExtraViewSingle);
            }
        }

        return resourceAdded;
    }

    public int indexLeaderCardFromResource (String resource, List<String> playerLeaderCards) {
        switch (resource) {
            case "Coins" :
                return playerLeaderCards.indexOf("LCCL4");
            case "Stones" :
                return playerLeaderCards.indexOf("LCCL1");
            case "Servants" :
                return playerLeaderCards.indexOf("LCCL2");
            case "Shields" :
                return playerLeaderCards.indexOf("LCCL3");
            default:
                return -1;
        }
    }

    public Node[] buttons(){

        Button marketTray = new Button();
        marketTray.setText("Extract");
        marketTray.setLayoutY(5);
        marketTray.setOnAction(e-> activeExtraction());
        marketTray.setDisable(!playerBoard.isMyturn());

        Button buyDevCard = new Button();
        buyDevCard.setText("Buy DevCard");
        buyDevCard.setLayoutY(31);
        buyDevCard.setOnAction(e-> activeBuyDevCard());
        buyDevCard.setDisable(!playerBoard.isMyturn());

        Button production = new Button();
        production.setText("Production");
        production.setLayoutY(56);
        production.setOnAction(e-> activeProductions());
        production.setDisable(!playerBoard.isMyturn());

        Button showMarket = new Button();
        showMarket.setText("Show market");
        showMarket.setLayoutY(81);
        showMarket.setOnAction(e-> showMarket());

        Button showDevDeck = new Button();
        showDevDeck.setText("Show DevDeck");
        showDevDeck.setLayoutY(106);
        showDevDeck.setOnAction(e-> showDevDeck());

        Button players = new Button();
        if(playerBoard.getPlayerList().size()>1){
            players.setText("Show players");
            players.setOnAction(e-> showOtherPlayers());
            players.setDisable(!playerBoard.isMyturn());
        }
        else{
            players.setText("Lorenzo last turn");
            players.setOnAction(e-> showLorenzoTurn());
        }
        players.setLayoutY(131);


        Button[] buttons = new Button[6];
        buttons[0] = marketTray;
        buttons[1] = showMarket;
        buttons[2] = buyDevCard;
        buttons[3] = showDevDeck;
        buttons[4] = production;
        buttons[5] = players;

        for(Button button : buttons){
            button.setMinWidth(150);
            button.setMinHeight(15);
        }

        return buttons;
    }

    public Node endTurn(){
        Button endTurn = new Button("End Turn");
        endTurn.setLayoutY(523);
        endTurn.setLayoutX(683);
        endTurn.setOnAction(e-> controller.sendMessage(new MessageEndTurn(playerBoard.getNickname())));
        endTurn.setDisable(!playerBoard.isMyturn());

        return endTurn;
    }

    public double getFaithMarkerX(int pos){
        switch (pos){
            case 0: return 30;
            case 1: return 67;
            case 2:
            case 3:
            case 4:
                return 103;
            case 5: return 140;
            case 6: return 177;
            case 7: return 213;
            case 8: return 250;
            case 9:
            case 10:
            case 11:
                return 287;
            case 12: return 324;
            case 13: return 360;
            case 14: return 397;
            case 15: return 434;
            case 16:
            case 17:
            case 18:
                return 470;
            case 19: return 507;
            case 20: return 543;
            case 21: return 580;
            case 22: return 616;
            case 23: return 653;
            case 24: return 689;
            default: return 0;
        }
    }

    public double getFaithMarkerY(int pos){
        switch (pos){
            case 0:
            case 1:
            case 2:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16: return 105;
            case 3:
            case 10:
            case 17: return 68;
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
            case 24: return 31;
            default: return 0;
        }
    }

    public Scene chooseInitialLeaderCards() {
        Pane total = new Pane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label label = new Label("Choose two Leader cards: ");
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.WHITE);
        GridPane.setConstraints(label, 0, 0);
        grid.getChildren().add(label);

        HBox cardsBox = new HBox();
        cardsBox.setSpacing(5);
        cardsBox.setLayoutX(5);
        cardsBox.setLayoutY(40);

        for (String leaderCard : playerBoard.getLeaderCards()) {
            ImageView leaderCardImgView = new ImageView(new Image("front/" + leaderCard + ".png"));
            leaderCardImgView.setFitWidth(160);
            leaderCardImgView.setPreserveRatio(true);
            cardsBox.getChildren().add(leaderCardImgView);
        }
        total.getChildren().add(cardsBox);

        total.getChildren().add(grid);

        //to not let select more of 2 checkboxes
        final int maxCount = 2;
        List<CheckBox> chosenCards = new ArrayList<>();
        List<CheckBox> checkBoxList = new ArrayList<>();

        ChangeListener<Boolean> listener = new ChangeListener<>() {
            private int activeCount = 0;

            public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    activeCount++;
                    for (CheckBox cb : checkBoxList) {
                        if (cb.isSelected()) {
                            if (!chosenCards.contains(cb))
                                chosenCards.add(cb);
                        }
                    }
                    if (activeCount == maxCount) {
                        // disable unselected CheckBoxes
                        for (CheckBox cb : checkBoxList) {
                            if (!cb.isSelected()) {
                                cb.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (activeCount == maxCount) {
                        for (CheckBox cb : checkBoxList) {
                            if (!cb.isDisable() && !cb.isSelected())
                                chosenCards.remove(cb);
                        }
                        // re-enable CheckBoxes
                        for (CheckBox cb : checkBoxList) {
                            cb.setDisable(false);
                        }
                    }
                    activeCount--;
                }
            }
        };

        for (int in = 0; in < 4; in++) {
            CheckBox checkBox = new CheckBox();
            checkBoxList.add(checkBox);
            checkBox.selectedProperty().addListener(listener);
            checkBox.setLayoutX(in*166 + 77);
            checkBox.setLayoutY(292);
            total.getChildren().add(checkBox);
        }

        Button enterCards = new Button("Enter");
        enterCards.setFont(new Font("Arial", 16));
        enterCards.setTextFill(Color.BLACK);
        enterCards.setOnAction(e->{
            if (chosenCards.size() != 2) {
                showErrorMessage("Choose two Leader cards");
            }
            else {
                int i1 = checkBoxList.indexOf(chosenCards.get(0));
                int i2 = checkBoxList.indexOf(chosenCards.get(1));
                controller.sendMessage(new MessageChooseLeaderCards(playerBoard.getNickname(), i1, i2));
            }
        });
        enterCards.setLayoutY(319);
        enterCards.setLayoutX(checkBoxList.get(3).getLayoutX() - 23);
        total.getChildren().add(enterCards);

        total.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(total, 670, 362);
    }

    public Scene chooseInitialResources(){
        Pane pane = new Pane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label label = new Label("Choose 1 resource if you are the second or the third\nplayer or 2 if you are the fourth.");
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.WHITE);
        GridPane.setConstraints(label, 0, 0);
        grid.getChildren().add(label);
        pane.getChildren().add(grid);

        String[] resources = {"Coins", "Servants", "Shields", "Stones"};

        int i = 0;

        for (String r : resources){
            ImageView resourceView = new ImageView(new Image("punchboard/" + r + ".png"));
            resourceView.setFitWidth(60);
            resourceView.setPreserveRatio(true);
            resourceView.setX(i*90 + 35);
            resourceView.setY(55);
            pane.getChildren().add(resourceView);
            i = i + 1;
        }

        int maxCount = 0;
        if (playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 1 || playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 2)
            maxCount = 1;
        else if (playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 3)
            maxCount = 2;

        List<CheckBox> chosenResources = new ArrayList<>();
        List<CheckBox> checkBoxList = new ArrayList<>();

        int finalMaxCount = maxCount;

        ChangeListener<Boolean> listener = new ChangeListener<>() {
            private int activeCount = 0;

            public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    activeCount++;
                    for (CheckBox cb : checkBoxList) {
                        if (cb.isSelected()) {
                            if (!chosenResources.contains(cb))
                                chosenResources.add(cb);
                        }
                    }
                    if (activeCount == finalMaxCount) {
                        // disable unselected CheckBoxes
                        for (CheckBox cb : checkBoxList) {
                            if (!cb.isSelected()) {
                                cb.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (activeCount == finalMaxCount) {
                        for (CheckBox cb : checkBoxList) {
                            if (!cb.isDisable() && !cb.isSelected())
                                chosenResources.remove(cb);
                        }
                        // re-enable CheckBoxes
                        for (CheckBox cb : checkBoxList) {
                            cb.setDisable(false);
                        }
                    }
                    activeCount--;
                }
            }
        };

        for (int in = 0; in < 4; in++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setLayoutY(135);
            checkBox.setLayoutX(in*90 + 55);
            checkBox.selectedProperty().addListener(listener);
            checkBoxList.add(checkBox);
            pane.getChildren().add(checkBox);
        }

        List<String> resourcesToSend = new ArrayList<>();

        Button chooseResourcesButton = new Button("Enter");
        chooseResourcesButton.setOnAction(e->{
            if (playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 1 || playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 2)
                resourcesToSend.add(resources[checkBoxList.indexOf(chosenResources.get(0))]);
            else if (playerBoard.getPlayerList().indexOf(playerBoard.getNickname()) == 3) {
                resourcesToSend.add(resources[checkBoxList.indexOf(chosenResources.get(0))]);
                resourcesToSend.add(resources[checkBoxList.indexOf(chosenResources.get(1))]);
            }
            controller.sendMessage(new MessageChooseResourcesFirstTurn(playerBoard.getNickname(), resourcesToSend));
        });
        chooseResourcesButton.setLayoutX(checkBoxList.get(3).getLayoutX() - 12);
        chooseResourcesButton.setLayoutY(160);
        pane.getChildren().add(chooseResourcesButton);

        pane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(pane);
    }

    public void showMessage(String message) {
        Stage stage1 = new Stage();
        Label text = new Label(message);

        text.setFont(new Font("Arial", 20));
        text.setTextFill(Color.WHITE);
        text.setLayoutY(60);
        Button close = new Button("Ok");
        close.setOnAction(e-> stage1.close());
        Pane group = new Pane();
        group.getChildren().addAll(text, close);
        close.setLayoutX(text.getText().length()*15-40);
        close.setLayoutY(120);
        group.setStyle("-fx-border-color: black");

        group.setBackground(new Background(new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY)));
        stage1.setTitle("Error");
        stage1.setScene(new Scene(group, text.getText().length()*15, 150));
        stage1.setAlwaysOnTop(true);
        stage1.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    }

    public void showErrorMessage(String errorMessage) {
        Stage stage1 = new Stage();
        Label text = new Label(errorMessage);
        text.setFont(new Font("Arial", 20));
        text.setTextFill(Color.WHITE);
        Button close = new Button("Ok");
        close.setOnAction(e-> stage1.close());
        Pane group = new Pane();
        group.getChildren().addAll(text, close);
        //group.setPrefWidth(400);
        //group.setPrefHeight(150);
        text.setLayoutY(60);
        close.setLayoutX(text.getText().length()*15-40);
        close.setLayoutY(120);
        group.setStyle("-fx-border-color: black");

        group.setBackground(new Background(new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY)));
        stage1.setTitle("Error");
        stage1.setScene(new Scene(group, text.getText().length()*15, 150));
        stage1.setAlwaysOnTop(true);
        stage1.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage1.initStyle(StageStyle.UNDECORATED);

        stage1.show();
    }

    public void activeProductions() {
        productionsStage = new Stage();
        productionsStage.setAlwaysOnTop(true);


        Pane borderPane = new Pane();

        HBox textBox = new HBox();
        Text choiceText = new Text("Choose the type of production: ");
        choiceText.setFont(new Font("Arial", 16));
        choiceText.setStroke(Color.WHITE);
        textBox.getChildren().add(choiceText);
        textBox.setLayoutY(5);
        textBox.setLayoutX(10);
        borderPane.getChildren().add(textBox);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setLayoutX(10);
        buttonsBox.setLayoutY(40);

        Button baseProductionButton = new Button("Base\nProduction");
        buttonsBox.getChildren().add(baseProductionButton);
        baseProductionButton.setOnAction(e-> baseProductionScene(productionsStage));

        Button devCardProductionButton = new Button("Development Card\nProduction");
        buttonsBox.getChildren().add(devCardProductionButton);
        devCardProductionButton.setOnAction(e-> devCardProductionScene(productionsStage));

        Button leaderCardProductionButton = new Button("Leader Card\nProduction");
        buttonsBox.getChildren().add(leaderCardProductionButton);
        leaderCardProductionButton.setOnAction(e-> leaderCardProductionScene(productionsStage));

        borderPane.getChildren().add(buttonsBox);

        Button endProductionButton = new Button("End production");
        endProductionButton.setOnAction(e->controller.sendMessage(new MessageEndProduction(playerBoard.getNickname())));
        endProductionButton.setLayoutX(10);
        endProductionButton.setLayoutY(100);
        borderPane.getChildren().add(endProductionButton);

        borderPane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(borderPane, 310, 140);

        productionsStage.setScene(scene);
        productionsStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        productionsStage.setTitle("Active Production");
        productionsStage.show();
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
        explanationLabel1.setFont(new Font("Arial", 16));
        explanationLabel1.setTextFill(Color.WHITE);
        GridPane.setConstraints(explanationLabel1, 0, 0);
        grid.getChildren().add(explanationLabel1);

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
                            chosenStructures[0] = 't';
                            break;
                    }
                });
        grid.getChildren().add(cbStructure1);

        ChoiceBox cbResources1 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        GridPane.setConstraints(cbResources1, 1, 3);
        cbResources1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> chosenResources[0] = resourcesVett[new_val.intValue()]);
        grid.getChildren().add(cbResources1);

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
                            chosenStructures[1] = 't';
                            break;
                    }
                });
        grid.getChildren().add(cbStructure2);

        ChoiceBox cbResources2 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        GridPane.setConstraints(cbResources2, 1, 5);
        cbResources2.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> chosenResources[1] = resourcesVett[new_val.intValue()]);
        grid.getChildren().add(cbResources2);

        Label explanationLabel2 = new Label("Choose the produced Resources: ");
        explanationLabel2.setFont(new Font("Arial", 16));
        explanationLabel2.setTextFill(Color.WHITE);
        GridPane.setConstraints(explanationLabel2, 0, 7);
        grid.getChildren().add(explanationLabel2);

        ChoiceBox cbResources3 = new ChoiceBox(FXCollections.observableArrayList(
                "Coin", "Servant", "Shield", "Stone")
        );
        GridPane.setConstraints(cbResources3, 0, 9);
        cbResources3.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> chosenResources[2] = resourcesVett[new_val.intValue()]);
        grid.getChildren().add(cbResources3);

        Button enterButton = new Button("Enter");
        GridPane.setConstraints(enterButton, 0, 11);
        enterButton.setOnAction(e->{
            if (chosenResources[0] != null || chosenResources[1] != null || chosenResources[2] != null)
                controller.sendMessage(new MessageActiveBaseProduction(playerBoard.getNickname(), chosenResources[2], chosenStructures[0], chosenResources[0], chosenStructures[1], chosenResources[1]));
            else showMessage("You have left some field blank");
        });
        grid.getChildren().add(enterButton);

        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(grid);
        stage1.setScene(scene);
        stage1.setTitle("Base production");
        stage1.show();
    }

    public void devCardProductionScene(Stage stage1) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        final int maxCount = 1;
        final Set<CheckBox> activatedCheckBoxes = new LinkedHashSet<>();
        List<CheckBox> selectedCheck = new ArrayList<>();

        ChangeListener<Boolean> listener = (o, oldValue, newValue) -> {
            CheckBox checkBox = (CheckBox) ((ReadOnlyProperty) o).getBean();

            if (newValue) {
                activatedCheckBoxes.add(checkBox);
                selectedCheck.add(checkBox);
                if (activatedCheckBoxes.size() > maxCount) {
                    // get first checkbox to be activated
                    checkBox = activatedCheckBoxes.iterator().next();

                    // unselect; change listener will remove
                    checkBox.setSelected(false);
                    selectedCheck.remove(checkBox);
                }
            } else {
                activatedCheckBoxes.remove(checkBox);
            }
        };

        for (int i = 0; i < 3; i++) {
            String cardID = null;
            for (int j = 0; j < 3; j++) {
                if (playerBoard.getSlotDevCard()[j][i] != null)
                    cardID = playerBoard.getSlotDevCard()[j][i];
            }
            if (cardID != null) {
                ImageView cardView = new ImageView(new Image("front/" + cardID + ".png"));
                cardView.setPreserveRatio(true);
                cardView.setFitWidth(150);
                GridPane.setConstraints(cardView, i, 0);
                grid.getChildren().add(cardView);

                CheckBox checkBox = new CheckBox();
                checkBox.setUserData(i);
                checkBox.selectedProperty().addListener(listener);
                checkBox.setLayoutX(10);
                GridPane.setConstraints(checkBox, i, 2);
                grid.getChildren().add(checkBox);
            }
        }

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(e->{
            if (!selectedCheck.isEmpty()) {
                controller.sendMessage(new MessageActiveProductionDevCard(playerBoard.getNickname(), (Integer) selectedCheck.get(0).getUserData()));
            }
        });
        GridPane.setConstraints(enterButton, 0, 4);
        grid.getChildren().add(enterButton);

        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        stage1.setScene(new Scene(grid));
        stage1.setTitle("Development card production");
        stage1.show();
    }

    public void leaderCardProductionScene(Stage stage1) {
        Pane leaderCardPane = new Pane();

        List<CheckBox> checkBoxListCard = new ArrayList<>();

        final int maxCount = 1;
        final Set<CheckBox> activatedCheckBoxes = new LinkedHashSet<>();
        List<CheckBox> checkBoxSelected = new ArrayList<>();

        ChangeListener<Boolean> listener1 = (o, oldValue, newValue) -> {
            CheckBox checkBox = (CheckBox) ((ReadOnlyProperty) o).getBean();

            if (newValue) {
                activatedCheckBoxes.add(checkBox);
                checkBoxSelected.add(checkBox);
                if (activatedCheckBoxes.size() > maxCount) {
                    // get first checkbox to be activated
                    checkBox = activatedCheckBoxes.iterator().next();

                    // unselect; change listener1 will remove
                    checkBox.setSelected(false);
                    checkBoxSelected.remove(checkBox);
                }
            } else {
                activatedCheckBoxes.remove(checkBox);
                checkBoxSelected.remove(checkBox);
            }
        };

        List<String> leaderCardProductionList = playerBoard.getLeaderCards()
                .stream()
                .filter(lc -> lc.equals("LCPL1") || lc.equals("LCPL2") || lc.equals("LCPL3") || lc.equals("LCPL4"))
                .collect(Collectors.toList());

        if (!leaderCardProductionList.isEmpty()) {
            int i = 0;
            for (String card : leaderCardProductionList) {
                ImageView leaderCardView = new ImageView(new Image("front/" + card + ".png"));
                leaderCardView.setFitWidth(120);
                leaderCardView.setPreserveRatio(true);
                leaderCardView.setX(i*135 + 5);
                leaderCardView.setY(5);
                leaderCardPane.getChildren().add(leaderCardView);

                CheckBox checkBox = new CheckBox();
                checkBox.setUserData(card);
                checkBox.selectedProperty().addListener(listener1);
                checkBox.setLayoutX(i*138 + 55);
                checkBox.setLayoutY(190);
                checkBoxListCard.add(checkBox);
                leaderCardPane.getChildren().add(checkBox);
                i++;
            }
        }

        Pane rightPane = new Pane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label label1 = new Label("Choose from where do you want to take\nthe resource indicated on the left: ");
        label1.setFont(new Font("Arial", 16));
        label1.setTextFill(Color.WHITE);
        GridPane.setConstraints(label1, 0, 0);
        grid.getChildren().add(label1);

        //choiceBox
        char[] chosenStructures = new char[1];

        ChoiceBox cbStructure1 = new ChoiceBox(FXCollections.observableArrayList(
                "Select a structure", "Warehouse", "Strongbox", "Extra chest")
        );
        GridPane.setConstraints(cbStructure1, 0, 2);
        cbStructure1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    switch (new_val.intValue()) {
                        case 1 :
                            chosenStructures[0] = 'W';
                            break;
                        case 2 :
                            chosenStructures[0] = 'S';
                            break;
                        case 3 :
                            chosenStructures[0] = 'E';
                            break;
                        default:
                            break;
                    }
                });
        grid.getChildren().add(cbStructure1);

        //view risorse con checkbox
        Label labelChosenResource = new Label("Choose which resource do you want obtain:");
        labelChosenResource.setFont(new Font("Arial", 16));
        labelChosenResource.setTextFill(Color.WHITE);
        GridPane.setConstraints(labelChosenResource, 0, 3);
        grid.getChildren().add(labelChosenResource);

        rightPane.getChildren().add(grid);

        String[] resources = {"Coins", "Servants", "Shields", "Stones"};

        int coordinateX = 0;

        for (String res : resources) {
            ImageView resView = new ImageView(new Image("punchboard/" + res + ".png"));
            resView.setFitWidth(30);
            resView.setPreserveRatio(true);
            resView.setY(120);
            resView.setX(coordinateX*50 + 10);
            rightPane.getChildren().add(resView);
            coordinateX++;
        }

        List<CheckBox> chosenResources = new ArrayList<>();
        List<CheckBox> checkBoxListResource = new ArrayList<>();

        int finalMaxCount = 1;

        ChangeListener<Boolean> listener2 = new ChangeListener<>() {
            private int activeCount = 0;

            public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    activeCount++;
                    for (CheckBox cb : checkBoxListResource) {
                        if (cb.isSelected()) {
                            if (!chosenResources.contains(cb))
                                chosenResources.add(cb);
                        }
                    }
                    if (activeCount == finalMaxCount) {
                        // disable unselected CheckBoxes
                        for (CheckBox cb : checkBoxListResource) {
                            if (!cb.isSelected()) {
                                cb.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (activeCount == finalMaxCount) {
                        for (CheckBox cb : checkBoxListResource) {
                            if (!cb.isDisable() && !cb.isSelected())
                                chosenResources.remove(cb);
                        }
                        // re-enable CheckBoxes
                        for (CheckBox cb : checkBoxListResource) {
                            cb.setDisable(false);
                        }
                    }
                    activeCount--;
                }
            }
        };

        for (int in = 0; in < 4; in++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setLayoutY(160);
            checkBox.setLayoutX(in*50 + 15);
            checkBox.selectedProperty().addListener(listener2);
            checkBoxListResource.add(checkBox);
            rightPane.getChildren().add(checkBox);
        }

        Button activeProductionButton = new Button("Active production");
        activeProductionButton.setOnAction(e->{
            if (!checkBoxSelected.isEmpty() && chosenStructures != null && !chosenResources.isEmpty()) {
                int index = checkBoxListCard.indexOf(checkBoxSelected.get(0));
                String cardID = (String) checkBoxListCard.get(index).getUserData();
                String resource = resources[checkBoxListResource.indexOf(chosenResources.get(0))];
                controller.sendMessage(new MessageActiveLeaderCardProduction(playerBoard.getNickname(), cardID, chosenStructures[0], resource));
            }
            else
                showErrorMessage("Select a Leader card");
        });
        activeProductionButton.setLayoutX(10);
        activeProductionButton.setLayoutY(190);
        rightPane.getChildren().add(activeProductionButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(leaderCardPane);
        borderPane.setRight(rightPane);

        borderPane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        stage1.setScene(new Scene(borderPane));
        stage1.setTitle("Active Leader card production");
        stage1.show();
    }

    public void payResourcesScene() {
        payResourcesStage = new Stage();
        payResourcesStage.setAlwaysOnTop(true);

        Pane pane = new Pane();
        ImageView devCardView = null;
        if (playerBoard.getCurrentDevCardToBuy() != null)
            devCardView = new ImageView(new Image("front/" + playerBoard.getCurrentDevCardToBuy() + ".png"));
        else if (playerBoard.getActivedDevCardProd() != null)
            devCardView = new ImageView(new Image("front/" + playerBoard.getActivedDevCardProd() + ".png"));

        devCardView.setFitWidth(180);
        devCardView.setPreserveRatio(true);
        pane.getChildren().add(devCardView);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label labelWarehouse = new Label("Warehouse: ");
        labelWarehouse.setFont(new Font("Arial", 16));
        labelWarehouse.setTextFill(Color.WHITE);
        GridPane.setConstraints(labelWarehouse, 1, 0);
        grid.getChildren().add(labelWarehouse);

        Label labelStrongbox = new Label("Strongbox: ");
        labelStrongbox.setFont(new Font("Arial", 16));
        labelStrongbox.setTextFill(Color.WHITE);
        GridPane.setConstraints(labelStrongbox, 2, 0);
        grid.getChildren().add(labelStrongbox);

        Label labelExtraChest = new Label("Extra chest: ");
        labelExtraChest.setFont(new Font("Arial", 16));
        labelExtraChest.setTextFill(Color.WHITE);
        GridPane.setConstraints(labelExtraChest, 3, 0);
        grid.getChildren().add(labelExtraChest);

        Label labelCoins = new Label();
        ImageView coinsView = new ImageView("punchboard/Coins.png");
        coinsView.setFitWidth(30);
        coinsView.setPreserveRatio(true);
        labelCoins.setGraphic(coinsView);
        GridPane.setConstraints(labelCoins, 0, 2);
        grid.getChildren().add(labelCoins);

        Label labelServants = new Label();
        ImageView servantsView = new ImageView("punchboard/Servants.png");
        servantsView.setFitWidth(30);
        servantsView.setPreserveRatio(true);
        labelServants.setGraphic(servantsView);
        GridPane.setConstraints(labelServants, 0, 4);
        grid.getChildren().add(labelServants);

        Label labelShields = new Label();
        ImageView shieldsView = new ImageView("punchboard/Shields.png");
        shieldsView.setFitWidth(30);
        shieldsView.setPreserveRatio(true);
        labelShields.setGraphic(shieldsView);
        GridPane.setConstraints(labelShields, 0, 6);
        grid.getChildren().add(labelShields);

        Label labelStones = new Label();
        ImageView stonesView = new ImageView("punchboard/Stones.png");
        stonesView.setFitWidth(30);
        stonesView.setPreserveRatio(true);
        labelStones.setGraphic(stonesView);
        GridPane.setConstraints(labelStones, 0, 8);
        grid.getChildren().add(labelStones);

        TextField numCoinsWarehouse = new TextField();
        numCoinsWarehouse.setMaxSize(50, 50);
        GridPane.setConstraints(numCoinsWarehouse, 1, 2);
        grid.getChildren().add(numCoinsWarehouse);

        TextField numServantsWarehouse = new TextField();
        numServantsWarehouse.setMaxSize(50, 50);
        GridPane.setConstraints(numServantsWarehouse, 1, 4);
        grid.getChildren().add(numServantsWarehouse);

        TextField numShieldsWarehouse = new TextField();
        numShieldsWarehouse.setMaxSize(50, 50);
        GridPane.setConstraints(numShieldsWarehouse, 1, 6);
        grid.getChildren().add(numShieldsWarehouse);

        TextField numStonesWarehouse = new TextField();
        numStonesWarehouse.setMaxSize(50, 50);
        GridPane.setConstraints(numStonesWarehouse, 1, 8);
        grid.getChildren().add(numStonesWarehouse);

        TextField numCoinsStrongbox = new TextField();
        numCoinsStrongbox.setMaxSize(50, 50);
        GridPane.setConstraints(numCoinsStrongbox, 2, 2);
        grid.getChildren().add(numCoinsStrongbox);

        TextField numServantsStrongbox = new TextField();
        numServantsStrongbox.setMaxSize(50, 50);
        GridPane.setConstraints(numServantsStrongbox, 2, 4);
        grid.getChildren().add(numServantsStrongbox);

        TextField numShieldsStrongbox = new TextField();
        numShieldsStrongbox.setMaxSize(50, 50);
        GridPane.setConstraints(numShieldsStrongbox, 2, 6);
        grid.getChildren().add(numShieldsStrongbox);

        TextField numStonesStrongbox = new TextField();
        numStonesStrongbox.setMaxSize(50, 50);
        GridPane.setConstraints(numStonesStrongbox, 2, 8);
        grid.getChildren().add(numStonesStrongbox);

        TextField numCoinsExtraChest = new TextField();
        numCoinsExtraChest.setMaxSize(50, 50);
        GridPane.setConstraints(numCoinsExtraChest, 3, 2);
        grid.getChildren().add(numCoinsExtraChest);

        TextField numServantsExtraChest = new TextField();
        numServantsExtraChest.setMaxSize(50, 50);
        GridPane.setConstraints(numServantsExtraChest, 3, 4);
        grid.getChildren().add(numServantsExtraChest);

        TextField numShieldsExtraChest = new TextField();
        numShieldsExtraChest.setMaxSize(50, 50);
        GridPane.setConstraints(numShieldsExtraChest, 3, 6);
        grid.getChildren().add(numShieldsExtraChest);

        TextField numStonesExtraChest = new TextField();
        numStonesExtraChest.setMaxSize(50, 50);
        GridPane.setConstraints(numStonesExtraChest, 3, 8);
        grid.getChildren().add(numStonesExtraChest);

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(e->{
            Map<String, Integer> warehouseMap = new HashMap<>();
            Map<String, Integer> strongboxMap = new HashMap<>();
            Map<String, Integer> extraChestMap = new HashMap<>();
            if (convertInputText(numCoinsWarehouse.getText()))
                warehouseMap.put("Coins", Integer.parseInt(numCoinsWarehouse.getText()));
            if (convertInputText(numServantsWarehouse.getText()))
                warehouseMap.put("Servants", Integer.parseInt(numServantsWarehouse.getText()));
            if (convertInputText(numShieldsWarehouse.getText()))
                warehouseMap.put("Shields", Integer.parseInt(numShieldsWarehouse.getText()));
            if (convertInputText(numStonesWarehouse.getText()))
                warehouseMap.put("Stones", Integer.parseInt(numStonesWarehouse.getText()));
            if (convertInputText(numCoinsStrongbox.getText()))
                strongboxMap.put("Coins", Integer.parseInt(numCoinsStrongbox.getText()));
            if (convertInputText(numServantsStrongbox.getText()))
                strongboxMap.put("Servants", Integer.parseInt(numServantsStrongbox.getText()));
            if (convertInputText(numShieldsStrongbox.getText()))
                strongboxMap.put("Shields", Integer.parseInt(numShieldsStrongbox.getText()));
            if (convertInputText(numStonesStrongbox.getText()))
                strongboxMap.put("Stones", Integer.parseInt(numStonesStrongbox.getText()));
            if (convertInputText(numCoinsExtraChest.getText()))
                extraChestMap.put("Coins", Integer.parseInt(numCoinsExtraChest.getText()));
            if (convertInputText(numServantsExtraChest.getText()))
                extraChestMap.put("Servants", Integer.parseInt(numServantsExtraChest.getText()));
            if (convertInputText(numShieldsExtraChest.getText()))
                extraChestMap.put("Shields", Integer.parseInt(numShieldsExtraChest.getText()));
            if (convertInputText(numStonesExtraChest.getText()))
                extraChestMap.put("Stones", Integer.parseInt(numStonesExtraChest.getText()));

            controller.sendMessage(new MessagePayResources(playerBoard.getNickname(), warehouseMap, strongboxMap, extraChestMap));
        });
        GridPane.setConstraints(enterButton, 0, 10);
        grid.getChildren().add(enterButton);

        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(pane);
        borderPane.setRight(grid);

        borderPane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        payResourcesStage.setScene(new Scene(borderPane));
        payResourcesStage.setTitle("Pay resources");
        payResourcesStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        payResourcesStage.initStyle(StageStyle.UNDECORATED);
        payResourcesStage.show();
    }

    public boolean convertInputText(String input) {
        if (input.equals(""))
            return false;
        try {
            Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void activeExtraction(){
        extractionMarbleStage = new Stage();
        extractionMarbleStage.setAlwaysOnTop(true);
        Pane marketTrayPane = new Pane();

        ImageView marblesPunchView = new ImageView(new Image("punchboard/plancia portabiglie.png"));
        marblesPunchView.setFitWidth(400);
        marblesPunchView.setPreserveRatio(true);
        marketTrayPane.getChildren().add(marblesPunchView);

        ImageView marketTrayView = new ImageView(new Image("punchboard/spazioBiglie.png"));
        marketTrayView.setFitWidth(280);
        marketTrayView.setPreserveRatio(true);
        marketTrayView.setY(62);
        marketTrayView.setX(62);
        marketTrayPane.getChildren().add(marketTrayView);

        ImageView[][] marblesView = new ImageView[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marblesView[i][j] = new ImageView(new Image("punchboard/Marbles/" + playerBoard.getMarketTray()[i][j] + ".png"));
                marblesView[i][j].setFitWidth(40);
                marblesView[i][j].setPreserveRatio(true);
                marblesView[i][j].setX(j*46 + 120);
                marblesView[i][j].setY(i*46 + 120);
                marketTrayPane.getChildren().add(marblesView[i][j]);
            }
        }
        ImageView remainingMarbleView = new ImageView(new Image("punchboard/Marbles/" + playerBoard.getRemainingMarble() + ".png"));
        remainingMarbleView.setFitWidth(40);
        remainingMarbleView.setPreserveRatio(true);
        remainingMarbleView.setX(290);
        remainingMarbleView.setY(80);
        marketTrayPane.getChildren().add(remainingMarbleView);

        final int maxCount = 1;
        final Set<CheckBox> activatedCheckBoxes = new LinkedHashSet<>();
        List<CheckBox> checkBoxList = new ArrayList<>();
        List<CheckBox> checkBoxListColumns = new ArrayList<>();
        List<CheckBox> checkBoxListRows = new ArrayList<>();

        ChangeListener<Boolean> listener = (o, oldValue, newValue) -> {
            CheckBox checkBox = (CheckBox) ((ReadOnlyProperty) o).getBean();

            if (newValue) {
                activatedCheckBoxes.add(checkBox);
                checkBoxList.add(checkBox);
                if (activatedCheckBoxes.size() > maxCount) {
                    // get first checkbox to be activated
                    checkBox = activatedCheckBoxes.iterator().next();

                    // unselect; change listener will remove
                    checkBox.setSelected(false);
                    checkBoxList.remove(checkBox);
                }
            } else {
                activatedCheckBoxes.remove(checkBox);
            }
        };

        // create checkboxes
        for (int i = 0; i < 4; i++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setUserData('C');
            checkBox.setLayoutX(marblesView[2][i].getX() + 10);
            checkBox.setLayoutY(marblesView[2][i].getY() + 50);
            checkBox.selectedProperty().addListener(listener);
            checkBoxListColumns.add(checkBox);
            marketTrayPane.getChildren().add(checkBox);
        }

        for (int i = 0; i < 3; i++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setUserData('R');
            checkBox.setLayoutX(marblesView[2][3].getX() + 52);
            checkBox.setLayoutY(marblesView[0][0].getY() + 12 + i*45);
            checkBox.selectedProperty().addListener(listener);
            checkBoxListRows.add(checkBox);
            marketTrayPane.getChildren().add(checkBox);
        }

        Button enterButton = new Button("Extract");
        enterButton.setOnAction(e->{
            try {
                if (checkBoxListColumns.contains(checkBoxList.get(0))) {
                    controller.sendMessage(new MessageExtractionMarbles(playerBoard.getNickname(), 'C', checkBoxListColumns.indexOf(checkBoxList.get(0))));
                }
                else if (checkBoxListRows.contains(checkBoxList.get(0))) {
                    controller.sendMessage(new MessageExtractionMarbles(playerBoard.getNickname(), 'R', checkBoxListRows.indexOf(checkBoxList.get(0))));
                }
                else
                    showErrorMessage("Sbagliato");
            }
            catch (IndexOutOfBoundsException exception) {
                showErrorMessage("Choose a row or a column");
            }
        });
        enterButton.setLayoutX(323);
        enterButton.setLayoutY(330);
        marketTrayPane.getChildren().add(enterButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(marketTrayPane);

        extractionMarbleStage.setScene(new Scene(borderPane));
        extractionMarbleStage.setTitle("Extract marbles");
        extractionMarbleStage.setAlwaysOnTop(true);
        extractionMarbleStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        extractionMarbleStage.show();
    }

    public void manageMarbleScene() {
        manageMarbleStage = new Stage();
        manageMarbleStage.setAlwaysOnTop(true);

        Pane marblesBufferPane = new Pane();

        List<ImageView> marblesBufferView = new ArrayList<>();

        int i = 0;
        for (String marble : playerBoard.getMarbleBuffer()) {
            ImageView marbleView = new ImageView(new Image("punchboard/Marbles/" + marble + ".png"));
            marbleView.setFitWidth(40);
            marbleView.setPreserveRatio(true);
            marbleView.setX(i + 50);
            marbleView.setY(20);
            i += 100;
            marblesBufferView.add(marbleView);
        }
        marblesBufferPane.getChildren().addAll(marblesBufferView);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        char[] structures = {'W', 'E'};
        List<Character> chosenStructure = new ArrayList<>();

        Label label1 = new Label("Select a structure in which you want to insert the converted resource: ");
        label1.setFont(new Font("Arial", 16));
        label1.setTextFill(Color.WHITE);
        GridPane.setConstraints(label1, 0, 5);
        grid.getChildren().add(label1);

        ChoiceBox cbStructure = new ChoiceBox(FXCollections.observableArrayList(
                "Select structure", "Warehouse", "Extra chest")
        );
        GridPane.setConstraints(cbStructure, 1, 5);
        cbStructure.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    if (new_val.intValue() != 0) {
                        //chosenStructure.set(0, structures[new_val.intValue() - 1]);
                        if (chosenStructure.isEmpty()) {
                            chosenStructure.add(structures[new_val.intValue() - 1]);
                        }
                        else {
                            chosenStructure.set(0, structures[new_val.intValue() - 1]);
                        }
                    }
                });
        grid.getChildren().add(cbStructure);

        List<Integer> indexWarehouse = new ArrayList<>();

        Label label2 = new Label("Select the Warehouse's index in which you want to insert the converted resources: ");
        label2.setFont(new Font("Arial", 16));
        label2.setTextFill(Color.WHITE);
        GridPane.setConstraints(label2, 0, 8);
        grid.getChildren().add(label2);

        ChoiceBox cbIndexWarehouse = new ChoiceBox(FXCollections.observableArrayList(
                "Select index", "0", "1", "2")
        );
        GridPane.setConstraints(cbIndexWarehouse, 1, 8);
        cbIndexWarehouse.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    if (new_val.intValue() != 0) {
                        //indexWarehouse.set(0, new_val.intValue() - 1);
                        if (indexWarehouse.isEmpty()) {
                            indexWarehouse.add(new_val.intValue() - 1);
                        }
                        else {
                            indexWarehouse.set(0, new_val.intValue() - 1);
                        }
                    }
                });
        grid.getChildren().add(cbIndexWarehouse);

        Pane whiteMarbleEffectPane = new Pane();
        List<CheckBox> chosenCheckBoxWhite = new ArrayList<>();
        List<CheckBox> chosenResource = new ArrayList<>();

        List<String> resourcesWhiteMarble = containsWhiteMarbleEffect();

        if (resourcesWhiteMarble != null) {
            final int maxCount = 1;
            final Set<CheckBox> activatedCheckBoxes = new LinkedHashSet<>();

            ChangeListener<Boolean> listener = (o, oldValue, newValue) -> {
                CheckBox checkBox = (CheckBox) ((ReadOnlyProperty) o).getBean();

                if (newValue) {
                    activatedCheckBoxes.add(checkBox);
                    chosenCheckBoxWhite.add(checkBox);
                    if (activatedCheckBoxes.size() > maxCount) {
                        // get first checkbox to be activated
                        checkBox = activatedCheckBoxes.iterator().next();

                        // unselect; change listener will remove
                        checkBox.setSelected(false);
                        chosenCheckBoxWhite.remove(checkBox);
                    }
                } else {
                    activatedCheckBoxes.remove(checkBox);
                }
            };

            ImageView resourceWhiteMarble1 = new ImageView(new Image("punchboard/Marbles/" + resourcesWhiteMarble.get(0) + ".png"));
            resourceWhiteMarble1.setFitWidth(40);
            resourceWhiteMarble1.setPreserveRatio(true);
            whiteMarbleEffectPane.getChildren().add(resourceWhiteMarble1);

            CheckBox checkBox1 = new CheckBox();
            checkBox1.setLayoutX(12);
            checkBox1.setLayoutY(50);
            chosenResource.add(checkBox1);
            checkBox1.selectedProperty().addListener(listener);
            whiteMarbleEffectPane.getChildren().add(checkBox1);

            if (resourcesWhiteMarble.size() > 1) {
                ImageView resourceWhiteMarble2 = new ImageView(new Image("punchboard/Marbles/" + resourcesWhiteMarble.get(1) + ".png"));
                resourceWhiteMarble2.setX(60);
                resourceWhiteMarble2.setFitWidth(40);
                resourceWhiteMarble2.setPreserveRatio(true);
                whiteMarbleEffectPane.getChildren().add(resourceWhiteMarble2);

                CheckBox checkBox2 = new CheckBox();
                checkBox2.setLayoutX(70);
                checkBox2.setLayoutY(50);
                chosenResource.add(checkBox2);
                checkBox2.selectedProperty().addListener(listener);
                whiteMarbleEffectPane.getChildren().add(checkBox2);
            }
        }

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton, 0, 10);
        addButton.setOnAction(e->{
            if (chosenStructure.isEmpty() || indexWarehouse.isEmpty()) {
                showErrorMessage("Choose where you want to add this marble");
            }
            else {
                if (!chosenCheckBoxWhite.isEmpty()) {
                    if (resourcesWhiteMarble != null) {
                        String changeFromWhite = resourcesWhiteMarble.get(chosenResource.indexOf(chosenCheckBoxWhite.get(0)));
                        controller.sendMessage(new MessageManageMarbles(playerBoard.getNickname(), chosenStructure.get(0), indexWarehouse.get(0), changeFromWhite));
                    }
                }
                else {
                    controller.sendMessage(new MessageManageMarbles(playerBoard.getNickname(), chosenStructure.get(0), indexWarehouse.get(0), null));
                }
            }
        });
        grid.getChildren().add(addButton);

        Label labelDiscardButton = new Label("If you want to discard the first marble, click on the button below");
        labelDiscardButton.setFont(new Font("Arial", 16));
        labelDiscardButton.setTextFill(Color.WHITE);
        GridPane.setConstraints(labelDiscardButton, 0, 13);
        grid.getChildren().add(labelDiscardButton);

        Button discardButton = new Button("Discard");
        GridPane.setConstraints(discardButton, 0, 15);
        discardButton.setOnAction(e-> controller.sendMessage(new MessageManageMarbles(playerBoard.getNickname(), 'D', -1, null)));
        grid.getChildren().add(discardButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(marblesBufferPane);
        borderPane.setCenter(grid);
        borderPane.setRight(whiteMarbleEffectPane);

        borderPane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        manageMarbleStage.setTitle("Manage marbles");
        manageMarbleStage.setScene(new Scene(borderPane));
        manageMarbleStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        manageMarbleStage.initStyle(StageStyle.UNDECORATED);
        manageMarbleStage.setAlwaysOnTop(true);
        manageMarbleStage.show();
    }

    /**
     * This method returns the list of Resources in which they can convert a white marble
     * @return a list of String
     */
    public List<String> containsWhiteMarbleEffect(){
        List<String> resourcesWhite = new ArrayList<>();

        if(playerBoard.getLeaderCards().contains("LCTL1") && playerBoard.getLeaderActionMap().get("LCTL1").getActivated())
            resourcesWhite.add(playerBoard.getLeaderActionMap().get("LCTL1").getResources().toString());

        if (playerBoard.getLeaderCards().contains("LCTL2") && playerBoard.getLeaderActionMap().get("LCTL2").getActivated())
            resourcesWhite.add(playerBoard.getLeaderActionMap().get("LCTL2").getResources().toString());

        if (playerBoard.getLeaderCards().contains("LCTL3") && playerBoard.getLeaderActionMap().get("LCTL3").getActivated())
            resourcesWhite.add(playerBoard.getLeaderActionMap().get("LCTL3").getResources().toString());

        if (playerBoard.getLeaderCards().contains("LCTL4") && playerBoard.getLeaderActionMap().get("LCTL4").getActivated())
            resourcesWhite.add(playerBoard.getLeaderActionMap().get("LCTL4").getResources().toString());

        if (resourcesWhite.isEmpty())
            return null;

        return resourcesWhite;
    }

    public void activeBuyDevCard(){
        activeBuyDevCardStage = new Stage();
        activeBuyDevCardStage.setAlwaysOnTop(true);

        Pane devCardsDeckPane = new Pane();

        List<CheckBox> checkBoxList = new ArrayList<>();

        final int maxCount = 1;
        final Set<CheckBox> activatedCheckBoxes = new LinkedHashSet<>();
        List<CheckBox> checkBoxSelected = new ArrayList<>();

        ChangeListener<Boolean> listener = (o, oldValue, newValue) -> {
            CheckBox checkBox = (CheckBox) ((ReadOnlyProperty) o).getBean();

            if (newValue) {
                activatedCheckBoxes.add(checkBox);
                checkBoxSelected.add(checkBox);
                if (activatedCheckBoxes.size() > maxCount) {
                    // get first checkbox to be activated
                    checkBox = activatedCheckBoxes.iterator().next();

                    // unselect; change listener will remove
                    checkBox.setSelected(false);
                    checkBoxSelected.remove(checkBox);
                }
            } else {
                activatedCheckBoxes.remove(checkBox);
            }
        };

        ImageView[][] devCardsDeckView = new ImageView[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (playerBoard.getDevCardDeck()[i][j][0] == null) {
                    if(i==0){
                        if(j==0) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-33-1.png"));
                        else if(j==1) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-35-1.png"));
                        else if (j==2) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-36-1.png"));
                        else if(j==3) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-34-1.png"));
                    }
                    else if(i==1){
                        if(j==0) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-17-1.png"));
                        else if(j==1) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-19-1.png"));
                        else if (j==2) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-20-1.png"));
                        else if(j==3) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-18-1.png"));
                    }
                    else if(i==2){
                        if(j==0) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-1-1.png"));
                        else if(j==1) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-3-1.png"));
                        else if (j==2) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-4-1.png"));
                        else if(j==3) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-2-1.png"));
                    }
                }
                else
                    devCardsDeckView[i][j] = new ImageView(new Image("front/" + playerBoard.getDevCardDeck()[i][j][0] + ".png"));
                devCardsDeckView[i][j].setFitWidth(120);
                devCardsDeckView[i][j].setPreserveRatio(true);
                devCardsDeckView[i][j].setX(j*130 + 5);
                devCardsDeckView[i][j].setY(i*212 + 5);
                devCardsDeckPane.getChildren().add(devCardsDeckView[i][j]);

                CheckBox checkBox = new CheckBox();
                checkBox.setUserData(playerBoard.getDevCardDeck()[i][j][0]);
                checkBox.setLayoutX(j*131 + 55);
                checkBox.setLayoutY(i*212 + 190);
                checkBox.selectedProperty().addListener(listener);
                checkBoxList.add(checkBox);
                devCardsDeckPane.getChildren().add(checkBox);
            }
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(50);
        grid.setVgap(5);

        Label label = new Label("After you have selected the card,\nchoose the column of the slots for Development Cards\nin which you want to add the card: ");
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.WHITE);
        GridPane.setConstraints(label, 0, 2);
        grid.getChildren().add(label);

        List<Integer> chosenColumn = new ArrayList<>();

        ChoiceBox cbColumnSlotDevCards = new ChoiceBox(FXCollections.observableArrayList(
                "Select column", "1", "2", "3")
        );
        GridPane.setConstraints(cbColumnSlotDevCards, 1, 2);
        cbColumnSlotDevCards.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_value, Number new_val) -> {
                    if (new_val.intValue() != 0) {
                        if (chosenColumn.isEmpty()) {
                            chosenColumn.add(new_val.intValue() - 1);
                        }
                        else {
                            chosenColumn.set(0, new_val.intValue() - 1);
                        }
                    }
                });
        grid.getChildren().add(cbColumnSlotDevCards);

        Button selectCardButton = new Button("Select");
        GridPane.setConstraints(selectCardButton, 0, 4);
        selectCardButton.setOnAction(e->{
            if (checkBoxSelected.isEmpty() || chosenColumn.isEmpty())
                showErrorMessage("Select the column or the card");
            else {
                controller.sendMessage(new MessageBuyDevCard(playerBoard.getNickname(), (String) checkBoxSelected.get(0).getUserData(), chosenColumn.get(0)));
            }
        });
        grid.getChildren().add(selectCardButton);

        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(devCardsDeckPane);
        borderPane.setRight(grid);

        borderPane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        activeBuyDevCardStage.setScene(new Scene(borderPane));
        activeBuyDevCardStage.setTitle("Buy a Development card");
        activeBuyDevCardStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        activeBuyDevCardStage.show();
    }

    public void resumeGameScene(String firstMessage, String secondMessage) {
        Stage stage1 = new Stage();
        stage1.setAlwaysOnTop(true);

        StackPane root = new StackPane();

        Text firstMessageText = new Text(firstMessage);
        firstMessageText.setFont(new Font("Arial", 16));
        firstMessageText.setTextAlignment(TextAlignment.CENTER);
        firstMessageText.setStroke(Color.WHITE);
        root.getChildren().add(firstMessageText);
        StackPane.setAlignment(firstMessageText, Pos.TOP_CENTER);

        Text secondMessageText = new Text(secondMessage);
        secondMessageText.setFont(new Font("Arial", 16));
        secondMessageText.setTextAlignment(TextAlignment.CENTER);
        secondMessageText.setStroke(Color.WHITE);
        root.getChildren().add(secondMessageText);
        StackPane.setAlignment(secondMessageText, Pos.CENTER);

        root.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        stage1.setScene(new Scene(root, 500, 200));
        stage1.setTitle("Resume match");
        stage1.setAlwaysOnTop(true);
        stage1.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage1.show();
    }

    public void endGameScene() {
        Stage stage1 = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(50);
        grid.setVgap(50);

        Text winnerText = new Text("The winner is " + playerBoard.getPlayerWinner());
        winnerText.setFont(new Font("Arial", 16));
        winnerText.setTextAlignment(TextAlignment.CENTER);
        winnerText.setStroke(Color.WHITE);

        GridPane.setConstraints(winnerText, 4, 1);
        grid.getChildren().add(winnerText);

        int i = 2;
        for (String player : playerBoard.getPlayerList()) {
            Text pointsText = new Text(player + " has scored " + playerBoard.getPlayersPoints().get(player));
            pointsText.setFont(new Font("Arial", 16));
            pointsText.setTextAlignment(TextAlignment.CENTER);
            pointsText.setStroke(Color.WHITE);

            GridPane.setConstraints(pointsText, 4, i);
            grid.getChildren().add(pointsText);
            i++;
        }

        Button closeButton = new Button("EndGame");
        closeButton.setOnAction(e-> controller.disconnect());
        closeButton.setLayoutX(50);
        closeButton.setLayoutY(200);
        GridPane.setConstraints(closeButton, 4, 7);
        grid.getChildren().add(closeButton);

        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        stage1.setScene(new Scene(grid, 500, 500));
        stage1.setAlwaysOnTop(true);
        stage1.setTitle("The winner is ...");
        stage1.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage1.show();
    }

    public void showMarket(){
        Stage stage1 = new Stage();
        stage1.setAlwaysOnTop(true);
        Pane marketTrayPane = new Pane();

        ImageView marblesPunchView = new ImageView(new Image("punchboard/plancia portabiglie.png"));
        marblesPunchView.setFitWidth(400);
        marblesPunchView.setPreserveRatio(true);
        marketTrayPane.getChildren().add(marblesPunchView);

        ImageView marketTrayView = new ImageView(new Image("punchboard/spazioBiglie.png"));
        marketTrayView.setFitWidth(280);
        marketTrayView.setPreserveRatio(true);
        marketTrayView.setY(62);
        marketTrayView.setX(62);
        marketTrayPane.getChildren().add(marketTrayView);

        ImageView[][] marblesView = new ImageView[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marblesView[i][j] = new ImageView(new Image("punchboard/Marbles/" + playerBoard.getMarketTray()[i][j] + ".png"));
                marblesView[i][j].setFitWidth(40);
                marblesView[i][j].setPreserveRatio(true);
                marblesView[i][j].setX(j*46 + 120);
                marblesView[i][j].setY(i*46 + 120);
                marketTrayPane.getChildren().add(marblesView[i][j]);
            }
        }
        ImageView remainingMarbleView = new ImageView(new Image("punchboard/Marbles/" + playerBoard.getRemainingMarble() + ".png"));
        remainingMarbleView.setFitWidth(40);
        remainingMarbleView.setPreserveRatio(true);
        remainingMarbleView.setX(290);
        remainingMarbleView.setY(80);
        marketTrayPane.getChildren().add(remainingMarbleView);

        stage1.setScene(new Scene(marketTrayPane));
        stage1.setTitle("Market tray");
        stage1.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage1.show();
    }

    public void showDevDeck(){
        Stage newStage = new Stage();
        newStage.setAlwaysOnTop(true);
        Pane devCardsDeckPane = new Pane();

        ImageView[][] devCardsDeckView = new ImageView[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(playerBoard.getDevCardDeck()[i][j][0]!=null) {
                    devCardsDeckView[i][j] = new ImageView(new Image("front/" + playerBoard.getDevCardDeck()[i][j][0] + ".png"));
                }
                else{
                    if(i==0){
                        if(j==0) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-33-1.png"));
                        else if(j==1) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-35-1.png"));
                        else if (j==2) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-36-1.png"));
                        else if(j==3) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-34-1.png"));
                    }
                    else if(i==1){
                        if(j==0) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-17-1.png"));
                        else if(j==1) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-19-1.png"));
                        else if (j==2) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-20-1.png"));
                        else if(j==3) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-18-1.png"));
                    }
                    else if(i==2){
                        if(j==0) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-1-1.png"));
                        else if(j==1) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-3-1.png"));
                        else if (j==2) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-4-1.png"));
                        else if(j==3) devCardsDeckView[i][j] = new ImageView(new Image("back/Masters of Renaissance__Cards_BACK_3mmBleed-2-1.png"));
                    }
                }
                    devCardsDeckView[i][j].setFitWidth(130);
                    devCardsDeckView[i][j].setPreserveRatio(true);
                    devCardsDeckView[i][j].setX(j * 135 + 5);
                    devCardsDeckView[i][j].setY(i * 200 + 5);
                    devCardsDeckPane.getChildren().add(devCardsDeckView[i][j]);

            }
        }

        devCardsDeckPane.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        newStage.setScene(new Scene(devCardsDeckPane));
        newStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        newStage.show();
    }

    public void showOtherPlayers(){
        otherPlayerStage = new Stage();
        otherPlayerStage.setAlwaysOnTop(true);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(8);

        Label label = new Label("Write the player's name: ");
        label.setFont(new Font("Arial", 16));
        label.setTextFill(Color.WHITE);
        GridPane.setConstraints(label, 0, 0);
        grid.getChildren().add(label);

        TextField textField = new TextField();
        GridPane.setConstraints(textField, 0, 1);
        grid.getChildren().add(textField);

        Button button = new Button("Enter");
        button.setOnAction(e-> controller.sendMessage(new MessageReqOtherPlayer(playerBoard.getNickname(), textField.getText())));
        GridPane.setConstraints(button, 0, 2);
        grid.getChildren().add(button);

        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        otherPlayerStage.setScene(new Scene(grid));
        otherPlayerStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        otherPlayerStage.setTitle("View other player's board");
        otherPlayerStage.show();
    }

    public void showOtherPlayerBoard() {
        Stage stage1 = new Stage();
        stage1.setAlwaysOnTop(true);

        Pane left = new Pane();

        left.setPrefWidth(150);

        Text text = new Text(playerBoard.getOtherPlayer().getName() + "'s Board.");
        text.setFont(new Font("Arial", 16));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setX(37);
        text.setY(40);

        left.getChildren().add(text);

        Button closeStageButton = new Button("Close window");
        closeStageButton.setOnAction(e-> stage1.close());
        closeStageButton.setLayoutY(60);
        closeStageButton.setLayoutX(30);

        left.getChildren().add(closeStageButton);


        left.getChildren().addAll(leaderCardBoard(playerBoard.getOtherPlayer().getLeaderCards()));

        left.getChildren().addAll(extraChest(playerBoard.getOtherPlayer().getExtrachest(), playerBoard.getOtherPlayer().getLeaderCards()));

        Image imageBoardMain = new Image("board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
        ImageView imageBoardView = new ImageView(imageBoardMain);
        imageBoardView.setFitWidth(747);
        imageBoardView.setFitHeight(550);

        Pane boardMain = new Pane();
        boardMain.getChildren().addAll(imageBoardView);
        boardMain.getChildren().addAll(faithMArkerBoard(playerBoard.getOtherPlayer().getName()));

       //POPE SPACE
        boardMain.getChildren().addAll(popeSpace(playerBoard.getPlayersPopFavoriteTile(), playerBoard.getOtherPlayer().getName()));

        //WAREHOUSE
        if(playerBoard.getOtherPlayer().getWarehouse()[0][0]!=null){
            Image resource = new Image("punchboard/" + playerBoard.getOtherPlayer().getWarehouse()[0][0] + ".png");
            ImageView resourceView = new ImageView(resource);
            resourceView.setFitWidth(30);
            resourceView.setFitHeight(30);
            resourceView.setLayoutX(83);
            resourceView.setLayoutY(235);
            boardMain.getChildren().add(resourceView);
        }

        if(playerBoard.getOtherPlayer().getWarehouse()[1][0]!=null){
            Image resource1 = new Image("punchboard/" + playerBoard.getOtherPlayer().getWarehouse()[1][0]+".png");
            ImageView resource1View = new ImageView(resource1);
            resource1View.setFitWidth(30);
            resource1View.setFitHeight(30);
            resource1View.setLayoutX(66);
            resource1View.setLayoutY(285);
            boardMain.getChildren().add(resource1View);
        }

        if(playerBoard.getOtherPlayer().getWarehouse()[1][1]!=null){
            Image resource2 = new Image("punchboard/"+playerBoard.getOtherPlayer().getWarehouse()[1][1]+".png");
            ImageView resource2View = new ImageView(resource2);
            resource2View.setFitWidth(30);
            resource2View.setFitHeight(30);
            resource2View.setLayoutX(99);
            resource2View.setLayoutY(285);
            boardMain.getChildren().add(resource2View);
        }

        if(playerBoard.getOtherPlayer().getWarehouse()[2][0]!=null){
            Image resource3 = new Image("punchboard/"+playerBoard.getOtherPlayer().getWarehouse()[2][0]+".png");
            ImageView resource3View = new ImageView(resource3);
            resource3View.setFitWidth(30);
            resource3View.setFitHeight(30);
            resource3View.setLayoutX(50);
            resource3View.setLayoutY(335);
            boardMain.getChildren().add(resource3View);
        }

        if(playerBoard.getOtherPlayer().getWarehouse()[2][1]!=null){
            Image resource4 = new Image("punchboard/"+playerBoard.getOtherPlayer().getWarehouse()[2][1]+".png");
            ImageView resource4View = new ImageView(resource4);
            resource4View.setFitWidth(30);
            resource4View.setFitHeight(30);
            resource4View.setLayoutX(81);
            resource4View.setLayoutY(335);
            boardMain.getChildren().add(resource4View);
        }

        if(playerBoard.getOtherPlayer().getWarehouse()[2][2]!=null){
            Image resource5 = new Image("punchboard/"+playerBoard.getOtherPlayer().getWarehouse()[2][2]+".png");
            ImageView resource5View = new ImageView(resource5);
            resource5View.setFitWidth(30);
            resource5View.setFitHeight(30);
            resource5View.setLayoutX(112);
            resource5View.setLayoutY(335);
            boardMain.getChildren().add(resource5View);
        }

        //STRONGBOX
        boardMain.getChildren().addAll(strongboxPane(playerBoard.getOtherPlayer().getStrongbox()));
        //SLOTDEVCARD
        boardMain.getChildren().addAll(devCardBoard(playerBoard.getOtherPlayer().getSlotDevCard()));

        HBox allBoard = new HBox();
        allBoard.getChildren().addAll(left, boardMain);

        stage1.setScene(new Scene(allBoard));
        stage1.getIcons().add(new Image("punchboard/retro cerchi.png"));
        stage1.setTitle("Board other player");
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    }

    public Stage getPayResourcesStage() {
        return payResourcesStage;
    }

    public Stage getActiveBuyDevCardStage() {
        return activeBuyDevCardStage;
    }

    public Stage getOtherPlayerStage() {
        return otherPlayerStage;
    }

    public Stage getExtractionMarbleStage() {
        return extractionMarbleStage;
    }

    public Stage getManageMarbleStage() {
        return manageMarbleStage;
    }

    public Stage getProductionsStage() {
        return productionsStage;
    }

    public void showLorenzoTurn(){
        Stage newStage = new Stage();
        newStage.setAlwaysOnTop(true);
        Pane pane = new Pane();
        Button close = new Button("Ok");
        close.setLayoutY(110);
        close.setLayoutX(500);
        close.setOnAction(e->newStage.close());

        Image token = null;
        ImageView tokenView = null;
        Label explanation = null;
        Button showDevDeck = new Button("Show DevDeck");

        if(playerBoard.getLastTokenUsed()!=null){
            switch (playerBoard.getLastTokenUsed()) {
                case "T1":
                    token = new Image("punchboard/cerchio5.png");
                    tokenView = new ImageView(token);
                    tokenView.setFitWidth(70);
                    tokenView.setFitHeight(70);
                    explanation = new Label("Lorenzo moved his black cross of two spaces");

                    break;
                case "T2":
                    token = new Image("punchboard/cerchio7.png");
                    tokenView = new ImageView(token);
                    tokenView.setFitWidth(70);
                    tokenView.setFitHeight(70);
                    explanation = new Label("Lorenzo moved his black cross of one space and\nreshuffle all his tokens");
                    break;
                case "T3":
                    switch (playerBoard.getLastTokenUsedColor()) {
                        case "BLUE":
                            token = new Image("punchboard/cerchio1.png");
                            explanation = new Label("Lorenzo discard two blue cards");
                            break;
                        case "GREEN":
                            token = new Image("punchboard/cerchio2.png");
                            explanation = new Label("Lorenzo discard two green cards");
                            break;
                        case "PURPLE":
                            token = new Image("punchboard/cerchio3.png");
                            explanation = new Label("Lorenzo discard two purple cards");
                            break;
                        case "YELLOW":
                            token = new Image("punchboard/cerchio4.png");
                            explanation = new Label("Lorenzo discard two yellow cards");
                            break;
                    }
                    tokenView = new ImageView(token);
                    tokenView.setFitWidth(70);
                    tokenView.setFitHeight(70);
                    showDevDeck.setOnAction(e -> showDevDeck());
                    showDevDeck.setLayoutY(110);
                    showDevDeck.setLayoutX(300);
                    pane.getChildren().add(showDevDeck);
                    break;
            }

        tokenView.setLayoutY(30);
        explanation.setLayoutY(53);
        explanation.setLayoutX(72);
        explanation.setFont(new Font("Arial", 24));
        explanation.setTextFill(Color.BLACK);

        pane.getChildren().addAll(tokenView, explanation, close);
        pane.setBackground(new Background(new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setStyle("-fx-border-color: black");
        pane.setMinHeight(130);
        newStage.setScene(new Scene(pane));
        newStage.getIcons().add(new Image("punchboard/retro cerchi.png"));
        newStage.setTitle("Lorenzo's turn");
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setAlwaysOnTop(true);
        newStage.show();
        }
    }


    public Scene waitOtherPlayers() {

        Pane grid = new Pane();

        Label numPlayersLabel = new Label("Waiting for other players... ");
        numPlayersLabel.setFont(new Font("Arial", 30));
        numPlayersLabel.setTextFill(Color.BLACK);
        numPlayersLabel.setLayoutX(250);
        numPlayersLabel.setLayoutY(250);

        grid.getChildren().addAll(numPlayersLabel);
        grid.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        return new Scene(grid, 897, 550);
    }
}
