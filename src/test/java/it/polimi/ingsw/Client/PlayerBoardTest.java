package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    void searchDevCard() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        DevelopmentCard d = playerBoard.searchDevCard("DCL1B1");

        assertEquals(2, d.getCost().getReqMap().get("Coins"));
    }

    @Test
    void searchLeaderCard() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        LeaderAction l = playerBoard.searchLeaderCard("LCPL1");

        assertEquals("LCPL1", l.getID());
    }

}