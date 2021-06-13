package it.polimi.ingsw.model.game;

import it.polimi.ingsw.Network.Server.UpdateCreator.JavaSerUpdateCreator;
import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.Server.UpdateSender.ServerUpdate;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.marbles.BlueMarble;
import it.polimi.ingsw.model.marbles.WhiteMarble;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Servants;
import it.polimi.ingsw.model.producible.Shields;
import it.polimi.ingsw.model.producible.Stones;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setCurrentPlayer() throws IOException, EndGameException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));
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
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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
    void isFinishedGame25() throws IOException, ActiveVaticanReportException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Augusto");
        for(int i = 0; i < 25; i++){
            try {
                player.increasefaithMarker();
            }
            catch (ActiveVaticanReportException activeVaticanReportException) {

            }
        }
        game.addPlayersList(player);

        assertTrue(game.isFinishedGame());
    }

    @Test
    void isFinishedGame7() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Ottaviano");
        game.addPlayersList(player);
        try {
            player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 0));
            player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(1, 0));
            player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(0, 0));
            player.getSlotDevCards().insertCards(1, game.getDevelopmentCardDeck().getDevCards(2, 0));
            player.getSlotDevCards().insertCards(1, game.getDevelopmentCardDeck().getDevCards(1, 0));
            player.getSlotDevCards().insertCards(1, game.getDevelopmentCardDeck().getDevCards(0, 0));
            player.getSlotDevCards().insertCards(2, game.getDevelopmentCardDeck().getDevCards(2, 0));
        } catch (GameFinishedException g) {
            assertTrue(game.isFinishedGame());
        }
    }

    @Test
    void startgameLeaderActionBox() throws IOException{
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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
    void startgameIncreaseFaithMarker4() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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
    void startgameIncreaseFaithMarker2() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Qui");
        Player player2 = new Player("Quo");
        game.addPlayersList(player1);
        game.addPlayersList(player2);

        game.startgame();

        assertEquals(0, game.getPlayersList().get(0).getFaithMarker());
        assertEquals(0, game.getPlayersList().get(1).getFaithMarker());
    }

    @Test
    void givefinalpoints() throws IOException, NegativeQuantityExceptions, GameFinishedException, ActiveVaticanReportException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Caterina");

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
        assertEquals(i, game.getPlayersList().get(0).getVictoryPoints());
    }

    @Test
    void givefinalpoints0() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Caterina");

        game.addPlayersList(player);
        game.startgame();
        game.givefinalpoints();
        assertEquals(0, game.getPlayersList().get(0).getVictoryPoints());
    }

    @Test
    void addPlayersList() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");

        assertTrue(game.getPlayersList().isEmpty());
        game.addPlayersList(player1);
        assertFalse(game.getPlayersList().isEmpty());
    }

    @Test
    void takeResourcesMarketRight() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        game.addPlayersList(player1);
        game.startgame();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertTrue(game.extractionMarble('C', 1));
    }

    @Test
    void takeResourcesMarketWrongColRow() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        game.addPlayersList(player1);
        game.startgame();


        assertFalse(game.extractionMarble('n', 2));
    }

    @Test
    void takeResourcesMarketWrongNum() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        game.addPlayersList(player1);
        game.startgame();

        assertFalse(game.extractionMarble('r', 4));
    }

    @Test
    void AddMarbleToWarehouse() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        game.addPlayersList(player1);
        game.startgame();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertTrue(game.extractionMarble('C', 1));
        assertTrue(game.manageMarble('W', 2, " "));
    }

    @Test
    void DiscardMarble() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        game.addPlayersList(player1);
        game.startgame();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertTrue(game.extractionMarble('C', 1));
        assertTrue(game.manageMarble('D', 0, " "));
    }

    @Test
    void exchangeWarehouse() throws IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        game.addPlayersList(player1);
        Player player2 = new Player("Anno");
        game.addPlayersList(player2);
        game.startgame();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertTrue(game.extractionMarble('C', 1));
        assertTrue(game.manageMarble('W', 0, " "));
        assertTrue(game.exchangeWarehouse(0, 1));
    }

    @Test
    void selectDevCardTestWrongID() throws NegativeQuantityExceptions, IOException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Anna");
        player1.getStrongbox().updateResources("Coins", 2);
        game.addPlayersList(player1);
        game.startgame();

        assertFalse(game.selectDevCard("Prova", 0));
    }

    @Test
    void deleteResRight() throws IOException, NegativeQuantityExceptions, OverflowQuantityExcepions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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

        game.addPlayersList(player);
        game.startgame();

        assertTrue(game.deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap));
        assertEquals(0, game.getCurrentPlayer().countTotalResources());
    }

    @Test
    void deleteResWrongChest() throws IOException, NegativeQuantityExceptions, OverflowQuantityExcepions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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

        game.addPlayersList(player);
        game.startgame();

        assertFalse(game.deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap));
        assertEquals(3, game.getCurrentPlayer().countTotalResources());
    }

    @Test
    void deleteResRightWithSecondChest() throws IOException, NegativeQuantityExceptions, OverflowQuantityExcepions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

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

        game.addPlayersList(player);
        game.startgame();



        assertTrue(game.deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap));
        assertEquals(1, game.getCurrentPlayer().countTotalResources());
    }

    @Test
    void activeBaseProductionTestRight() throws IOException, NegativeQuantityExceptions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        player.getStrongbox().updateResources("Coins", 2);
        game.addPlayersList(player);
        game.startgame();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertEquals(2, player.countTotalResources());
        assertTrue(game.activeBaseProduction('S', "Coins", 'S', "Coins", "Servants"));
        game.endProduction();
        assertEquals(1, player.countTotalResources());
    }

    @Test
    void activeBaseProductionTestWrongChar() throws IOException, NegativeQuantityExceptions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        player.getStrongbox().updateResources("Coins", 2);
        game.addPlayersList(player);
        game.startgame();

        assertEquals(2, player.countTotalResources());
        assertFalse(game.activeBaseProduction('t', "Coins", 's', "Coins", "Servants"));
        assertEquals(2, player.countTotalResources());
    }

    @Test
    void activeLeaderCardProductionRight() throws IOException, NegativeQuantityExceptions, GameFinishedException, EndGameException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        player.getStrongbox().updateResources("Shields", 2);
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 2));
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(1, 2));
        game.addPlayersList(player);
        game.setCurrentPlayer();
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCPL1"));
        player.getLeaderActionBox().get(0).doSpecialAbility(player);
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertEquals(2, player.countTotalResources());
        assertTrue(game.activeLeaderCardProduction("LCPL1", 'S', "Coins"));
        assertEquals(1, player.countTotalResources());
        assertEquals(1, player.getSlotDevCards().getBuffer().get("Coins"));
        assertEquals(1, player.getFaithMarker());
    }

    @Test
    void activeLeaderCardProductionWrongID() throws IOException, NegativeQuantityExceptions, GameFinishedException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        game.addPlayersList(player);
        game.startgame();

        assertFalse(game.activeLeaderCardProduction("prova", 's', "Coins"));
    }

    @Test
    void activeLeaderCardProductionWrongChest() throws IOException, NegativeQuantityExceptions, GameFinishedException, EndGameException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        player.getStrongbox().updateResources("Shields", 2);
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 2));
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(1, 2));
        game.addPlayersList(player);
        game.setCurrentPlayer();
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCPL1"));
        player.getLeaderActionBox().get(0).doSpecialAbility(player);

        assertEquals(2, player.countTotalResources());
        assertFalse(game.activeLeaderCardProduction("LCPL1", 't', "Coins"));
        assertEquals(2, player.countTotalResources());
        assertNull(player.getSlotDevCards().getBuffer().get("Coins"));
        assertEquals(0, player.getFaithMarker());
    }

    @Test
    void activeLeaderCardProductionResource() throws IOException, NegativeQuantityExceptions, GameFinishedException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        player.getStrongbox().updateResources("Shields", 2);
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 2));
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(1, 2));
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCPL1"));
        player.getLeaderActionBox().get(0).doSpecialAbility(player);
        game.addPlayersList(player);
        game.startgame();

        assertEquals(2, player.countTotalResources());
        assertFalse(game.activeLeaderCardProduction("LCPL1", 'S', "prova"));
        assertEquals(2, player.countTotalResources());
        assertNull(player.getSlotDevCards().getBuffer().get("Coins"));
        assertEquals(0, player.getFaithMarker());
    }

    @Test
    void activeLeaderCardProductionWrongxtraChest() throws IOException, NegativeQuantityExceptions, GameFinishedException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Angelo");
        player.getStrongbox().updateResources("Shields", 2);
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(2, 2));
        player.getSlotDevCards().insertCards(0, game.getDevelopmentCardDeck().getDevCards(1, 2));
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCPL1"));
        player.getLeaderActionBox().get(0).doSpecialAbility(player);
        game.addPlayersList(player);
        game.startgame();

        assertEquals(2, player.countTotalResources());
        assertFalse(game.activeLeaderCardProduction("LCPL1", 'E', "prova"));
        assertEquals(2, player.countTotalResources());
        assertNull(player.getSlotDevCards().getBuffer().get("Coins"));
        assertEquals(0, player.getFaithMarker());
    }

    @Test
    void updateLeaderCardTestRightActivation() throws IOException, EndGameException, NegativeQuantityExceptions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Paolo Condo");
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCCL1"));
        player.getStrongbox().updateResources("Coins", 5);
        game.addPlayersList(player);
        game.setCurrentPlayer();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertTrue(game.updateLeaderCard("LCCL1", 1));
        assertTrue(player.getLeaderActionBox().get(0).getActivated());
    }

    @Test
    void updateLeaderCardTestRightDiscard() throws IOException, EndGameException, NegativeQuantityExceptions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Paolo Condo");
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCCL1"));
        player.getStrongbox().updateResources("Coins", 5);
        game.addPlayersList(player);
        game.setCurrentPlayer();
        game.getCurrentPlayer().setGameState(GameState.INGAME);

        assertTrue(game.updateLeaderCard("LCCL1", 0));
        assertTrue(player.getLeaderActionBox().isEmpty());
    }

    @Test
    void updateLeaderCardTestWrongID() throws IOException, EndGameException, NegativeQuantityExceptions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Paolo Condo");
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCCL1"));
        player.getStrongbox().updateResources("Coins", 5);
        game.addPlayersList(player);
        game.setCurrentPlayer();

        assertFalse(game.updateLeaderCard("prova", 1));
    }

    @Test
    void updateLeaderCardTestWrongChoice() throws IOException, EndGameException, NegativeQuantityExceptions {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player = new Player("Paolo Condo");
        player.addLeaderAction(game.getLeaderCardDeck().getLeaderCardFromID("LCCL1"));
        player.getStrongbox().updateResources("Coins", 5);
        game.addPlayersList(player);
        game.setCurrentPlayer();

        assertFalse(game.updateLeaderCard("LCCL1", 4));
    }

    @Test
    void endTurnTestMultiPlayer() throws IOException, EndGameException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Vanessa Leonardi");
        game.addPlayersList(player1);
        game.setCurrentPlayer();
        Player player2 = new Player("Maurizio Pistocchi");
        game.addPlayersList(player2);

        game.endTurn(false);
        assertEquals(player2, game.getCurrentPlayer());
    }

    @Test
    void addWhiteMarbleWithEffect() throws IOException, EndGameException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Vanessa Leonardi");
        game.addPlayersList(player1);
        game.setCurrentPlayer();
        player1.addleaderCardEffectWhiteMarble(new Coins());
        player1.addleaderCardEffectWhiteMarble(new Stones());
        game.getCurrentPlayer().getWarehouse().getBuffer().add(new WhiteMarble());
        game.getCurrentPlayer().getWarehouse().getBuffer().add(new BlueMarble());
        game.getCurrentPlayer().getWarehouse().getBuffer().add(new WhiteMarble());
        game.getCurrentPlayer().getWarehouse().getBuffer().add(new WhiteMarble());
        game.getCurrentPlayer().setGameState(GameState.INGAME);
        game.getCurrentPlayer().setTurnPhase(TurnPhase.DOTURN);
        assertTrue(game.manageMarble('W', 1, "Stones"));
        assertTrue(game.manageMarble('W', 0, null));
        assertTrue(game.manageMarble('W', 2, "Coins"));
        assertTrue(game.manageMarble('W', 1, "Stones"));
        assertEquals(2, player1.getWarehouse().getNumResources(new Stones()));
        assertEquals(1, player1.getWarehouse().getNumResources(new Shields()));
        assertEquals(1, player1.getWarehouse().getNumResources(new Coins()));
    }

    @Test
    void endProductionTest() throws IOException, EndGameException {
        Game game = new Game(new JavaSerUpdateCreator(new ServerUpdate(new Server(1234))));

        Player player1 = new Player("Vanessa Leonardi");
        game.addPlayersList(player1);
        game.setCurrentPlayer();
        player1.getSlotDevCards().getBuffer().put("FaithMarker", 4);
        player1.getSlotDevCards().getBuffer().put("Coins", 5);
        player1.getSlotDevCards().getBuffer().put("Servants", 3);
        player1.getSlotDevCards().getBuffer().put("Shields", 1);
        assertTrue(game.endProduction());
        assertEquals(4, player1.getFaithMarker());
        assertEquals(5, player1.getStrongbox().getNumResources("Coins"));
        assertEquals(3, player1.getStrongbox().getNumResources("Servants"));
        assertEquals(1, player1.getStrongbox().getNumResources("Shields"));
    }


}