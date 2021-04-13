package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.exceptions.EmptyLeaderCardException;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.exceptions.NullPlayerListGameException;
import it.polimi.ingsw.game.Game;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerGameTest {

    @Test
    void isFinishedGame25Player() throws IOException, NegativeQuantityExceptions {
        SinglePlayerGame s = new SinglePlayerGame();
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer();

        for(int i=0; i<25; i++) s.getCurrentPlayer().increasefaithMarker();
        assertTrue(s.isFinishedGame());
    }

    @Test
    void isFinishedGame25Lorenzo() throws IOException, NegativeQuantityExceptions {
        SinglePlayerGame s = new SinglePlayerGame();
        Player p = new Player("Giusè");
        s.addPlayersList(p);
        s.setCurrentPlayer();
        s.getLorenzoIlMagnifico().increaseFaithMarker(25);
        assertTrue(s.isFinishedGame());
    }

    @Test
    void isFinishedGameEmptyColumn() throws IOException, NegativeQuantityExceptions {
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
    void givefinalpoints() throws IOException, NegativeQuantityExceptions, NullPlayerListGameException, EmptyLeaderCardException {
        SinglePlayerGame game = new SinglePlayerGame();
        Player player = new Player("Valentina");
        game.addPlayersList(player);
        game.setCurrentPlayer();
        game.startgame();

        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 0));
        game.getLeaderCardDeck().getLeaderCardList(0).setActivated();
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardList(0));
        player.getStrongbox().updateResources(new Coins(),2);

        game.givefinalpoints();
        assertEquals(game.getDevelopmentCardDeck().getDevCards(2, 0).getVictoryPoints() + player.getLeaderActionBox().get(0).getVictoryPoints(), game.getCurrentPlayer().getVictoryPoints());
    }
}