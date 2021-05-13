package it.polimi.ingsw.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.message.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.view;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ChestLeader;
import it.polimi.ingsw.model.card.leadereffect.DiscountLeader;
import it.polimi.ingsw.model.card.leadereffect.ProductionLeader;
import it.polimi.ingsw.model.card.leadereffect.TrasformationMarbleLeader;
import it.polimi.ingsw.model.producible.*;
import it.polimi.ingsw.model.requirements.RequestedLevelDevelopmentCards;
import it.polimi.ingsw.model.requirements.RequestedResources;
import it.polimi.ingsw.model.requirements.RequestedTypeDevelopmentCards;
import it.polimi.ingsw.model.requirements.Requirements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ClientController implements Observer {

    PlayerBoard board;
    ClientSocket clientSocket;
    view view;
    Map<String, DevelopmentCard> developmentCardMap;
    Map<String, LeaderAction> leaderActionMap;

    MessageType currState;


    public ClientController(view view) throws IOException {
        this.view = view;
        this.board= new PlayerBoard();
        currState=MessageType.CONNECT;
        action();
        developmentCardMap = new HashMap<>();
        initializeDevCardMap();
        leaderActionMap = new HashMap<>();
        initializeLeaderCardMap();
    }

    public void connect(Message message){
        MessageConnect mess = (MessageConnect) message;
        clientSocket = new ClientSocket(mess.getServerAddress(), mess.getServerPort());
        currState=MessageType.LOGIN;
        action();
    }

    public void sendMessage(Message message) {
        //divido i messaggi che devono essere spediti al server da quelli che vanno gestiti nel client
        switch (message.getMessageType()){
            case CONNECT:
                connect(message);
                break;
            case CHOOSETURN:
                nextState(message);
                break;
            case CHOOSEAFTERTAKEMARBLE:
                nextState(message);

            case LOGIN:
                board.setNickname(message.getNickname());
                clientSocket.sendMessage(message);
                break;
            default:
                clientSocket.sendMessage(message);
        }
    }

    public void nextState(Message message){
        switch (currState){
            case LOGIN:
                switch (message.getMessageType()){
                    case CHECKOK:
                        view.showMessage("Nickname already in use!");
                        break;
                    case WAITINGOTHERPLAYERS:
                        view.showMessage("Wait for other Player");
                        currState=MessageType.WAITINGOTHERPLAYERS;
                        break;
                    case REQUESTNUMPLAYERS:
                        currState=MessageType.NUMPLAYERS;
                    case INITIALSITUATIONGAME:
                        currState=MessageType.CHOOSELEADERCARDS;
                    break;
                    default:
                        view.showMessage("SERVER ERROR");
                }
            break;

            case NUMPLAYERS:
                switch (message.getMessageType()){
                    case CHECKOK:
                        view.showMessage("Number of player not allowed");
                        break;
                    case WAITINGOTHERPLAYERS:
                        currState=MessageType.WAITINGOTHERPLAYERS;
                        view.showMessage("Wait for other Player");
                        break;
                    case INITIALSITUATIONGAME:
                        currState=MessageType.CHOOSELEADERCARDS;
                        break;
                }
            break;

            case WAITINGOTHERPLAYERS:
                if(message.getMessageType().equals(MessageType.INITIALSITUATIONGAME))
                    currState=MessageType.CHOOSELEADERCARDS;
                break;

            case UPDATECURRENTPLAYER:
                //fa l'azione settata prima se il giocatore è lui
                break;

            case CHOOSELEADERCARDS:
                if(message.getMessageType().equals(MessageType.CHECKOK)){
                    MessageChechOk mess= (MessageChechOk) message;
                    if(mess.getCheck()){
                        currState=MessageType.CHOOSERESOURCESFIRSTTURN;
                    }
                }
                break;

            case CHOSENRESOURCEBASEPRODUCTION:
                currState= MessageType.ENDTURN;
                break;

            case ENDTURN:
                //da vedere in caso di fine partita
                currState= MessageType.CHOOSETURN;
                break;

            case CHOOSETURN:
                if(message.getMessageType().equals(MessageType.CHOOSETURN)) {
                    MessageChooseTurn mess = (MessageChooseTurn) message;
                    switch (mess.getTurnID()){
                        case 0: currState=MessageType.EXTRACTIONMARBLES;            break;
                        case 1: currState=MessageType.SELECTDEVCARD;                break;
                        case 2: currState=MessageType.CHOSENRESOURCEBASEPRODUCTION; break;
                        case 3: currState=MessageType.ACTIVEPRODUCTIONDEVCARD;      break;
                        case 4: currState=MessageType.ACTIVELEADERCARDPRODUCTION;   break;
                        case 5: currState=MessageType.UPDATESTATELEADERACTION;      break;
                        default: view.showMessage("Choose not Allowed");
                    }
                }
                break;

            case EXTRACTIONMARBLES:
                switch (message.getMessageType()){
                    case CHECKOK:
                        view.showMessage("Choose not Allowed");
                        currState=MessageType.CHOOSETURN;
                        break;
                    case EXTRACTEDMARBLESLIST:
                        currState=MessageType.CHOOSEAFTERTAKEMARBLE;
                        break;
                }
                break;

            case CHOOSEAFTERTAKEMARBLE:
                if(message.getMessageType().equals(MessageType.CHOOSEAFTERTAKEMARBLE)){
                    MessageChooseAfterTakeMarble mess= (MessageChooseAfterTakeMarble) message;
                    switch (mess.getChoice()){
                        case 0: currState=MessageType.EXCHANGEWAREHOUSE;  break;
                        case 1: currState=MessageType.ADDDISCARDMARBLES; break;
                        case 2: currState=MessageType.SELECTTRANSFORMATIONWHITEMARBLE; break;

                    }

                }


            case SELECTDEVCARD:
                if(message.getMessageType().equals(MessageType.CHECKOK)){
                    MessageChechOk mess= (MessageChechOk) message;
                    if(mess.getCheck())
                        currState= MessageType.CHOOSERESOURCESPURCHASEDEVCARD;
                    else
                    {
                        view.showMessage("You Can't Buy This Card");
                        currState=MessageType.CHOOSETURN;
                    }
                }
                break;

            case CHOOSERESOURCESPURCHASEDEVCARD:



        }
        //azioni solo se siamo in fase iniziale oppure se  il suo turno
        if(board.getCurrentPlayer()==null || board.getCurrentPlayer().equals(board.getNickname()))
            action();
    }

    public void action(){
        switch (currState){
            case CONNECT:
                view.askServerInfo();
                break;

            case LOGIN:
                view.askNickname();
                break;

            case NUMPLAYERS:
                view.askNumPlayer();
                break;

            case WAITINGOTHERPLAYERS:
                break;

            case CHOOSELEADERCARDS:
                view.askChooseLeaderCards();
                break;

            case CHOOSERESOURCESFIRSTTURN:
                askChooseFirstResurces();
                break;

            case ENDTURN:
                view.endturn();
                break;

            case CHOOSETURN:
                view.askChooseTurn();
                break;

            case EXTRACTIONMARBLES:
                view.askExtractMarble();
                break;

            case CHOOSEAFTERTAKEMARBLE:
                view.askAfterTakeMarble();
                break;



            case SELECTDEVCARD:
                view.askSelectDevCard();
                break;

            case CHOOSERESOURCESPURCHASEDEVCARD:
               view.askChooseResourcesPurchaseDevCard();

        }

    }

    /**
     * this method is used to select the number of initial resources
     */
    private void askChooseFirstResurces() {
        int position= board.getPlayerList().indexOf(board.getNickname());
        int num=1;
        if(position==0) num=0;
        if(position==3) num=2;

        if(num>0){
         view.askChooseResourcesFirstTurn(num);
        }
        else{
            List<String> emptyList= new ArrayList<>();
            sendMessage(new MessageChooseResourcesFirstTurn(board.getNickname(), emptyList));
        }

    }









    /* ************************** UPDATE INFORMAZIONI DA SERVER ********************* */

    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
            case INITIALSITUATIONGAME:
                initialization((MessageInizialization)message);
                break;
            case UPDATECURRENTPLAYER:
                updateCurrentPlayer((MessageUpdateCurrPlayer)message);
                break;
            case UPDATEFAITHPOINTS:
                updateFaithPoint((MessageUpdateFaithMarker) message);
                break;
            case UPDATEMARKETTRAY:
                updateMarketTray((MessageUpdateMarketTray) message);
                break;
            case UPDATEWAREHOUSE:
                updateWarehouse((MessageUpdateWarehouse) message);
                break;
            case UPDATESTRONGBOX:
                updateStrongbox((MessageUpdateStrongbox) message);
                break;

            case EXTRACTEDMARBLESLIST:
                setMarbleBuffer((MessageExtractedMarbles)message);
                break;

            case UPDATEDEVCARDDECK:

        }
        nextState(message);

    }

    private void setMarbleBuffer(MessageExtractedMarbles message) {
        //board.setMarbleBuffer(message.getMarblesList());
    }

    private void initialization(MessageInizialization message) {
        List<String> PlayerList = message.getPlayersNameList();
        List<List<String>> AllLeaderCards= message.getLeaderCardsToChoose();
        String[][][] devCardDeck = message.getDevCardDeckMethod();
        String[][] marketTray= message.getMarketTray();
        String remainingMarble= message.getRemainingMarble();

        List<String> LeaderCard= AllLeaderCards.get(PlayerList.indexOf(board.getNickname()));

        board.initialization(PlayerList,LeaderCard,devCardDeck,marketTray,remainingMarble);
    }

    void updateCurrentPlayer(MessageUpdateCurrPlayer message){
        board.setCurrentPlayer(message.getNickname());
    }

    void updateFaithPoint(MessageUpdateFaithMarker message){
        board.setFaithMarker(message.getFaithPoints());
    }

    void updateMarketTray(MessageUpdateMarketTray message){
        char direction= message.getDirection();
        int num=message.getNum();
        board.updateMarketTray(direction,num);
    }

    void updateStrongbox(MessageUpdateStrongbox message){
        board.setStrongbox(message.getStrongbox());
    }

    void updateWarehouse(MessageUpdateWarehouse message){
        String[][] warehouse= message.getWarehouse();
        Map<String,Integer> extraChest= message.getExtraChest();
        board.setWarehouse(warehouse,extraChest);
    }

    //todo update devCardDeck
    //void updateDevCardDeck(MessageR)

    void initializeDevCardMap() throws IOException {
        Gson gson = new GsonBuilder().create();

        String devCards3g = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3green.json"));
        DevelopmentCard[] devCards3gvet = gson.fromJson(devCards3g, DevelopmentCard[].class);
        for (DevelopmentCard dc3g : devCards3gvet) {
            developmentCardMap.put(dc3g.getID(), dc3g);
        }
        String devCards3b = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3blue.json"));
        DevelopmentCard[] devCards3bvet = gson.fromJson(devCards3b, DevelopmentCard[].class);
        for (DevelopmentCard dc3b : devCards3bvet) {
            developmentCardMap.put(dc3b.getID(), dc3b);
        }
        String devCards3y = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3yellow.json"));
        DevelopmentCard[] devCards3yvet = gson.fromJson(devCards3y, DevelopmentCard[].class);
        for (DevelopmentCard dc3y : devCards3yvet) {
            developmentCardMap.put(dc3y.getID(), dc3y);
        }
        String devCards3p = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3purple.json"));
        DevelopmentCard[] devCards3pvet = gson.fromJson(devCards3p, DevelopmentCard[].class);
        for (DevelopmentCard dc3p : devCards3pvet) {
            developmentCardMap.put(dc3p.getID(), dc3p);
        }
        String devCards2g = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2green.json"));
        DevelopmentCard[] devCards2gvet = gson.fromJson(devCards2g, DevelopmentCard[].class);
        for (DevelopmentCard dc2g : devCards2gvet) {
            developmentCardMap.put(dc2g.getID(), dc2g);
        }
        String devCards2b = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2blue.json"));
        DevelopmentCard[] devCards2bvet = gson.fromJson(devCards2b, DevelopmentCard[].class);
        for (DevelopmentCard dc2b : devCards2bvet) {
            developmentCardMap.put(dc2b.getID(), dc2b);
        }
        String devCards2y = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2yellow.json"));
        DevelopmentCard[] devCards2yvet = gson.fromJson(devCards2y, DevelopmentCard[].class);
        for (DevelopmentCard dc2y : devCards2yvet) {
            developmentCardMap.put(dc2y.getID(), dc2y);
        }
        String devCards2p = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2purple.json"));
        DevelopmentCard[] devCards2pvet = gson.fromJson(devCards2p, DevelopmentCard[].class);
        for (DevelopmentCard dc2p : devCards2pvet) {
            developmentCardMap.put(dc2p.getID(), dc2p);
        }
        String devCards1g = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1green.json"));
        DevelopmentCard[] devCards1gvet = gson.fromJson(devCards1g, DevelopmentCard[].class);
        for (DevelopmentCard dc1g : devCards1gvet) {
            developmentCardMap.put(dc1g.getID(), dc1g);
        }
        String devCards1b = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1blue.json"));
        DevelopmentCard[] devCards1bvet = gson.fromJson(devCards1b, DevelopmentCard[].class);
        for (DevelopmentCard dc1b : devCards1bvet) {
            developmentCardMap.put(dc1b.getID(), dc1b);
        }
        String devCards1y = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1yellow.json"));
        DevelopmentCard[] devCards1yvet = gson.fromJson(devCards1y, DevelopmentCard[].class);
        for (DevelopmentCard dc1y : devCards1yvet) {
            developmentCardMap.put(dc1y.getID(), dc1y);
        }
        String devCards1p = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1purple.json"));
        DevelopmentCard[] devCards1pvet = gson.fromJson(devCards1p, DevelopmentCard[].class);
        for (DevelopmentCard dc1p : devCards1pvet) {
            developmentCardMap.put(dc1p.getID(), dc1p);
        }
    }

    void initializeLeaderCardMap () throws IOException {
        RuntimeTypeAdapterFactory<Requirements> requirementsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Requirements.class)
                .registerSubtype(RequestedResources.class)
                .registerSubtype(RequestedLevelDevelopmentCards.class)
                .registerSubtype(RequestedTypeDevelopmentCards.class);
        RuntimeTypeAdapterFactory<Resources> resourcesRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Resources.class)
                .registerSubtype(Coins.class)
                .registerSubtype(Servants.class)
                .registerSubtype(Shields.class)
                .registerSubtype(Stones.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(requirementsRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(resourcesRuntimeTypeAdapterFactory)
                .create();

        String discount = Files.readString(Paths.get("src/cardJSON/leaderCard/VettDiscountLeader_LeaderCards.json"));
        DiscountLeader[] discountLeaders = gson.fromJson(discount, DiscountLeader[].class);
        for (LeaderAction dl : discountLeaders) {
            leaderActionMap.put(dl.getID(), dl);
        }

        String chest = Files.readString(Paths.get("src/cardJSON/leaderCard/VettChestLeader_LeaderCards.json"));
        ChestLeader[] chestLeaders = gson.fromJson(chest, ChestLeader[].class);
        for (LeaderAction cl : chestLeaders) {
            leaderActionMap.put(cl.getID(), cl);
        }

        String production = Files.readString(Paths.get("src/cardJSON/leaderCard/VettProductionLeader_LeaderCards.json"));
        ProductionLeader[] productionLeaders = gson.fromJson(production, ProductionLeader[].class);
        for (LeaderAction pl : productionLeaders) {
            leaderActionMap.put(pl.getID(), pl);
        }

        String transformation = Files.readString(Paths.get("src/cardJSON/leaderCard/VettTrasformationMarbleLeader_LeaderCards.json"));
        TrasformationMarbleLeader[] transformationMarbleLeaders = gson.fromJson(transformation, TrasformationMarbleLeader[].class);
        for (LeaderAction tl : transformationMarbleLeaders) {
            leaderActionMap.put(tl.getID(), tl);
        }
    }

    DevelopmentCard searchDevCard (String ID) {
        return developmentCardMap.get(ID);
    }

    LeaderAction searchLeaderCard (String ID) {
        return leaderActionMap.get(ID);
    }

    public Map<String, DevelopmentCard> getDevelopmentCardMap() {
        return developmentCardMap;
    }

    public Map<String, LeaderAction> getLeaderActionMap() {
        return leaderActionMap;
    }
}
