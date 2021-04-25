package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.EmptyLeaderCardException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.NullPlayerListGameException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerGameTest {

    @Test
    void isFinishedGame25Player() throws IOException {
        SinglePlayerGame s = new SinglePlayerGame();
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer();

        for(int i=0; i<25; i++) {
            try {s.getCurrentPlayer().increasefaithMarker();}
            catch (ActiveVaticanReportException activeVaticanReportException) {}
        }
        assertTrue(s.isFinishedGame());
    }

    @Test
    void isFinishedGame25Lorenzo() throws IOException {
        SinglePlayerGame s = new SinglePlayerGame();
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer();
        try {
            s.getLorenzoIlMagnifico().increaseFaithMarker(24);
        } catch (ActiveVaticanReportException e) {}
        assertTrue(s.isFinishedGame());
    }

    @Test
    void isFinishedGameEmptyColumn() throws IOException {
        SinglePlayerGame s = new SinglePlayerGame();
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer();

        for(int i=0; i<3; i++){
            for(int k=0; k<4; k++){
                s.getDevelopmentCardDeck().removeDevCards(i,0);
            }
        }
        assertTrue(s.isFinishedGame());
    }

    @Test
    void startgame() throws IOException, NullPlayerListGameException, EmptyLeaderCardException {

        SinglePlayerGame game = new SinglePlayerGame();
        Player player1 = new Player("Qui");
        game.addPlayersList(player1);
        game.setCurrentPlayer();
        game.startgame();

        assertFalse(player1.getLeaderActionBox().isEmpty());
    }

    @Test
    void givefinalpoints() throws IOException, NegativeQuantityExceptions, NullPlayerListGameException, EmptyLeaderCardException, ActiveVaticanReportException {
        SinglePlayerGame game = new SinglePlayerGame();
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
}