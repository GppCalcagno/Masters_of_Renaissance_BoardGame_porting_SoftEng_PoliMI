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
     * This attribute is the reference to LorenzoIlMagnifico
     */
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    /**
     * This attribute indicates if the game is finished
     */
    private boolean finishedGame;

    private boolean playerWin;

    /**
     * This is the constructor method
     */
    public SinglePlayerGame(UpdateCreator update) throws IOException {
        super(update);
        lorenzoIlMagnifico = new LorenzoIlMagnifico(getDevelopmentCardDeck());
        playerWin = false;
    }

    /**
     * This method takes from playersvet the current turn's player.
     */
    @Override
    public void setCurrentPlayer () throws EndGameException {
        setCurrentPlayer(getPlayersList().get(0));
        if (isFinishedGame())
            throw new EndGameException();
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
            if(null == getDevelopmentCardDeck().getDevelopmentCardDeck()[0][i][0]){
                playerWin = false;
                finishedGame = true;
                return true;
            }
            else i++;
        }

        if(lorenzoIlMagnifico.getFaithMarker()==getFaithTrack().getFaithtracksize()){
            playerWin = false;
            finishedGame = true;
            return true;
        }

        if(getCurrentPlayer().getSlotDevCards().countTotalNumberDevCards()>=7 || getCurrentPlayer().getFaithMarker() == getFaithTrack().getFaithtracksize()){
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
        setCurrentPlayer(getPlayersList().get(0));
        for (int x = 0; x < 4; x++) {
            getLeaderCardDeck().givetoPlayer(0, getCurrentPlayer());
        }
        getUpdate().onUpdateStartGame(getDevelopmentCardDeck().getDevelopmentCardDeck(), getPlayersList(), getMarketStructure().getMarketTray(), getMarketStructure().getRemainingMarble());
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    @Override
    public void givefinalpoints () {
        getCurrentPlayer().addVictoryPoints(getCurrentPlayer().getSlotDevCards().countVictoryPoints() + getCurrentPlayer().countLeaderActionVictoryPoints() + getCurrentPlayer().countTotalResources()/5 + getFaithTrack().getPlayerPoint(getCurrentPlayer()));
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
                getFaithTrack().checkPopeSpace(getPlayersList(), getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {
                if (isFinishedGame())
                    getUpdate().onUpdateGameFinished();
            }
        }
        getUpdate().onUpdateSinglePlayer(getBlackCrossToken(), getDevelopmentCardDeck().getDevelopmentCardDeck(), lorenzoIlMagnifico.getCurrentToken());
        setTurnPhase(TurnPhase.DOTURN);
        setCanDoProductionTrue();
        try {
            setCurrentPlayer();
        } catch (EndGameException e) {
            givefinalpoints();
            getUpdate().onUpdateWinnerSinglePlayer(playerWin, getCurrentPlayer().getVictoryPoints());
        }
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
