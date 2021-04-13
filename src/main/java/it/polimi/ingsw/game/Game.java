package it.polimi.ingsw.game;

import it.polimi.ingsw.exceptions.EmptyLeaderCardException;
import it.polimi.ingsw.exceptions.NullPlayerListGameException;
import it.polimi.ingsw.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    /**
     * This attribute is the number of the players
     */
    private int numPlayers;

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

    /**
     * This is the constructor method
     */
    public Game() throws IOException {
        this.numPlayers = 0;
        this.currentPlayer = null;
        this.playersList = new ArrayList<>();
        this.leaderCardDeck = new LeaderCardDeck();
        this.developmentCardDeck = new DevCardsDeck();
        this.marketStructure = new MarketStructure();
        this.faithTrack = new FaithTrack();
        this.finishedGame = false;
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
    public void setCurrentPlayer () {
        int i = this.playersList.indexOf(this.currentPlayer);
        i++;
        if(i < this.playersList.size())
            this.currentPlayer = this.playersList.get(i);
        else this.currentPlayer = this.playersList.get(0);
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
            if(player.getFaithMarker() == this.faithTrack.getFaithtracksize() || player.getSlotDevCards().countTotalNumberDevCards() >= 7)
                return true;
        }
        return false;
    }

    /**
     * This method initialized the game. It make draw four Leader Cards to each player, that discards two; it extracts the first player and gives to all players the initial resources and faith points
     */
    public void startgame () throws NullPlayerListGameException, EmptyLeaderCardException {
        // Dà 4 carte leader a ogni giocatore. Poi tramite il controller verranno scartate 2 carte per ogni giocatore
        if(this.playersList.isEmpty())
            throw new NullPlayerListGameException();
        else {
            if(this.leaderCardDeck.getLeaderCardList().isEmpty())
                throw new EmptyLeaderCardException();
            else{
                for(int i = 0; i < this.playersList.size(); i++){
                    for(int x = 0; x < 4; x++){
                        this.leaderCardDeck.givetoPlayer(0, this.playersList.get(i));
                    }
                }
                // Set the current Player
                Collections.shuffle(this.playersList);
                this.currentPlayer = this.playersList.get(0);
                // Assegno solo i punti fede agli altri giocatori. Le Risorse a scelta vengono selezionate tramite il controller
                for(int i = 2; i < this.playersList.size(); i++){
                    this.playersList.get(i).increasefaithMarker();
                }
            }
        }
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        for(Player p : this.playersList){
            p.addVictoryPoints(p.getSlotDevCards().countVictoryPoints() + p.countLeaderActionVictoryPoints() + p.countTotalResources()/5 + faithTrack.getPlayerPoint(p));
        }
    }

    /**
     * This method adds a Player to playersList
     * @param player that is to add to the players' list
     */
    public void addPlayersList(Player player){
        this.playersList.add(player);
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
}
