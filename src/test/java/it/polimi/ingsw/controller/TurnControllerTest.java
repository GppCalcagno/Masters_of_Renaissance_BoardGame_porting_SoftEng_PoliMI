package it.polimi.ingsw.controller;

import Stub.LeaderCardDeckStub;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {

    @Test
    void selectDevCard() throws IOException, NegativeQuantityExceptions, EndGameException {
       TurnController t = new TurnController();

        Player p = new Player("Casseruola");
        assertTrue(t.VerifyNumPlayers(1));
        t.getGame().addPlayersList(p);
        t.getGame().setCurrentPlayer();

        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Coins(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Servants(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Shields(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Stones(), 100);

        assertTrue(t.selectDevCard(2,0));
        assertTrue(t.selectDevCard(2,1));
        assertTrue(t.selectDevCard(2,2));
    }

    @Test
    void insertCard() throws IOException, NegativeQuantityExceptions, GameFinishedException, EndGameException {
        TurnController t = new TurnController();

        Player p = new Player("Casseruola");
        assertTrue(t.VerifyNumPlayers(1));
        t.getGame().addPlayersList(p);
        t.getGame().setCurrentPlayer();

        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Coins(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Servants(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Shields(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Stones(), 100);

        t.selectDevCard(2,0);
        assertTrue(t.insertCard(0));
        t.selectDevCard(2,1);
        assertTrue(t.insertCard(1));
        t.selectDevCard(2,2);
        assertTrue(t.insertCard(2));
        t.selectDevCard(1,0);
        assertTrue(t.insertCard(2));
    }

    @Test
    void activeLeaderCardNotActivated() throws IOException, NegativeQuantityExceptions, EmptyLeaderCardException, NullPlayerListGameException, ActiveVaticanReportException, EndGameException {
        TurnController t = new TurnController();
        LeaderCardDeckStub leaderCardDeckStub = new LeaderCardDeckStub();

        Player p = new Player("Cassandra");
        assertTrue(t.VerifyNumPlayers(1));
        p.getStrongbox().updateResources(new Coins(), 5);
        p.addLeaderAction(leaderCardDeckStub.getLeaderCardList(4));
        t.getGame().addPlayersList(p);
        t.getGame().setCurrentPlayer();

        assertTrue(t.activeLeaderAction(0));
    }

    @Test
    void activeLeaderCardActivated() throws IOException, EndGameException {
        TurnController t = new TurnController();
        LeaderCardDeckStub leaderCardDeckStub = new LeaderCardDeckStub();

        Player p = new Player("Cassandra");
        assertTrue(t.VerifyNumPlayers(1));
        //p.getStrongbox().updateResources(new Coins(), 5);
        leaderCardDeckStub.getLeaderCardList(4).setActivated();
        p.addLeaderAction(leaderCardDeckStub.getLeaderCardList(4));
        t.getGame().addPlayersList(p);
        t.getGame().setCurrentPlayer();

        assertTrue(t.activeLeaderAction(0));
    }

    @Test
    void takeResourcesMarketRight() throws IOException {
        TurnController giovanniController = new TurnController();

        assertTrue(giovanniController.VerifyNumPlayers(1));
        assertTrue(giovanniController.TakeResourcesMarket('c', 1));
    }

    @Test
    void takeResourcesMarketWrongColRow() throws IOException {
        TurnController giovanniController = new TurnController();

        assertTrue(giovanniController.VerifyNumPlayers(1));
        assertFalse(giovanniController.TakeResourcesMarket('n', 2));
    }

    @Test
    void takeResourcesMarketWrongNum() throws IOException {
        TurnController giovanniController = new TurnController();

        assertTrue(giovanniController.VerifyNumPlayers(1));
        assertFalse(giovanniController.TakeResourcesMarket('r', 4));
    }

    @Test
    void AddMarble() throws IOException {
        TurnController giovanniController = new TurnController();
        Player player = new Player("Stanis");
        assertTrue(giovanniController.VerifyNumPlayers(1));
        giovanniController.getGame().addPlayersList(player);
        giovanniController.getGame().startgame();

        assertTrue(giovanniController.TakeResourcesMarket('c', 1));
        assertTrue(giovanniController.AddDiscardMarble(true, 2));
    }

    @Test
    void DiscardMarble() throws IOException {
        TurnController giovanniController = new TurnController();
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
    void exchangeWarehouse() throws IOException {
        TurnController giovanniController = new TurnController();
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
        TurnController giovanniController = new TurnController();
        assertTrue(giovanniController.VerifyNumPlayers(3));
        assertEquals(3, giovanniController.getNumPlayersCount());
    }

    @Test
    void verifyNumPlayersWrong() throws IOException {
        TurnController giovanniController = new TurnController();
        assertFalse(giovanniController.VerifyNumPlayers(5));
        assertEquals(0, giovanniController.getNumPlayersCount());
    }

    @Test
    void chooseLeaderCards() throws IOException {
        TurnController giovanniController = new TurnController();
        Player player1 = new Player("Gianfranco");
        assertTrue(giovanniController.VerifyNumPlayers(2));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().startgame();

        assertEquals(4, giovanniController.getGame().getCurrentPlayer().getLeaderActionBox().size());
        assertTrue(giovanniController.ChooseLeaderCards(0, 2));
        assertEquals(2, giovanniController.getGame().getCurrentPlayer().getLeaderActionBox().size());
    }

    @Test
    void chooseResourcesOne() throws IOException {
        TurnController giovanniController = new TurnController();
        Player player1 = new Player("Gianfranco");
        assertTrue(giovanniController.VerifyNumPlayers(1));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().startgame();
        List<String> resourcesList = new ArrayList<>();
        resourcesList.add("Coins");
        resourcesList.add("Servants");

        assertFalse(giovanniController.ChooseResourcesFirstTurn(resourcesList));
        assertEquals(0, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(new Coins()));
        assertEquals(0, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(new Servants()));
    }

    @Test
    void chooseResourcesTwo() throws IOException, EndGameException {
        TurnController giovanniController = new TurnController();
        Player player1 = new Player("Gianfranco");
        Player player2 = new Player("Fini");
        assertTrue(giovanniController.VerifyNumPlayers(2));
        giovanniController.getGame().addPlayersList(player1);
        giovanniController.getGame().addPlayersList(player2);
        giovanniController.getGame().startgame();
        giovanniController.getGame().setCurrentPlayer();
        List<String> resourcesList = new ArrayList<>();
        resourcesList.add("Coins");
        resourcesList.add("Servants");

        assertTrue(giovanniController.ChooseResourcesFirstTurn(resourcesList));
        assertEquals(1, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(new Coins()));
        assertEquals(0, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(new Servants()));
    }

    @Test
    void chooseResourcesFour() throws IOException, EndGameException {
        TurnController giovanniController = new TurnController();
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
        List<String> resourcesList = new ArrayList<>();
        resourcesList.add("Coins");
        resourcesList.add("Servants");

        Resources coin = new Coins();
        Resources servant = new Servants();
        assertTrue(giovanniController.ChooseResourcesFirstTurn(resourcesList));
        assertEquals(1, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(coin));
        assertEquals(1, giovanniController.getGame().getCurrentPlayer().getWarehouse().getNumResources(servant));
    }

    @Test
    void deleteResRight() throws IOException, NegativeQuantityExceptions, OverflowQuantityExcepions {
        TurnController controller = new TurnController();

        Player player = new Player("Angelo");
        player.getWarehouse().checkInsertion(0, new Coins());
        player.getStrongbox().updateResources(new Coins(), 1);
        player.getWarehouse().addleaderCardEffect(new Coins());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(1);

        Map<String,Integer> WarehouseRes = new HashMap<>();
        Map<String,Integer> StrongboxRes = new HashMap<>();
        Map<String,Integer> ExtrachestMap = new HashMap<>();
        WarehouseRes.put("Coins", 1);
        StrongboxRes.put("Coins", 1);
        ExtrachestMap.put("Coins", 1);

        assertTrue(controller.VerifyNumPlayers(2));

        controller.getGame().addPlayersList(player);
        controller.startGame();

        assertTrue(controller.deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap));
        assertEquals(0, controller.getGame().getCurrentPlayer().countTotalResources());
    }

    @Test
    void deleteResWrongChest() throws IOException, NegativeQuantityExceptions, OverflowQuantityExcepions {
        TurnController controller = new TurnController();

        Player player = new Player("Angelo");
        player.getWarehouse().checkInsertion(0, new Coins());
        player.getStrongbox().updateResources(new Coins(), 1);
        player.getWarehouse().addleaderCardEffect(new Coins());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(1);

        Map<String,Integer> WarehouseRes = new HashMap<>();
        Map<String,Integer> StrongboxRes = new HashMap<>();
        Map<String,Integer> ExtrachestMap = new HashMap<>();
        WarehouseRes.put("Coins", 1);
        StrongboxRes.put("Coins", 1);
        ExtrachestMap.put("Coins", 2);

        assertTrue(controller.VerifyNumPlayers(2));

        controller.getGame().addPlayersList(player);
        controller.startGame();

        assertFalse(controller.deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap));
        assertEquals(3, controller.getGame().getCurrentPlayer().countTotalResources());
    }

    @Test
    void deleteResRightWithSecondChest() throws IOException, NegativeQuantityExceptions, OverflowQuantityExcepions {
        TurnController controller = new TurnController();

        Player player = new Player("Angelo");
        player.getWarehouse().checkInsertion(0, new Coins());
        player.getStrongbox().updateResources(new Coins(), 1);
        player.getStrongbox().updateResources(new Coins(), 3);
        player.getWarehouse().addleaderCardEffect(new Servants());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(1);
        player.getWarehouse().addleaderCardEffect(new Coins());
        player.getWarehouse().getLeaderCardEffect().get(1).updateResources(2);

        Map<String,Integer> WarehouseRes = new HashMap<>();
        Map<String,Integer> StrongboxRes = new HashMap<>();
        Map<String,Integer> ExtrachestMap = new HashMap<>();
        WarehouseRes.put("Coins", 1);
        StrongboxRes.put("Coins", 4);
        ExtrachestMap.put("Coins", 2);

        assertTrue(controller.VerifyNumPlayers(2));

        controller.getGame().addPlayersList(player);
        controller.startGame();

        assertTrue(controller.deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap));
        assertEquals(1, controller.getGame().getCurrentPlayer().countTotalResources());
    }
}