package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.EmptyLeaderCardException;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.model.exceptions.NullPlayerListGameException;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    /**
     * This attribute is the vector that contains the players' references
     */
    private List<Player> playersList;

    /**
     * This attribute is the current player's reference
     */
    private Player currentPlayer;

    /**
     * This attribute indicates if the game is finished
     */
    private boolean finishedGame;

    /**
     * This attribute is the development cards' container. It is implemented like a matrix of pile
     */
    private DevCardsDeck developmentCardDeck;

    /**
     * This attribute is a list of the Leader card
     */
    private LeaderCardDeck leaderCardDeck;

    /**
     * This attribute is a reference to the Market Structure
     */
    private MarketStructure marketStructure;

    /**
     * This attribute is a reference to the Faith track
     */
    private FaithTrack faithTrack;

    private int lastPlayer;

    /**
     * This is the constructor method
     */
    public Game() throws IOException {
        this.currentPlayer = null;
        this.playersList = new ArrayList<>();
        this.leaderCardDeck = new LeaderCardDeck();
        this.developmentCardDeck = new DevCardsDeck();
        this.marketStructure = new MarketStructure();
        this.faithTrack = new FaithTrack();
        this.finishedGame = false;
        this.lastPlayer = -1;
    }

    /**
     * This method returns the current player
     * @return current Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method takes from playersList the current turn's player.
     */
    public void setCurrentPlayer () throws EndGameException {
        int i = this.playersList.indexOf(this.currentPlayer);
        if (finishedGame && lastPlayer == -1)
            lastPlayer = i;
        i++;
        if(i < this.playersList.size())
            this.currentPlayer = this.playersList.get(i);
        else this.currentPlayer = this.playersList.get(0);
        if (finishedGame && i == lastPlayer)
            throw new EndGameException();
    }

    /**
     * This method returns the winner of the game in multiplayer mode.
     * @return winning a player from "playersList"
     */
    public int getWinner (){
        Player playerWinnerMax = this.playersList.get(0);
        for(Player player : this.playersList){
            if(player.getVictoryPoints() > playerWinnerMax.getVictoryPoints()){
                playerWinnerMax = player;
            }
        }
        List<Player> playersWinners = new ArrayList<>();
        for(Player p : this.playersList){
            if(playerWinnerMax.getVictoryPoints() == p.getVictoryPoints()){
                playersWinners.add(p);
            }
        }
        for(Player player : playersWinners){
            if(player.countTotalResources() > playerWinnerMax.countTotalResources())
                playerWinnerMax = player;
        }
        return this.playersList.indexOf(playerWinnerMax);
    }

    /**
     * This method checks if the game is finished
     * @return true if the game is finished
     */
    public boolean isFinishedGame() {
        for(Player player : this.playersList){
            if(player.getFaithMarker() == this.faithTrack.getFaithtracksize() || player.getSlotDevCards().countTotalNumberDevCards() >= 7) {
                finishedGame = true;
                return true;
            }
        }
        return false;
    }

    /**
     * This method initialized the game. It make draw four Leader Cards to each player, that discards two; it extracts the first player and gives to all players the initial resources and faith points
     */
    public void startgame () {
        // Dà 4 carte leader a ogni giocatore. Poi tramite il controller verranno scartate 2 carte per ogni giocatore
        for (Player player : this.playersList) {
            for (int x = 0; x < 4; x++) {
                this.leaderCardDeck.givetoPlayer(0, player);
            }
        }
        // Set the current Player
        Collections.shuffle(this.playersList);
        this.currentPlayer = this.playersList.get(0);
        // Assegno solo i punti fede agli altri giocatori. Le Risorse a scelta vengono selezionate tramite il controller
        for(int i = 2; i < this.playersList.size(); i++){
            try {
                this.playersList.get(i).increasefaithMarker();
            }
            catch (ActiveVaticanReportException ignored) {}
        }
    }


    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        for(Player p : this.playersList){
            p.addVictoryPoints(p.getSlotDevCards().countVictoryPoints() + p.countLeaderActionVictoryPoints() + (p.countTotalResources())/5 + faithTrack.getPlayerPoint(p));
        }
    }

    /**
     * This method adds a Player to playersList
     * @param player that is to add to the players' list
     */
    public void addPlayersList(Player player){
        this.playersList.add(player);
        System.out.println(playersList.size()+ " "+ playersList.get(0).getNickname());
    }

    /**
     * This method returns the marketSructure attribute
     * @return a Market Structure object
     */
    public MarketStructure getMarketStructure(){
        return this.marketStructure;
    }

    /**
     * This method returns hte playerList attribute
     * @return a Player's list
     */
    public List<Player> getPlayersList(){
        return this.playersList;
    }

    /**
     * This method returns the developmentCardDeck attribute
     * @return a DevCardsDeck object
     */
    public DevCardsDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    /**
     * This method returns the LeaderCardDeck attribute
     * @return a LeaderCardDeck object
     */
    public LeaderCardDeck getLeaderCardDeck() {
        return leaderCardDeck;
    }

    /**
     * This method returns the faithTrack attribute
     * @return a FaithTrack object
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public int getBlackCrossToken () {
        return 0;
    }
}
