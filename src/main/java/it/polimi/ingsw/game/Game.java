package it.polimi.ingsw.game;

import it.polimi.ingsw.card.DevCardsDeck;
import it.polimi.ingsw.card.LeaderCardDeck;
import it.polimi.ingsw.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * This is the constructor method
     */
    public Game() throws IOException {
       this.playersList = new ArrayList<>();
       this.leaderCardDeck = new LeaderCardDeck();
       this.developmentCardDeck = new DevCardsDeck();
       this.marketStructure = new MarketStructure();
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
     * This method takes from playersvet the current turn's player.
     */
    public void setCurrentPlayer () {
        //to implement
    }

    /**
     * This method returns the winner of the game in multiplayer mode.
     * @return winning player's index from "playersVet"
     */
    public int getWinner (){
        //to implement
        return 0;
    }


    /**
     * This method checks if the game is finished
     * @return true if the game is finished
     */
    public boolean isFinishedGame() {
        //to implement
        return finishedGame;
    }

    /**
     * This method initialized the game. It make draw four Leader Cards to each player, that discards two; it extracts the first player and gives to all players the initial resources and faith points
     */
    public void startgame () {

    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        //to implement (vedi ultima pag regole in "Fine della partita")
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
}

