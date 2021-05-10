package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.player.Player;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SinglePlayerGame extends Game {
    /**
     * This attribute is the number of the players
     */
    private int numPlayers;

    /**
     * This attribute is the vector that contains the players' references
     */
    private List<Player> playersList;

    /**
     * This attribute is the reference to LorenzoIlMagnifico
     */
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    /**
     * This attribute is the current player's reference
     */
    private Player currentPlayer;

    /**
     * This attribute indicates if the game is finished
     */
    private boolean finishedGame;

    private boolean playerWin;

    /**
     * This attribute is the development cards' container. It is implemented like a matrix of pile
     */
    private DevCardsDeck developmentCardDeck;

    /**
     * This attribute is a list of the Leader card
     */
    private LeaderCardDeck leaderCardDeck;

    /**
     * This attribute is a reference to the Faith track
     */
    private FaithTrack faithTrack;

    /**
     * This attribute is a reference to the Market Structure
     */
    private MarketStructure marketStructure;

    /**
     * This is the constructor method
     */
    public SinglePlayerGame() throws IOException {
        numPlayers = 0;
        playersList = new ArrayList<>();
        finishedGame = false;
        developmentCardDeck = new DevCardsDeck();
        marketStructure = new MarketStructure();
        leaderCardDeck = new LeaderCardDeck();
        faithTrack = new FaithTrack();
        lorenzoIlMagnifico = new LorenzoIlMagnifico(developmentCardDeck);
        playerWin = false;
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
    public void setCurrentPlayer () throws EndGameException {
        currentPlayer = playersList.get(0);
        if (isFinishedGame())
            throw new EndGameException();
    }

    /**
     * This method adds a Player to playersList
     * @param player that is to add to the players' list
     */
    public void addPlayersList(Player player){
        playersList.add(player);
    }

    /**
     * This method returns the winner of the game between Lorenzo Il Magnifico and the single Player
     * @return 0 if Single Player wins, 1 if Lorenzo Il Magnifico wins
     */
    public int getWinner () {
        if(isFinishedGame()){
            if(playerWin) return 0;
            else return -1;
        }
        return -2;
    }

    /**
     * This method checks if the game is finished
     * @return true if the game is finished
     */
    public boolean isFinishedGame() {
        int i = 0;
        while(i< ColorCard.values().length){
            if(null == developmentCardDeck.getDevelopmentCardDeck()[0][i][0]){
                playerWin = false;
                finishedGame = true;
                return true;
            }
            else i++;
        }

        if(lorenzoIlMagnifico.getFaithMarker()==faithTrack.getFaithtracksize()){
            playerWin = false;
            finishedGame = true;
            return true;
        }

        if(currentPlayer.getSlotDevCards().countTotalNumberDevCards()>=7 || currentPlayer.getFaithMarker() == faithTrack.getFaithtracksize()){
            playerWin = true;
            finishedGame = true;
            return true;
        }
        return false;
    }

    /**
     * This method initialized the game. It make draw four Leader Cards to each player, that discards two; it extracts the first player and gives to all players the initial resources and faith points
     */
    public void startgame () {
        // Dà 4 carte leader a ogni giocatore. Poi tramite il controller verranno scartate 2 carte per ogni giocatore
        this.currentPlayer = this.playersList.get(0);
        for (int x = 0; x < 4; x++) {
            leaderCardDeck.givetoPlayer(0, currentPlayer);
        }
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        currentPlayer.addVictoryPoints(currentPlayer.getSlotDevCards().countVictoryPoints() + currentPlayer.countLeaderActionVictoryPoints() + currentPlayer.countTotalResources()/5 + faithTrack.getPlayerPoint(currentPlayer));
    }

    /**
     * This method plays Lorenzo Il Magnifico's turn
     */
    public void playLorenzoTurn () throws ActiveVaticanReportException {
        lorenzoIlMagnifico.drawTokens();
    }

    /**
     * This method returns the marketSructure attribute
     * @return a Market Structure object
     */
    public MarketStructure getMarketStructure(){
        return this.marketStructure;
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
     * This method returns the LorenzoIlMagnifico attribute
     * @return a LorenzoIlMagnifico object
     */
    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public int getBlackCrossToken () {
        return lorenzoIlMagnifico.getFaithMarker();
    }

    public String getCurrentToken () {
        return lorenzoIlMagnifico.getCurrentToken();
    }
}
