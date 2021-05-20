package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Network.Message.UpdateMesssage.*;
import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.Server.UpdateCreator;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GameController {
    private Server server;
    private List<String> playersNames;
    private int numPlayer;
    private Game game=null;
    private static Logger LOGGER= Logger.getLogger(Server.class.getName());

    private final static Object modelLock = new Object();


    public GameController (Server server){
        this.server = server;
        this.playersNames = new ArrayList<>();
        numPlayer=0;
    }

    public void onRecivedMessage(Message message) {
        synchronized (modelLock){
            switch (message.getMessageType()){
                case LOGIN: message.action(this); break;
                case NUMPLAYERS: message.action(this); break;
                default:
                    if(message.getNickname().equals(game.getCurrentPlayer().getNickname()))
                        message.action(this);
                    else
                        server.sendtoPlayer(message.getNickname(),new MessageError("server","This isn't your Turn"));
            }
        }
    }

    public void onLogin(String name){
        if(playersNames.size()==0){
            playersNames.add(name);
            server.sendtoPlayer(name, new MessageRequestNumPlayers());
        }
        else
        {
            if (numPlayer==0 || (numPlayer>0 && playersNames.size()>=numPlayer)){
                server.sendtoPlayer(name, new MessageError("server","Not yet established NumPlayer"));
                server.sendtoPlayer(name, new MessageDisconnect(name));
            }
            else {
                playersNames.add(name);
                if(playersNames.size()==numPlayer) {
                    try {
                        game= new Game(new UpdateCreator(server));
                        for(String player: playersNames){
                            game.addPlayersList(new Player(player));
                        }
                        game.startgame();
                    } catch (IOException e) {
                        server.sendBroadcastMessage(new MessageError("server", "FATAL ERROR: can't Read System File"));
                        LOGGER.severe("FATAL ERROR: can't Read System File");
                        System.exit(0);
                    }
                }
                else
                    server.sendtoPlayer(name, new MessageWaitingForOtherPlayer());
                }
            }
    }

    public void onNumPlayer(int num){
        if(num<1 ||num>4){
            server.sendBroadcastMessage(new MessageError("server", "Number of Player is Not Correct"));
            server.sendBroadcastMessage(new MessageRequestNumPlayers());
        }
        else{
            if(numPlayer==0)
                numPlayer=num;
                try {
                    if(num==1) {
                        game = new SinglePlayerGame(new UpdateCreator(server));
                        game.addPlayersList(new Player(playersNames.get(0)));
                        game.startgame();
                    }
                    else{
                        server.sendBroadcastMessage(new MessageWaitingForOtherPlayer());
                    }

                } catch (IOException e) {
                    server.sendBroadcastMessage(new MessageError("server", "FATAL ERROR: can't Read System File"));
                    LOGGER.severe("FATAL ERROR: can't Read System File");
                    System.exit(0);
                }
        }
    }

    public void disconnect(String name){
        if(game==null){

            playersNames.remove(name);
            if(playersNames.size()==0)
                numPlayer=0;
        }
        else {
            for(int i=0;i<game.getPlayersList().size();i++){
                if(game.getPlayersList().get(i).getNickname().equals(name))
                    game.getPlayersList().get(i).setConnected(false);
            }
        }
    }



    public boolean chooseInitialLeaderCards (int i1, int i2) {
        return game.chooseInitialLeaderCards(i1, i2);
    }

    public boolean chooseInitialResources (List<String> initialResources) {
        return game.chooseInitialResources(initialResources);
    }

    public boolean extractionMarble(char colrowextract, int numextract) {
        return game.extractionMarble(colrowextract, numextract);
    }

    public boolean manageMarble(int choice, int indexWarehouse, String resourceWhiteMarble) {
        return game.manageMarble(choice, indexWarehouse, resourceWhiteMarble);
    }

    public boolean exchangeWarehouse (int row1, int row2) {
        return game.exchangeWarehouse(row1, row2);
    }

    public boolean selectDevCard(String ID, int column){
        return game.selectDevCard(ID, column);
    }

    public boolean payResources (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        return game.payResources(WarehouseRes, StrongboxRes, ExtrachestMap);
    }

    public boolean activeBaseProduction (char r1, String reqRes1, char r2, String reqRes2, String chosenResource) {
        return game.activeBaseProduction(r1, reqRes1, r2, reqRes2, chosenResource);
    }

    public boolean activeLeaderCardProduction (String ID, char r, String resource) {
        return game.activeLeaderCardProduction(ID, r, resource);
    }

    public boolean activeDevCardProduction (int col) {
        return game.activeDevCardProduction(col);
    }


    public boolean endProduction () {
        return game.endProduction();
    }

    public boolean updateLeaderCard (String ID, int choice) {
        return game.updateLeaderCard(ID, choice);
    }

    public void endTurn (){
        game.endTurn();
    }

    public void setGame(int numPlayers, UpdateCreator updateCreator) throws IOException {
        if (numPlayers == 1)
            game = new SinglePlayerGame(updateCreator);
        else if (numPlayers == 2)
            game = new Game(updateCreator);
    }
}