package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.EmptyLeaderCardException;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.exceptions.NullPlayerListGameException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.producible.Servants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiovanniControllerTest {

    @Test
    void takeResourcesMarketRight() throws IOException {
        GiovanniController giovanniController = new GiovanniController();

        assertTrue(giovanniController.VerifyNumPlayers(1));
        assertTrue(giovanniController.TakeResourcesMarket('c', 1));
    }

    @Test
    void takeResourcesMarketWrongColRow() throws IOException {
        GiovanniController giovanniController = new GiovanniController();

        assertTrue(giovanniController.VerifyNumPlayers(1));
        assertFalse(giovanniController.TakeResourcesMarket('n', 2));
    }

    @Test
    void takeResourcesMarketWrongNum() throws IOException {
        GiovanniController giovanniController = new GiovanniController();

        assertTrue(giovanniController.VerifyNumPlayers(1));
        assertFalse(giovanniController.TakeResourcesMarket('r', 4));
    }

    @Test
    void AddMarble() throws IOException, GameFinishedException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException {
        GiovanniController giovanniController = new GiovanniController();
        Player player = new Player("Stanis");
        assertTrue(giovanniController.VerifyNumPlayers(1));
        giovanniController.getGame().addPlayersList(player);
        giovanniController.getGame().startgame();

        assertTrue(giovanniController.TakeResourcesMarket('c', 1));
        assertTrue(giovanniController.AddDiscardMarble(true, 2));
    }

    @Test
    void DiscardMarble() throws IOException, GameFinishedException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException {
        GiovanniController giovanniController = new GiovanniController();
        Player player1 = new Player("Gianfranco");
        Player player2 = new Player("Fini");
        assertTrue(giovanniController.VerifyNumPlayers(2));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().addPlayersList(player2);
        giovanniController.getGame().startgame();

        assertTrue(giovanniController.TakeResourcesMarket('c', 1));
        assertTrue(giovanniController.AddDiscardMarble(false, 0));
    }

    @Test
    void exchangeWarehouse() throws IOException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException, GameFinishedException {
        GiovanniController giovanniController = new GiovanniController();
        Player player1 = new Player("Gianfranco");
        assertTrue(giovanniController.VerifyNumPlayers(1));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().startgame();

        assertTrue(giovanniController.TakeResourcesMarket('c', 1));
        assertTrue(giovanniController.AddDiscardMarble(true, 0));
        assertTrue(giovanniController.ExchangeWarehouse(0, 1));
    }

    @Test
    void verifyNumPlayersRight() throws IOException {
        GiovanniController giovanniController = new GiovanniController();
        assertTrue(giovanniController.VerifyNumPlayers(3));
        assertEquals(3, giovanniController.getNumPlayersCount());
    }

    @Test
    void verifyNumPlayersWrong() throws IOException {
        GiovanniController giovanniController = new GiovanniController();
        assertFalse(giovanniController.VerifyNumPlayers(5));
        assertEquals(0, giovanniController.getNumPlayersCount());
    }

    @Test
    void chooseLeaderCards() throws IOException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException {
        GiovanniController giovanniController = new GiovanniController();
        Player player1 = new Player("Gianfranco");
        assertTrue(giovanniController.VerifyNumPlayers(2));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().startgame();

        assertEquals(4, giovanniController.getGame().getCurrentPlayer().getLeaderActionBox().size());
        assertTrue(giovanniController.ChooseLeaderCards(0, 2));
        assertEquals(2, giovanniController.getGame().getCurrentPlayer().getLeaderActionBox().size());
    }

    @Test
    void chooseResourcesOne() throws IOException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException {
        GiovanniController giovanniController = new GiovanniController();
        Player player1 = new Player("Gianfranco");
        assertTrue(giovanniController.VerifyNumPlayers(1));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().startgame();
        List<Resources> resourcesList = new ArrayList<>();
        Resources coin = new Coins();
        Resources servant = new Servants();
        resourcesList.add(coin);
        resourcesList.add(servant);

        assertFalse(giovanniController.ChooseResources(resourcesList));
        assertEquals(0, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(coin));
        assertEquals(0, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(servant));
    }

    @Test
    void chooseResourcesTwo() throws IOException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException {
        GiovanniController giovanniController = new GiovanniController();
        Player player1 = new Player("Gianfranco");
        Player player2 = new Player("Fini");
        assertTrue(giovanniController.VerifyNumPlayers(2));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().addPlayersList(player2);
        giovanniController.getGame().startgame();
        giovanniController.getGame().setCurrentPlayer();
        List<Resources> resourcesList = new ArrayList<>();
        Resources coin = new Coins();
        Resources servant = new Servants();
        resourcesList.add(coin);
        resourcesList.add(servant);

        assertTrue(giovanniController.ChooseResources(resourcesList));
        assertEquals(1, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(coin));
        assertEquals(0, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(servant));
    }

    @Test
    void chooseResourcesFour() throws IOException, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException {
        GiovanniController giovanniController = new GiovanniController();
        Player player1 = new Player("Gianfranco");
        Player player2 = new Player("Fini");
        Player player3 = new Player("Fulvio");
        Player player4 = new Player("Fusi");
        assertTrue(giovanniController.VerifyNumPlayers(4));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().addPlayersList(player2);
        giovanniController.getGame().addPlayersList(player3);
        giovanniController.getGame().addPlayersList(player4);
        giovanniController.getGame().startgame();
        for (int i = 0; i < 3; i++) {
            giovanniController.getGame().setCurrentPlayer();
        }
        List<Resources> resourcesList = new ArrayList<>();
        resourcesList.add(new Coins());
        resourcesList.add(new Servants());

        Resources coin = new Coins();
        Resources servant = new Servants();
        assertTrue(giovanniController.ChooseResources(resourcesList));
        assertEquals(1, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(coin));
        assertEquals(1, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(servant));
    }
}

