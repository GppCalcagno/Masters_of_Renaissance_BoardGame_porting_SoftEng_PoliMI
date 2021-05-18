package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.Network.Server.UpdateCreator;
import it.polimi.ingsw.model.singleplayer.token.Tokens;


import java.io.IOException;
import java.util.List;

public class SinglePlayerGame extends Game {

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

    private UpdateCreator update;

    private TurnPhase turnPhase;

    /**
     * This is the constructor method
     */
    public SinglePlayerGame(UpdateCreator update) throws IOException {
        super(update);
        this.update = update;
        this.turnPhase = super.getTurnPhase();
        playersList = super.getPlayersList();
        finishedGame = super.getFinishedGame();
        developmentCardDeck = super.getDevelopmentCardDeck();
        marketStructure = super.getMarketStructure();
        leaderCardDeck = super.getLeaderCardDeck();
        faithTrack = super.getFaithTrack();
        lorenzoIlMagnifico = new LorenzoIlMagnifico(developmentCardDeck);
        playerWin = false;
    }

    /**
     * This method returns the current player
     * @return current Player
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method takes from playersvet the current turn's player.
     */
    @Override
    public void setCurrentPlayer () throws EndGameException {
        currentPlayer = playersList.get(0);
        if (isFinishedGame())
            throw new EndGameException();
    }

    /**
     * This method adds a Player to playersList
     * @param player that is to add to the players' list
     */
    @Override
    public void addPlayersList(Player player){
        playersList.add(player);
    }

    /**
     * This method returns the winner of the game between Lorenzo Il Magnifico and the single Player
     * @return 0 if Single Player wins, 1 if Lorenzo Il Magnifico wins
     */
    @Override
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
    @Override
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
    @Override
    public void startgame () {
        // Dà 4 carte leader a ogni giocatore. Poi tramite il controller verranno scartate 2 carte per ogni giocatore
        this.currentPlayer = this.playersList.get(0);
        for (int x = 0; x < 4; x++) {
            leaderCardDeck.givetoPlayer(0, currentPlayer);
        }
        update.onUpdateStartGame(developmentCardDeck.getDevelopmentCardDeck(), playersList, marketStructure.getMarketTray(), marketStructure.getRemainingMarble());
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    @Override
    public void givefinalpoints () {
        currentPlayer.addVictoryPoints(currentPlayer.getSlotDevCards().countVictoryPoints() + currentPlayer.countLeaderActionVictoryPoints() + currentPlayer.countTotalResources()/5 + faithTrack.getPlayerPoint(currentPlayer));
    }

    /**
     * This method plays Lorenzo Il Magnifico's turn
     */
    @Override
    public void playLorenzoTurn () throws ActiveVaticanReportException {
        lorenzoIlMagnifico.drawTokens();
    }

    @Override
    public void endTurn() {
        try {
            playLorenzoTurn();
        } catch (ActiveVaticanReportException e) {
            try {
                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {
                if (isFinishedGame())
                    update.onUpdateGameFinished();
            }
        }
        update.onUpdateSinglePlayer(getBlackCrossToken(), developmentCardDeck.getDevelopmentCardDeck(), lorenzoIlMagnifico.getCurrentToken());
        turnPhase = TurnPhase.DOTURN;
        setCanDoProductionTrue();
        try {
            setCurrentPlayer();
        } catch (EndGameException e) {
            givefinalpoints();
            update.onUpdateWinnerSinglePlayer(playerWin, currentPlayer.getVictoryPoints());
        }
    }

    /**
     * This method returns the marketSructure attribute
     * @return a Market Structure object
     */
    @Override
    public MarketStructure getMarketStructure(){
        return this.marketStructure;
    }

    /**
     * This method returns the developmentCardDeck attribute
     * @return a DevCardsDeck object
     */
    @Override
    public DevCardsDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    /**
     * This method returns the LeaderCardDeck attribute
     * @return a LeaderCardDeck object
     */
    @Override
    public LeaderCardDeck getLeaderCardDeck() {
        return leaderCardDeck;
    }

    @Override
    public List<Player> getPlayersList() {
        return playersList;
    }

    @Override
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    @Override
    public boolean getFinishedGame() {
        return super.getFinishedGame();
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

    public Tokens getCurrentToken () {
        return lorenzoIlMagnifico.getCurrentToken();
    }
}
