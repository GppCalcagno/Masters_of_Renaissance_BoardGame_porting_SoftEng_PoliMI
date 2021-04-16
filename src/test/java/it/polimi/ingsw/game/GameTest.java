package it.polimi.ingsw.game;

import it.polimi.ingsw.exceptions.EmptyLeaderCardException;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.exceptions.NullPlayerListGameException;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Shields;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setCurrentPlayer() throws IOException, EmptyLeaderCardException, NullPlayerListGameException {
        Game game = new Game();
        Player player1 = new Player("Rene");
        Player player2 = new Player("Caterina");

        game.addPlayersList(player1);
        game.addPlayersList(player2);
        game.startgame();
        player1 = game.getPlayersList().get(0);
        player2 = game.getPlayersList().get(1);
        assertEquals(player1, game.getCurrentPlayer());
        game.setCurrentPlayer();
        assertEquals(player2, game.getCurrentPlayer());
        game.setCurrentPlayer();
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    void getWinnerWithoutSame() throws IOException {
        Game game = new Game();
        Player player1 = new Player("Qui");
        Player player2 = new Player("Quo");
        Player player3 = new Player("Qua");
        Player player4 = new Player("Pluto");
        player1.addVictoryPoints(1);
        player2.addVictoryPoints(4);
        player3.addVictoryPoints(3);
        player4.addVictoryPoints(2);
        game.addPlayersList(player1);
        game.addPlayersList(player2);
        game.addPlayersList(player3);
        game.addPlayersList(player4);

        assertEquals(1, game.getWinner());
    }

    @Test
    void getWinnerWithSame() throws IOException, NegativeQuantityExceptions {
        Game game = new Game();
        Player player1 = new Player("Qui");
        Player player2 = new Player("Quo");
        Player player3 = new Player("Qua");
        Player player4 = new Player("Pluto");
        player1.addVictoryPoints(1);
        player2.addVictoryPoints(4);
        player3.addVictoryPoints(4);
        player4.addVictoryPoints(2);
        player2.getStrongbox().updateResources(new Shields(),3);
        player3.getStrongbox().updateResources(new Coins(),2);
        game.addPlayersList(player1);
        game.addPlayersList(player2);
        game.addPlayersList(player3);
        game.addPlayersList(player4);

        assertEquals(1, game.getWinner());
    }

    @Test
    void isFinishedGame25() throws IOException {
        Game game = new Game();
        Player player = new Player("Augusto");
        for(int i = 0; i < 25; i++){
            player.increasefaithMarker();
        }
        game.addPlayersList(player);

        assertTrue(game.isFinishedGame());
    }

    @Test
    void isFinishedGame7() throws IOException {
        Game game = new Game();
        Player player = new Player("Ottaviano");
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 0));
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(1, 0));
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(0, 0));
        player.getSlotDevCards().insertCards(1, game.getDevelopmentCardDeck().getDevCards(2, 0));
        player.getSlotDevCards().insertCards(1, game.getDevelopmentCardDeck().getDevCards(1, 0));
        player.getSlotDevCards().insertCards(1, game.getDevelopmentCardDeck().getDevCards(0, 0));
        player.getSlotDevCards().insertCards(2, game.getDevelopmentCardDeck().getDevCards(2, 0));

        game.addPlayersList(player);

        assertTrue(game.isFinishedGame());
    }

    @Test
    void stargameEmpty() throws IOException {
        Game game = new Game();
        try {
            game.startgame();
            fail();
        } catch (NullPlayerListGameException e) {
            assertTrue(true);
        } catch (EmptyLeaderCardException e) {
            fail();
        }

    }
    @Test
    void startgameLeaderActionBox() throws IOException, EmptyLeaderCardException, NullPlayerListGameException {
        Game game = new Game();
        Player player1 = new Player("Qui");
        Player player2 = new Player("Quo");
        Player player3 = new Player("Qua");
        Player player4 = new Player("Pluto");
        game.addPlayersList(player1);
        game.addPlayersList(player2);
        game.addPlayersList(player3);
        game.addPlayersList(player4);

        game.startgame();

        for(Player p : game.getPlayersList()){
            assertFalse(p.getLeaderActionBox().isEmpty());
        }
    }

    @Test
    void startgameIncreaseFaithMarker4() throws IOException, EmptyLeaderCardException, NullPlayerListGameException {
        Game game = new Game();
        Player player1 = new Player("Qui");
        Player player2 = new Player("Quo");
        Player player3 = new Player("Qua");
        Player player4 = new Player("Pluto");
        game.addPlayersList(player1);
        game.addPlayersList(player2);
        game.addPlayersList(player3);
        game.addPlayersList(player4);

        game.startgame();

        assertEquals(1, game.getPlayersList().get(2).getFaithMarker());
        assertEquals(1, game.getPlayersList().get(3).getFaithMarker());
    }

    @Test
    void startgameIncreaseFaithMarker2() throws IOException, EmptyLeaderCardException, NullPlayerListGameException {
        Game game = new Game();
        Player player1 = new Player("Qui");
        Player player2 = new Player("Quo");
        game.addPlayersList(player1);
        game.addPlayersList(player2);

        game.startgame();

        assertEquals(0, game.getPlayersList().get(0).getFaithMarker());
        assertEquals(0, game.getPlayersList().get(1).getFaithMarker());
    }

    @Test
    void givefinalpoints() throws IOException, NegativeQuantityExceptions, EmptyLeaderCardException, NullPlayerListGameException {
        Game game = new Game();
        Player player = new Player("Caterina");

        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 0));
        game.getLeaderCardDeck().getLeaderCardList(0).setActivated();
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardList(0));
        player.getStrongbox().updateResources(new Coins(),2);
        int i = game.getDevelopmentCardDeck().getDevCards(2, 0).getVictoryPoints() + player.getLeaderActionBox().get(0).getVictoryPoints();

        game.addPlayersList(player);
        game.givefinalpoints();
        assertEquals(i, game.getPlayersList().get(0).getVictoryPoints());
    }

    @Test
    void givefinalpoints0() throws IOException, EmptyLeaderCardException, NullPlayerListGameException {
        Game game = new Game();
        Player player = new Player("Caterina");

        game.addPlayersList(player);
        game.startgame();
        game.givefinalpoints();
        assertEquals(0, game.getPlayersList().get(0).getVictoryPoints());
    }

    @Test
    void addPlayersList() throws IOException {
        Game game = new Game();
        Player player1 = new Player("Anna");

        assertTrue(game.getPlayersList().isEmpty());
        game.addPlayersList(player1);
        assertFalse(game.getPlayersList().isEmpty());
    }
}