package it.polimi.ingsw.Client;

import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.message.*;
import it.polimi.ingsw.Network.message.UpdateMesssage.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;
import it.polimi.ingsw.View.ViewThread;

import java.io.IOException;

import java.util.List;
import java.util.Map;


public class ClientController implements Observer {
    @Override
    public void update(Message message) {

    }

    public void sendMessage(Message messageConnect) {
    }
/*
    PlayerBoard board;
    ClientSocket clientSocket;
    ViewThread view;

    boolean activatedProduction;
    boolean turndone;

    String info=null;

    MessageType currState;


    public ClientController(ViewInterface view) throws IOException {
        this.board= new PlayerBoard();
        this.view = new ViewThread(view,board);

        activatedProduction=false;
        turndone=false;
        currState=MessageType.CONNECT;
        action();
    }

    public void connect(Message message){
        MessageConnect mess = (MessageConnect) message;
        clientSocket = new ClientSocket(mess.getServerAddress(), mess.getServerPort());
        currState=MessageType.LOGIN;
        action();
    }

    public void sendMessage(Message message){
        //divido i messaggi che devono essere spediti al server da quelli che vanno gestiti nel client
        switch (message.getMessageType()){
            case CONNECT: connect(message); break;
            case CHOOSETURN: nextState(message); break;
            case CHOOSEAFTERTAKEMARBLE: nextState(message); break;
            case CHOOSEPRODUCTIONTYPE: nextState(message); break;
            case DISCONNECT: disconnect(); break;
            case LOGIN:
                board.setNickname(message.getNickname());
                clientSocket.sendMessage(message);
                break;
            default: clientSocket.sendMessage(message);
        }
    }

    private void disconnect() {

    }

    public void nextState(Message message){
        switch (currState){
/* *** LOGINPHASE *** */
    /*
            case LOGIN:
                switch (message.getMessageType()){
                    case CHECKOK: info="Nickname already in use!"; break;
                    case REQUESTNUMPLAYERS:  currState=MessageType.NUMPLAYERS; break;
                    case INITIALSITUATIONGAME:  currState=MessageType.CHOOSELEADERCARDS; break;
                    case WAITINGOTHERPLAYERS:
                        info="Wait for other Player";
                        currState=MessageType.WAITINGOTHERPLAYERS;
                        break;
                    default:  info="SERVER ERROR";
                }
            break;

            case NUMPLAYERS:
                switch (message.getMessageType()){
                    case CHECKOK: info="Number of player not allowed"; break;
                    case INITIALSITUATIONGAME:  currState=MessageType.CHOOSELEADERCARDS; break;
                    case WAITINGOTHERPLAYERS:
                        currState=MessageType.WAITINGOTHERPLAYERS;
                        info="Wait for other Player";
                        break;
                }
            break;

/* *** FIRST TURN *** */
    /*
            case WAITINGOTHERPLAYERS:
                if(message.getMessageType().equals(MessageType.INITIALSITUATIONGAME))
                    currState=MessageType.CHOOSELEADERCARDS;
                break;

            case UPDATECURRENTPLAYER: break;

            case CHOOSELEADERCARDS:
                if(message.getMessageType().equals(MessageType.CHECKOK)){
                    MessageChechOk mess= (MessageChechOk) message;
                    if(mess.getCheck()){
                        currState=MessageType.CHOOSERESOURCESFIRSTTURN;
                    }
                }
                break;

            case CHOOSERESOURCESFIRSTTURN:
                currState= MessageType.ENDTURN;
                break;

/* *** fine turno\partita *** */
    /*
            case ENDTURN:
                //aspetto il mio turno-non esiste in singleplayer
                switch(message.getMessageType()){
                    case UPDATECURRENTPLAYER:
                        if(board.getCurrentPlayer().equals(board.getNickname())){
                            info="Start Your Turn!";
                            currState=MessageType.CHOOSETURN;
                        }
                        else
                            info="Start "+ board.getCurrentPlayer()+ " turn!";
                        break;
                    case UPDATEWINNER:
                        currState=MessageType.ENDGAME;

                }

                break;


            case ENDTURNACTIVELEADERCARD:
                turndone=true;
                switch (message.getMessageType()){
                    case CHECKOK:
                        info="can't Active this LeaderCard";
                        currState=MessageType.ENDTURNACTIVELEADERCARD;
                        break;
                    case UPDATECURRENTPLAYER:
                        info="Your Turn is ended. Start "+ board.getCurrentPlayer()+ " turn!";
                        currState=MessageType.ENDTURN;
                        break;
                    case UPDATESINGLEPLAYER:
                        currState=MessageType.CHOOSETURN;
                        break;
                    case UPDATEWINNER: //per giocatore corrente o singleplyaer
                        currState=MessageType.ENDGAME;
                }
                break;


/* *** SCELTA TURNI *** */
    /*
            case CHOOSETURN:
                turndone=false;
                if(message.getMessageType().equals(MessageType.CHOOSETURN)) {
                    MessageChooseTurn mess = (MessageChooseTurn) message;
                    switch (mess.getTurnID()){
                        case 0: currState=MessageType.EXTRACTIONMARBLES;            break;
                        case 1: currState=MessageType.SELECTDEVCARD;                break;
                        case 2: currState=MessageType.CHOOSEPRODUCTIONTYPE;         break;
                        case 3: currState=MessageType.UPDATESTATELEADERACTION;      break;
                        default: info="Choose not Allowed";
                    }
                }
                break;

/* *** Estrazione Biglie *** */
    /*
            case EXTRACTIONMARBLES:
                switch (message.getMessageType()){
                    case CHECKOK:
                        info="Choose not Allowed";
                        currState=MessageType.CHOOSETURN; break;
                    case EXTRACTEDMARBLESLIST: currState=MessageType.CHOOSEAFTERTAKEMARBLE; break;
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
                break;

            case EXCHANGEWAREHOUSE:
                if(message.getMessageType().equals(MessageType.CHECKOK))
                    info="Exchange Not Allowed";
                currState=MessageType.CHOOSEAFTERTAKEMARBLE;
                break;

            case SELECTTRANSFORMATIONWHITEMARBLE:
                if(message.getMessageType().equals(MessageType.CHECKOK))
                    info="Choice Not Allowed";
                currState=MessageType.CHOOSEAFTERTAKEMARBLE;
                break;

            case ADDDISCARDMARBLES:
                switch (message.getMessageType()){
                    case CHECKOK: info="Choice Not Allowed"; break;
                    case UPDATEWAREHOUSE:
                        if(board.getMarbleBuffer().size()>0)
                            currState=MessageType.CHOOSEAFTERTAKEMARBLE;
                        else
                            currState=MessageType.ENDTURNACTIVELEADERCARD;
                }
                 break;


/* *** compro carta ***/
    /*
            case SELECTDEVCARD:
                MessageChechOk mess= (MessageChechOk) message;
                if(mess.getCheck())
                    currState= MessageType.CHOOSERESOURCESPURCHASEDEVCARD;
                else {
                    info="You can't buy this card!";
                    currState=MessageType.CHOOSETURN;
                }
                break;

            case CHOOSERESOURCESPURCHASEDEVCARD:
                switch (message.getMessageType()){
                    case CHECKOK: info="Incorrect Requested Resources!"; break;
                    case UPDATERESOURCES: currState= MessageType.INSERTCARD; break;
                }
                break;
            case INSERTCARD:
                switch (message.getMessageType()){
                    case CHECKOK: info="You can't Insert the Card in that position"; break;
                    case UPDATERESOURCES: currState= MessageType.ENDTURNACTIVELEADERCARD; break;
                }
                break;

/* *** Attivo produzione ** */
    /*
            case CHOOSEPRODUCTIONTYPE:
                MessageChooseProductionType messageChooseProductionType= (MessageChooseProductionType) message;
                switch (messageChooseProductionType.getChoice()){
                    case 0: currState= MessageType.ACTIVESBASEPRODUCTION;           break;
                    case 1: currState= MessageType.ACTIVEPRODUCTIONDEVCARD;         break;
                    case 2: currState= MessageType.ACTIVELEADERCARDPRODUCTION;      break;
                    case 3:
                        if(activatedProduction)
                            currState=MessageType.ENDPRODUCTION;
                        else
                            currState= MessageType.CHOOSETURN;
                    break;

                    default: info="Choice Not Allowed";
                }
                break;

            case ENDPRODUCTION:
                if(message.getMessageType().equals(MessageType.UPDATESTRONGBOX))
                    currState=MessageType.ENDTURNACTIVELEADERCARD;
                    activatedProduction=false;
                break;

            case ACTIVEPRODUCTIONDEVCARD:
                MessageChechOk messageChechOk= (MessageChechOk) message;
                if(messageChechOk.getCheck()){
                    currState=MessageType.CHOOSERESOURCESDEVCARDPRODUCTION;
                    activatedProduction=true;
                }

            case CHOOSERESOURCESDEVCARDPRODUCTION:
                switch (message.getMessageType()){
                    case UPDATERESOURCES:
                        info="Production started!";
                        currState=MessageType.CHOOSEPRODUCTIONTYPE;
                        break;
                    case CHECKOK: info="Incorrect Requested Resources!"; break;
                }
                break;

            case ACTIVESBASEPRODUCTION:
                switch(message.getMessageType()){
                    case UPDATERESOURCES:
                        currState=MessageType.CHOSENRESOURCEBASEPRODUCTION;
                        activatedProduction=true;
                        break;
                    case CHECKOK:
                        info="can't active Base Production!";
                        if(activatedProduction)
                            currState=MessageType.CHOOSEPRODUCTIONTYPE;
                        else
                            currState=MessageType.CHOOSETURN;
                }
                break;

            case CHOSENRESOURCEBASEPRODUCTION:
                MessageChechOk messageChechOk1 = (MessageChechOk) message;
                if(messageChechOk1.getCheck()){
                    info=("Production started!");
                    currState=MessageType.CHOOSEPRODUCTIONTYPE;
                }
                else{
                    info=("Incorrect Requested Resources!");
                }
                break;

            case ACTIVELEADERCARDPRODUCTION:
                switch (message.getMessageType()){
                    case UPDATERESOURCES:
                        info=("Production started!");
                        activatedProduction=true;
                        break;
                    case CHECKOK:
                        info=("Can't Active LeaderProduction");
                        if(activatedProduction)
                            currState=MessageType.CHOOSEPRODUCTIONTYPE;
                        else
                            currState=MessageType.CHOOSETURN;
                }
                break;

/* ** UpdateStateLeaderCard ** */
    /*
            case UPDATESTATELEADERACTION:
                switch (message.getMessageType()){
                    case CHECKOK: info=("Can't Active This card"); break;
                    case UPDATESTATELEADERACTION:
                        MessageUpdateStateLeaderAction messageUpdateStateLeaderAction= (MessageUpdateStateLeaderAction) message;
                        if(messageUpdateStateLeaderAction.isActivated()){
                            info=("Leader Card activated");
                        }
                        else
                            info=("Leader Card discarded");
                }

        }//endSwitch

        //azioni solo se siamo in fase iniziale, se è il suo turno o se la partita è finita
        if(board.getCurrentPlayer()==null || board.getCurrentPlayer().equals(board.getNickname()) || currState==MessageType.ENDGAME)
            action();
    }

public void action(){
        if(info!=null){
            view.setInfo(info);
            view.setCurrentState(MessageType.SHOWMESSAGE);
            view.setActive(true);
            info=null;
        }
        view.setCurrentState(currState);
        view.setActive(true);
};

    /* ************************** UPDATE INFORMAZIONI DA SERVER ********************* */

