package it.polimi.ingsw.requirements;

import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Shields;
import it.polimi.ingsw.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementsProductionTest {

    @Test
    void CheckEmpty() {
        Player player= new Player("lillo");

        RequirementsProduction tested= new RequirementsProduction();
        assertTrue(tested.checkResources(player));
    }

    @Test
    void NoNeededResources1() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");
        player.getStrongbox().updateResources(new Coins(),5);

        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),3);
        tested.addRequirementsProduction(new Shields(),1);

        assertFalse(tested.checkResources(player));
    }

    @Test
    void NoNeededResources2() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");
        player.getStrongbox().updateResources(new Coins(),5);

        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),8);

        assertFalse(tested.checkResources(player));
    }

    @Test
    void ReturnTrue() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");

        player.getStrongbox().updateResources(new Coins(),5);
        player.getStrongbox().updateResources(new Shields(),5);


        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),3);
        tested.addRequirementsProduction(new Shields(),1);

        assertTrue(tested.checkResources(player));
    }
}