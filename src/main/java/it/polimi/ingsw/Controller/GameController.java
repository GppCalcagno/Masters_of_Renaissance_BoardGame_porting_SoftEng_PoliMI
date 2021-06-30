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

    /**
     * this method lock the model and proceed with the logic of the game doing the action
     * @param message a serialized message that indicates which action have to be done
     */
    public void onRecivedMessage(Message message) {
        synchronized (modelLock){
            message.action(this);
        }
    }

    /**
     * this method set the name of the player and check there isn't someone else with the same name
     * @param name
     */
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

    /**
     * this method check the name of the player during the game phase
     * @param name
     */
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

    /**
     * this method add players at the game before start it
     * @param name name of the player that is logging
     */
    public void onLoginBeforeGame(String name) {
        if(isOnline) {
            if (playersNames.size() == 0) {
                playersNames.add(name);
                updateCreator.onRequestNumPlayer(name);
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

    /**
     * this mehtod check the number of players and create a game with that
     * @param name name of the player
     * @param num number that first player insert
     */
    public void onNumPlayer(String name, int num){
        if(num<1 ||num>4){
            updateCreator.onUpdateError("Number of Player is Not Correct");
            updateCreator.onRequestNumPlayer(name);
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
                        updateCreator.onWaitingForOtherPlayer(name);
                    }
                } catch (IOException e) {
                    updateCreator.onUpdateError("FATAL ERROR: can't Read System File");
                    LOGGER.severe("FATAL ERROR: can't Read System File");
                    System.exit(0);
                }
        }
    }

    /**
     * this method allow the disconnection of a player
     * @param name player's name
     */
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

    /**
     * this method ask the game to show other player, in order for the player to be able to visualize his opponents
     * @param otherPlayer
     */
    public void onReqOtherPlayer(String otherPlayer){
        game.onReqOtherPlayer(otherPlayer);
    }


    /**
     * this method allow the reconnection and resume the game where player left it
     * @param player
     */
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


    /**
     * this method allows a player at the start of the game to choose his leadercards
     * @param i1 first index
     * @param i2 second index
     * @return true if index are correct
     */
    public boolean chooseInitialLeaderCards (int i1, int i2) {
        return game.chooseInitialLeaderCards(i1, i2);
    }

    /**
     * this method allows a player at the start of the game, if he has to, to choose his resources
     * @param initialResources one or more resources
     * @return true if resources are correct
     */
    public boolean chooseInitialResources (List<String> initialResources) {
        return game.chooseInitialResources(initialResources);
    }

    /**
     * this method allows to do an extraction
     * @param colrowextract column or row
     * @param numextract index of column or row
     * @return true if is successful
     */
    public boolean extractionMarble(char colrowextract, int numextract) {
        return game.extractionMarble(colrowextract, numextract);
    }

    /**
     * this method allows the player to manage his marbles extracted
     * @param choice add or discard
     * @param indexWarehouse where store them in the warehouse
     * @param resourceWhiteMarble if leadercard is active player can choose in what transform his white marbles
     * @return true if is successful
     */
    public boolean manageMarble(char choice, int indexWarehouse, String resourceWhiteMarble) {
        return game.manageMarble(choice, indexWarehouse, resourceWhiteMarble);
    }

    /**
     * this method allows to exchange two rows of the warehouse in accord with the rules
     * @param row1 first row
     * @param row2 second row
     * @return true if is successful
     */
    public boolean exchangeWarehouse (int row1, int row2) {
        return game.exchangeWarehouse(row1, row2);
    }

    /**
     * select a dev card to buy it
     * @param ID card's id
     * @param column column of the deck
     * @return true if is successful
     */
    public boolean selectDevCard(String ID, int column){
        return game.selectDevCard(ID, column);
    }

    /**
     * this method allows player to pay for buying devcard
     * @param WarehouseRes a map of the resources use for the payment that came from the warehouse
     * @param StrongboxRes a map of the resources use for the payment that came from the strongbox
     * @param ExtrachestMap a map of the resources use for the payment that came from the extrachest
     * @return true if is successful
     */
    public boolean payResources (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        return game.payResources(WarehouseRes, StrongboxRes, ExtrachestMap);
    }

    /**
     * this method allow to activate a base production
     * @param r1 first structure where take the resource
     * @param reqRes1 first resource
     * @param r2 second structure where take the resource
     * @param reqRes2 second resource
     * @param chosenResource the resource player wants
     * @return true if is successful
     */
    public boolean activeBaseProduction (char r1, String reqRes1, char r2, String reqRes2, String chosenResource) {
        return game.activeBaseProduction(r1, reqRes1, r2, reqRes2, chosenResource);
    }

    /**
     * this method allow to activate a leader card production
     * @param ID card's id
     * @param r structure where take the resource
     * @param resource resource wanted
     * @return true if is successful
     */
    public boolean activeLeaderCardProduction (String ID, char r, String resource) {
        return game.activeLeaderCardProduction(ID, r, resource);
    }

    /**
     * active dev card production
     * @param col column of slot dev card where the card is stored
     * @return true if is successful
     */
    public boolean activeDevCardProduction (int col) {
        return game.activeDevCardProduction(col);
    }


    /**
     * end of the production, needed to empty the buffer
     * @return true if is successful
     */
    public boolean endProduction () {
        return game.endProduction();
    }

    /**
     * this method change the state of a leadercard
     * @param ID card's id
     * @param choice activate or discard
     * @return true if is successful
     */
    public boolean updateLeaderCard (String ID, int choice) {
        return game.updateLeaderCard(ID, choice);
    }

    /**
     * this method is needed to end the turn
     * @param onDisconnect
     */
    public void endTurn (boolean onDisconnect){
        game.endTurn(onDisconnect);
    }

    public void cheat() {
        game.cheat();
    }
}
