package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.Network.Server.UpdateCreator.JavaSerUpdateCreator;
import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.Server.UpdateSender.ServerUpdate;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerGameTest {

    @Test
    void isFinishedGame25Player() throws IOException {
        SinglePlayerGame s = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer(p);

        for(int i=0; i<25; i++) {
            try {s.getCurrentPlayer().increasefaithMarker();}
            catch (ActiveVaticanReportException activeVaticanReportException) {}
        }
        assertTrue(s.isFinishedGame());
    }

    @Test
    void isFinishedGame25Lorenzo() throws IOException {
        SinglePlayerGame s = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer(p);
        try {
            s.getLorenzoIlMagnifico().increaseFaithMarker(24);
        } catch (ActiveVaticanReportException e) {}
        assertTrue(s.isFinishedGame());
    }

    @Test
    void isFinishedGameEmptyColumn() throws IOException {
        SinglePlayerGame s = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer(p);

        for(int i=0; i<3; i++){
            for(int k=0; k<4; k++){
                s.getDevelopmentCardDeck().removeDevCards(i,0);
            }
        }
        assertTrue(s.isFinishedGame());
    }

    @Test
    void startgame() throws IOException {

        SinglePlayerGame game = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));
        Player player1 = new Player("Qui");
        game.addPlayersList(player1);
        game.setCurrentPlayer(player1);
        game.startgame();

        assertFalse(player1.getLeaderActionBox().isEmpty());
    }

    @Test
    void givefinalpoints() throws IOException, NegativeQuantityExceptions, ActiveVaticanReportException, GameFinishedException {
        SinglePlayerGame game = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));
        Player player = new Player("Valentina");

        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 0));
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardList(10));
        player.getLeaderActionBox().get(0).setActivated();
        player.getStrongbox().updateResources(new Coins(),5);
        for (int j = 0; j < 7; j++) {
            player.increasefaithMarker();
        }
        int i = game.getDevelopmentCardDeck().getDevCards(2, 0).getVictoryPoints() + player.getLeaderActionBox().get(0).getVictoryPoints() + 1 + 2;

        game.addPlayersList(player);
        game.startgame();
        game.givefinalpoints();
        assertEquals(i, game.getCurrentPlayer().getVictoryPoints());
    }

    @Test
    void playLorenzoOneTurn() throws IOException, ActiveVaticanReportException {
        SinglePlayerGame game = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234) )));
        game.playLorenzoTurn();
        assertTrue(game.getBlackCrossToken()!=0 || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][0][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][1][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][2][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][3][3]==null);

    }

    @Test
    void playLorenzoMultipleturn() throws IOException, ActiveVaticanReportException {
        SinglePlayerGame game = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234) )));
        game.playLorenzoTurn();
        game.playLorenzoTurn();
        game.playLorenzoTurn();
        game.playLorenzoTurn();
        game.playLorenzoTurn();
        assertTrue(game.getBlackCrossToken()!=0 && ( game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][0][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][1][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][2][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][3][3]==null));

    }

    @Test
    void increaseLorenzoFaithTrack() throws IOException, ActiveVaticanReportException {
        SinglePlayerGame game = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234) )));
        game.increaseLorenzoFaithtrack();
        game.increaseLorenzoFaithtrack();
        game.increaseLorenzoFaithtrack();

        assertEquals(game.getBlackCrossToken(), 3);

    }

    @Test
    void endTurnSinglePlayer() throws IOException, EndGameException {
        SinglePlayerGame game = new SinglePlayerGame(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Vanessa Leonardi");
        game.addPlayersList(player1);
        game.setCurrentPlayer(player1);

        game.endTurn(false);

        assertTrue(game.getBlackCrossToken()!=0 || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][0][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][1][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][2][3]==null || game.getDevelopmentCardDeck().getDevelopmentCardDeck()[2][3][3]==null);

    }
}