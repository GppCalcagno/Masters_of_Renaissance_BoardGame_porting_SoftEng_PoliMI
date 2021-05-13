package it.polimi.ingsw.Client;

import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.view;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {
    PlayerBoard playerBoard = new PlayerBoard();
    view cli = new Cli(playerBoard);

    ClientControllerTest() throws IOException {
    }

    @Test
    void searchDevCard() throws IOException {
        ClientController controller = new ClientController(cli);
        DevelopmentCard d = controller.searchDevCard("DCL1B1");

        assertEquals(2, d.getCost().getReqMap().get("Coins"));
    }

    @Test
    void searchLeaderCard() throws IOException {
        ClientController controller = new ClientController(cli);
        LeaderAction l = controller.searchLeaderCard("LCPL1");

        assertEquals("LCPL1", l.getID());
    }
}