package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.Server.UpdateCreator;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameController {
    private GameState gameState;

    private Server server;

    private List<String> playersNames;

    private final static Object modelLock = new Object();


    private Game game;

    public GameController (Server server) throws IOException {
        gameState = GameState.LOGIN;
        this.server = server;
        this.playersNames = new ArrayList<>();
    }

    public void onRecivedMessage(Message message) {
        synchronized (modelLock){
            if(message.getNickname().equals(game.getCurrentPlayer()))
            message.action(this);
        }
    }



    public void onLogin(){



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

    public boolean payResourcesToBuyDevCard (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        return game.payResourcesToBuyDevCard(WarehouseRes, StrongboxRes, ExtrachestMap);
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

    public boolean payResourcesForDevCardProduction (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        return game.payResourcesForDevCardProduction(WarehouseRes, StrongboxRes, ExtrachestMap);
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
