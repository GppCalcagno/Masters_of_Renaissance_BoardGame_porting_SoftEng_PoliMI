package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.Network.Server.UpdateCreator.JavaSerUpdateCreator;
import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.singleplayer.token.Tokens;


import java.io.IOException;

public class SinglePlayerGame extends Game {

    /**
     * This attribute is the reference to LorenzoIlMagnifico
     */
    private LorenzoIlMagnifico lorenzoIlMagnifico;


    private boolean playerWin;

    /**
     * This is the constructor method
     */
    public SinglePlayerGame(JavaSerUpdateCreator update) throws IOException {
        super(update);
        lorenzoIlMagnifico = new LorenzoIlMagnifico(getDevelopmentCardDeck());
        playerWin = false;
    }

    /**
     * This method takes from playersvet the current turn's player.
     */
    @Override
    public void setCurrentPlayer () throws EndGameException {
        if (isFinishedGame()) throw new EndGameException();
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
                setFinishedGame(true);
                return true;
            }
            else i++;
        }

        if(lorenzoIlMagnifico.getFaithMarker()>=getFaithTrack().getFaithtracksize()){
            playerWin = false;
            setFinishedGame(true);
            return true;
        }

        if(getCurrentPlayer().getSlotDevCards().countTotalNumberDevCards()>=7 || getCurrentPlayer().getFaithMarker() == getFaithTrack().getFaithtracksize()){
            playerWin = true;
            setFinishedGame(true);
            return true;
        }
        return false;
    }

    /**
     * This method initialized the game. It make draw four Leader Cards to each player, that discards two; it extracts the first player and gives to all players the initial resources and faith points
     */
    @Override
    public void startgame () {
        setDuringGame(true);
        setCurrentPlayer(getPlayersList().get(0));
        for (int x = 0; x < 4; x++) {
            getLeaderCardDeck().givetoPlayer(0, getCurrentPlayer());
        }
        getUpdate().onUpdateStartGame(getDevelopmentCardDeck().getDevelopmentCardDeck(), getPlayersList(), getMarketStructure().getMarketTray(), getMarketStructure().getRemainingMarble());
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */


    /**
     * This method plays Lorenzo Il Magnifico's turn
     */
    @Override
    public void playLorenzoTurn () throws ActiveVaticanReportException {
        lorenzoIlMagnifico.drawTokens();
    }

    /**
     * this method end the turn of the player and start a Lorenzo's turn
     * @param onDisconnect if player disconnects
     */
    @Override
    public void endTurn(boolean onDisconnect) {
        /* DECOMMENTA SE VUOI FAR FINIRE LA PARTITA AL PRIMO ENDTURN;
        setDuringGame(false);
        getUpdate().onUpdateWinnerSinglePlayer(false, getCurrentPlayer().getVictoryPoints());
        return;
        */

        boolean canEndTurn = onDisconnect;
        if(!onDisconnect) {
            if (getCurrentPlayer().getGameState().equals(GameState.INITGAME) && getCurrentPlayer().getLeaderActionBox().size() <= 2 && getCurrentPlayer().getInitialResources() == 0) {
                getCurrentPlayer().setGameState(GameState.INGAME);
                getCurrentPlayer().setTurnPhase(TurnPhase.DOTURN);
                canEndTurn = true;
            } else {
                if (getCurrentPlayer().getTurnPhase().equals(TurnPhase.ENDTURN)) {
                    canEndTurn = true;
                    getCurrentPlayer().setTurnPhase(TurnPhase.DOTURN);
                    getCurrentPlayer().setCanDoProductionTrue();
                }
            }
        }

        if(canEndTurn){
            try {
                if(!onDisconnect )  playLorenzoTurn();
            } catch (ActiveVaticanReportException e) {
                try {
                    getFaithTrack().checkPopeSpace(getPlayersList(), getBlackCrossToken());
                } catch (GameFinishedException gameFinishedException) {
                    if (isFinishedGame())
                        getUpdate().onUpdateGameFinished();
                }
            }
            if(!onDisconnect) getUpdate().onUpdateSinglePlayer(getBlackCrossToken(), getDevelopmentCardDeck().getDevelopmentCardDeck(), lorenzoIlMagnifico.getCurrentToken(), lorenzoIlMagnifico.getCurrentToken().getColor());
            getCurrentPlayer().setCanDoProductionTrue();
            try {
                setCurrentPlayer();
            } catch (EndGameException e) {
                givefinalpoints();
                getUpdate().onUpdateWinnerSinglePlayer(playerWin, getCurrentPlayer().getVictoryPoints());
                setDuringGame(false);
            }
        }
        else{
            getUpdate().onUpdateError(getCurrentPlayer().getNickname(),"Take your turn before you finish it");
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

    @Override
    public void increaseLorenzoFaithtrack() throws ActiveVaticanReportException {
        lorenzoIlMagnifico.increaseFaithMarker(1);
    }
}
