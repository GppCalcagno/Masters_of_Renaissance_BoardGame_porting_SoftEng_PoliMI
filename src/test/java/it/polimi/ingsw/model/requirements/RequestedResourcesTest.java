package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestedResourcesTest {
    Requirements tested= new RequestedResources(new Coins(),5);


    @Test
    public void NoNeededResources(){
        Player player=new Player("Boris");

        assertFalse(tested.checkResources(player));
    }

    @Test
    public void ReturnTrue() throws NegativeQuantityExceptions {
        Player player=new Player("Biascica");
        player.getStrongbox().updateResources(new Coins(),5);

        assertTrue(tested.checkResources(player));
    }

    @Test
    public void ReturnFalse() throws NegativeQuantityExceptions {
        Player player=new Player("Biascica");
        player.getStrongbox().updateResources(new Coins(),3);

        assertFalse(tested.checkResources(player));
    }
}