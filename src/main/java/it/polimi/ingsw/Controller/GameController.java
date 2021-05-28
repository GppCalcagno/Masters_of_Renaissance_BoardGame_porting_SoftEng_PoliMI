package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Server.*;
import it.polimi.ingsw.Network.Server.UpdateCreator.JavaSerUpdateCreator;
import it.polimi.ingsw.Network.Server.UpdateCreator.UpdateCreator;
import it.polimi.ingsw.Network.Server.UpdateSender.SenderUpdateInterface;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GameController {
    private UpdateCreator updateCreator;
    private SenderUpdateInterface sender;
    private List<String> playersNames;
    private int numPlayer;
    private Game game;
    private boolean isOnline;

    private static Logger LOGGER= Logger.getLogger(Server.class.getName());

    private final static Object modelLock = new Object();


    public GameController (SenderUpdateInterface sender,boolean isOnline){
        this.isOnline=isOnline;
        this.sender=sender;
        updateCreator= new JavaSerUpdateCreator(sender);
        this.playersNames = new ArrayList<>();
        numPlayer=0;

        try {
            this.game=new Game(updateCreator); //mi serve solo per fare all'inizio game.isduringgame
        } catch (IOException ioException) {
            LOGGER.severe("FATAL ERROR: can't Read System File");
            System.exit(0);
        }

    }

    public void onRecivedMessage(Message message) {
        synchronized (modelLock){
            message.action(this);
        }
    }

    public void onLoginPhase(String name){
        //checking on the names of online players is done by the server because the name is used as an
        // ID to identify the correct handler. There cannot be 2 clients with the same handler!
        if(game.isDuringGame()){
            onLoginDuringGame(name);
        }
        else{
            onLoginBeforeGame(name);
        }
    }

    public void onLoginDuringGame(String name){
        int i=0;

        while (i < game.getPlayersList().size() && !game.getPlayersList().get(i).getNickname().equals(name)) i++;
        if(i==game.getPlayersList().size()){
            updateCreator.onUpdateError(name, "Game Already Started! Disconnecting...");
            updateCreator.onRequestDisconnect(name);
        }
        else
        {
            onResumeRequest(game.getPlayersList().get(i));
        }

    }


    public void onLoginBeforeGame(String name) {
        if(isOnline) {
            if (playersNames.size() == 0) {
                playersNames.add(name);
                updateCreator.onRequestNumPlayer();
            } else {
                if (numPlayer == 0 || (numPlayer > 0 && playersNames.size() >= numPlayer)) {
                    updateCreator.onUpdateError(name, "Inconsistent number of players. Disconnecting...");
                    updateCreator.onRequestDisconnect(name);
                } else {
                    playersNames.add(name);
                    if (playersNames.size() == numPlayer) {
                        synchronized (modelLock) {
                            for (String player : playersNames) {
                                game.addPlayersList(new Player(player));
                            }
                            game.startgame();
                        }
                    } else
                        updateCreator.onWaitingForOtherPlayer(name);
                }
            }
        }
        else
        {
            playersNames.add(name);
            try {
                game = new SinglePlayerGame(new JavaSerUpdateCreator(sender));
                game.addPlayersList(new Player(playersNames.get(0)));
                game.startgame();
            } catch (IOException ioException) {
                updateCreator.onUpdateError("FATAL ERROR: can't Read System File");
                LOGGER.severe("FATAL ERROR: can't Read System File");
                System.exit(0);
            }


        }
    }

    public void onNumPlayer(int num){
        if(num<1 ||num>4){
            updateCreator.onUpdateError("Number of Player is Not Correct");
            updateCreator.onRequestNumPlayer();
        }
        else{
            if(numPlayer==0)
                numPlayer=num;
                try {
                    if(num==1) {
                            game = new SinglePlayerGame(new JavaSerUpdateCreator(sender));
                            game.addPlayersList(new Player(playersNames.get(0)));
                            game.startgame();
                    }
                    else{
                        game = new Game(new JavaSerUpdateCreator(sender));
                        updateCreator.onWaitingForOtherPlayer();
                    }
                } catch (IOException e) {
                    updateCreator.onUpdateError("FATAL ERROR: can't Read System File");
                    LOGGER.severe("FATAL ERROR: can't Read System File");
                    System.exit(0);
                }
        }
    }

    public void disconnect(String name) {
        if (!game.isDuringGame()) {
            playersNames.remove(name);
            if (playersNames.size() == 0)
                numPlayer = 0;
        } else {
            synchronized (modelLock) {
                int i = 0;
                while (i < game.getPlayersList().size()-1 && !game.getPlayersList().get(i).getNickname().equals(name)) i++;

                if (game.getPlayersList().get(i).getNickname().equals(name)) {
                    game.getPlayersList().get(i).setConnected(false);
                    if (game.getCurrentPlayer().getNickname().equals(name)){
                        endTurn(true);
                    }
                     updateCreator.onUpdatePlayerDisconnected(game.getPlayersList().get(i));
                }
            }
        }
    }



    public void onResumeRequest(Player player){
        boolean isSomeoneOnline=false;

        for(Player p: game.getPlayersList()){
            if (p.getConnected()) {
                isSomeoneOnline = true;
                break;
            }
        }
        player.setConnected(true);
        if(!isSomeoneOnline)
            endTurn(true);

       updateCreator.onRequestResume(game.getPlayersList(),player,game.getCurrentPlayer(),game.getDevelopmentCardDeck().getDevelopmentCardDeck()
        ,game.getMarketStructure().getMarketTray(),game.getMarketStructure().getRemainingMarble(),game.getBlackCrossToken());

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

    public boolean manageMarble(char choice, int indexWarehouse, String resourceWhiteMarble) {
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

    public void endTurn (boolean onDisconnect){
        game.endTurn(onDisconnect);
    }

    public void fakeTaxi() {
        game.fakeTaxi();
    }
}
