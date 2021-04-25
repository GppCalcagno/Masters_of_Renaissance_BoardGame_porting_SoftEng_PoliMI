package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Shields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementsProductionTest {

    @Test
    void ProductionCheckEmpty() {
        Player player= new Player("lillo");

        RequirementsProduction tested= new RequirementsProduction();
        assertTrue(tested.checkResources(player));
    }

    @Test
    void ProductionNoNeededResources1() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");
        player.getStrongbox().updateResources(new Coins(),5);

        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),3);
        tested.addRequirementsProduction(new Shields(),1);

        assertFalse(tested.checkResources(player));
    }

    @Test
    void ProductionNoNeededResources2() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");
        player.getStrongbox().updateResources(new Coins(),5);

        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),8);

        assertFalse(tested.checkResources(player));
    }

    @Test
    void ProductionReturnTrue() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");

        player.getStrongbox().updateResources(new Coins(),5);
        player.getStrongbox().updateResources(new Shields(),5);


        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),3);
        tested.addRequirementsProduction(new Shields(),1);

        assertTrue(tested.checkResources(player));
    }

    @Test
    void PurchaseCheckEmpty() {
        Player player= new Player("lillo");

        RequirementsProduction tested= new RequirementsProduction();
        assertTrue(tested.checkBuy(player));
    }

    @Test
    void PurchaseNoNeededResources1() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");
        player.getStrongbox().updateResources(new Coins(),5);

        RequirementsProduction tested= new RequirementsProduction();

        tested.addRequirementsProduction(new Coins(),3);
        tested.addRequirementsProduction(new Shields(),1);

        assertFalse(tested.checkBuy(player));
    }

    @Test
    void PurchaseReturnTrue() throws NegativeQuantityExceptions {
        Player player= new Player("lillo");

        player.getStrongbox().updateResources(new Coins(),3);
        player.addleaderCardEffectDiscount(new Coins());
        player.addleaderCardEffectDiscount(new Shields());


        RequirementsProduction tested= new RequirementsProduction();
        tested.addRequirementsProduction(new Coins(),3);
        tested.addRequirementsProduction(new Shields(),1);

        assertTrue(tested.checkBuy(player));
    }

}