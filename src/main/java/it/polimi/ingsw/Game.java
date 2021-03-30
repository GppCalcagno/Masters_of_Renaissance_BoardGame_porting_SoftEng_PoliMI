package it.polimi.ingsw;

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
    private Player playersVet [];

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
     * This attribute is an optional for the Discount effect of Leader card
     */
    public List<Optional<DiscountEffect>> leaderCardEffect; // da controllare

    /**
     * This is the constructor method
     */
    public Game() {
       // to implement
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
        //to implemnt
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        //to implement (vedi ultima pag regole in "Fine della partita")
    }


}

