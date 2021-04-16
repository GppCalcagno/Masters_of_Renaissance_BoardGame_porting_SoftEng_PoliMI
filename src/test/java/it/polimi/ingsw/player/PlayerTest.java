package it.polimi.ingsw.player;

import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.exceptions.NoSelectedLeaderActionExceptions;
import it.polimi.ingsw.game.LeaderCardDeck;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Shields;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    //siccome la classe player chiama fa solo dei get e al massimo un remove e un add di una lista,
    //che sono cose abbastanza "standard" ha senso fare i test?d

    @Test
    void countLeaderActionVictoryPoints1() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Francesco");
        leaderCardDeck.getLeaderCardList(0).setActivated();
        player.addLeaderAction(leaderCardDeck.getLeaderCardList(0));

        assertEquals(leaderCardDeck.getLeaderCardList(0).getVictoryPoints(), player.countLeaderActionVictoryPoints());
    }

    @Test
    void countLeaderActionVictoryPoints2() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Francesco");
        leaderCardDeck.getLeaderCardList(0).setActivated();
        leaderCardDeck.getLeaderCardList(1).setActivated();
        player.addLeaderAction(leaderCardDeck.getLeaderCardList(0));
        player.addLeaderAction(leaderCardDeck.getLeaderCardList(1));

        int expected = leaderCardDeck.getLeaderCardList(0).getVictoryPoints() + leaderCardDeck.getLeaderCardList(1).getVictoryPoints();
        int actual = player.countLeaderActionVictoryPoints();
        assertEquals(expected, actual);
    }

    @Test
    void countLeaderActionVictoryPoints0() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Francesco");

        assertEquals(0, player.countLeaderActionVictoryPoints());

    }

    @Test
    void countLeaderActionVictoryPointsNotActivated() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Francesco");

        player.addLeaderAction(leaderCardDeck.getLeaderCardList(0));
        player.addLeaderAction(leaderCardDeck.getLeaderCardList(1));

        assertEquals(0, player.countLeaderActionVictoryPoints());
    }

    @Test
    void countTotalResources2() throws IOException, NegativeQuantityExceptions {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Giuseppe");

        player.getStrongbox().updateResources(new Coins(), 2);
        player.getWarehouse().insertResources(0, new Shields());

        assertEquals(3, player.countTotalResources());
    }

    @Test
    void countTotalResources0() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Giuseppe");

        assertEquals(0, player.countTotalResources());
    }

    @Test
    void selectedWhiteMarble(){
        Player player = new Player("Giuseppe");

        try {
            player.selectedWhiteMarble(new Coins());
        } catch (NoSelectedLeaderActionExceptions noSelectedLeaderActionExceptions) {assertTrue(true);}

    }

    @Test
    void AlreadyFirstMarble() throws NoSelectedLeaderActionExceptions {
        Player player = new Player("Giuseppe");
        player.addleaderCardEffectWhiteMarble(new Coins());

        player.selectedWhiteMarble(new Coins());


        assertEquals(player.getLeaderCardEffectWhiteMarble().get(0).getClass(), Coins.class);
    }

    @Test
    void MovetoFirst() throws NoSelectedLeaderActionExceptions {
        Player player = new Player("Giuseppe");

        player.addleaderCardEffectWhiteMarble(new Shields());
        player.addleaderCardEffectWhiteMarble(new Coins());

        player.selectedWhiteMarble(new Coins());

        assertEquals(player.getLeaderCardEffectWhiteMarble().get(0).getClass(), Coins.class);

        assertEquals(player.getLeaderCardEffectWhiteMarble().size(),2);
        assertEquals(player.getLeaderCardEffectWhiteMarble().get(1).getClass(), Shields.class);

    }




}