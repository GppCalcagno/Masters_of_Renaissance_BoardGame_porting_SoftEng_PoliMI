package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.NoSelectedLeaderActionExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.game.LeaderCardDeck;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Servants;
import it.polimi.ingsw.model.producible.Shields;
import it.polimi.ingsw.model.producible.Stones;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
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

    @Test
    void truecheckListResources() throws NegativeQuantityExceptions {
        Player player = new Player("concetta");

        player.getWarehouse().insertResources(2, new Coins());
        player.getWarehouse().insertResources(2, new Coins());
        player.getWarehouse().insertResources(1, new Stones());

        player.getStrongbox().updateResources(new Servants(),10);
        player.getStrongbox().updateResources(new Shields(),10);

        Map<String,Integer> reqWareHouse= new HashMap<>();
        Map<String,Integer> reqStrongbox= new HashMap<>();
        Map<String,Integer> reqExtraChest= new HashMap<>();



        reqWareHouse.put(new Coins().toString(),2);
        reqStrongbox.put(new Shields().toString(),5);

       assertTrue(player.checkListResources(reqWareHouse,reqStrongbox,reqExtraChest));
    }

    @Test
    void faslsecheckListResources() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("concetta");

        player.getWarehouse().insertResources(2, new Coins());
        player.getWarehouse().insertResources(2, new Coins());
        player.getWarehouse().insertResources(1, new Stones());
        player.getWarehouse().getLeaderCardEffect().add(new ExtraChest(new Coins()));
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(1);

        player.getStrongbox().updateResources(new Servants(),10);
        player.getStrongbox().updateResources(new Shields(),10);

        Map<String,Integer> reqWareHouse= new HashMap<>();
        Map<String,Integer> reqStrongbox= new HashMap<>();
        Map<String,Integer> reqExtraChest= new HashMap<>();


        reqWareHouse.put(new Coins().toString(),2);
        reqStrongbox.put(new Shields().toString(),5);
        reqExtraChest.put(new Coins().toString(),2);

      assertFalse(player.checkListResources(reqWareHouse,reqStrongbox,reqExtraChest));
    }

    @Test
    void NoRescheckListResources() throws NegativeQuantityExceptions {
        Player player = new Player("concetta");

        player.getWarehouse().insertResources(2, new Coins());
        player.getWarehouse().insertResources(2, new Coins());
        player.getWarehouse().insertResources(1, new Stones());


        player.getStrongbox().updateResources(new Servants(),10);
        player.getStrongbox().updateResources(new Shields(),10);

        Map<String,Integer> reqWareHouse= new HashMap<>();
        Map<String,Integer> reqStrongbox= new HashMap<>();
        Map<String,Integer> reqExtraChest= new HashMap<>();

        reqWareHouse.put(new Servants().toString(),2);
        reqStrongbox.put(new Shields().toString(),5);

        assertFalse(player.checkListResources(reqWareHouse,reqStrongbox,reqExtraChest));
    }



}