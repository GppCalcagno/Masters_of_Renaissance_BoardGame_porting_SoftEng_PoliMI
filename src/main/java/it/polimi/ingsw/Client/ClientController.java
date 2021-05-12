package it.polimi.ingsw.Client;

import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.message.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClientController implements Observer {

    PlayerBoard board;
    ClientSocket clientSocket;
    view view;

    MessageType currState;


    public ClientController(view view) {
        this.view = view;
        this.board= new PlayerBoard();
        currState=MessageType.CONNECT;
        action();
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

}