    /**
     * this method is the Update method of the Observer model. it is used to update client information
     * @param message information about Update
     */
    /*
    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
            case INITIALSITUATIONGAME: initialization((MessageStartGame)message); break;
            case UPDATECURRENTPLAYER:  updateCurrentPlayer((MessageUpdateCurrPlayer)message); break;
            case UPDATEFAITHPOINTS:  updateFaithPoint((MessageUpdateFaithMarker) message);break;
            case UPDATEMARKETTRAY: updateMarketTray((MessageUpdateMarketTray) message); break;
            case UPDATEWAREHOUSE: updateWarehouse((MessageUpdateWarehouse) message); break;
            case UPDATESTRONGBOX: updateStrongbox((MessageUpdateStrongbox) message); break;
            case EXTRACTEDMARBLESLIST: setMarbleBuffer((MessageExtractedMarbles)message); break;
            case UPDATEDEVCARDDECK: updateDevCardDeck((MessageDeleteDevCard) message); break;
            case UPDATEWHITEMARBLEEFFECT: updateWhiteMarbleEffect((MessageUpdateWhiteMarbleEffect) message); break;
            case UPDATESINGLEPLAYER: updateSinglePlyaer((MessageUpdateSinglePlayerGame) message); break;
            case UPDATERESOURCES: updateResources((MessageUpdateResources) message); break;
            case UPDATESTATELEADERACTION:updateStateLeaderAction((MessageUpdateStateLeaderAction) message); break;
        }

        //questi update non triggerano il client
        if(!message.getMessageType().equals(MessageType.UPDATEFAITHPOINTS) && !message.getMessageType().equals(MessageType.UPDATESLOTDEVCARDS))
        nextState(message);

    }


    private void setMarbleBuffer(MessageExtractedMarbles message) {
        board.setMarbleBuffer(message.getMarblesList());
    }

    private void initialization(MessageStartGame message) {
        List<String> PlayerList = message.getPlayersNameList();
        List<List<String>> AllLeaderCards= message.getLeaderCardsToChoose();
        String[][][] devCardDeck = message.getDevCardDeckMethod();
        String[][] marketTray= message.getMarketTray();
        String remainingMarble= message.getRemainingMarble();

        List<String> LeaderCard= AllLeaderCards.get(PlayerList.indexOf(board.getNickname()));

        board.initialization(PlayerList,LeaderCard,devCardDeck,marketTray,remainingMarble);
    }

    private void updateCurrentPlayer(MessageUpdateCurrPlayer message){
        board.setCurrentPlayer(message.getNickname());
    }

    private void updateFaithPoint(MessageUpdateFaithMarker message){
        board.setFaithMarker(message.getFaithPoints());
    }

    private void updateMarketTray(MessageUpdateMarketTray message){
        char direction= message.getDirection();
        int num=message.getNum();
        board.updateMarketTray(direction,num);
    }

    private void updateStrongbox(MessageUpdateStrongbox message){
        board.setStrongbox(message.getStrongbox());
    }

    private void updateWarehouse(MessageUpdateWarehouse message){
        String[][] warehouse= message.getWarehouse();
        Map<String,Integer> extraChest= message.getExtraChest();
        board.setWarehouse(warehouse,extraChest);
    }

    private void updateDevCardDeck(MessageDeleteDevCard message){
        board.removeCardfromDevCardDeck(message.getDevCardID());
    }

    private void updateWhiteMarbleEffect(MessageUpdateWhiteMarbleEffect message){
        board.setWhiteMarbleEffectList(message.getWhiteMarbleEffectList());
    }

    private void updateSinglePlyaer(MessageUpdateSinglePlayerGame message){
        String[][][] devcarddeck= message.getDevCardDeck();
        int blackcrosstoken= message.getBlackCrossToken();
        String token= message.getID();
        board.singlePlayerUpdate(devcarddeck,blackcrosstoken,token);
    }

    private void updateResources(MessageUpdateResources message){
        String[][] warehouse = message.getWarehouse();
        Map<String, Integer> extraChest = message.getExtraChest();
        Map<String,Integer> strongbox = message.getStrongbox();
        board.updateresoruces(warehouse,extraChest,strongbox);
    }

    private void updateStateLeaderAction(MessageUpdateStateLeaderAction message) {
        String ID =message.getID();
        if(message.isActivated()){
            board.getLeaderActionMap().get(ID).setActivated();
        }
        else{
            board.getLeaderCard().remove(ID);
        }
    }
*/
}
