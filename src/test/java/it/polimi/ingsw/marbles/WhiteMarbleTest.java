package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleTest {

    @Test
    void addToWarehouseNotEmpty(){
        Player player = new Player("Filomena");
        Resources resources = new Coins();
        player.addleaderCardEffectWhiteMarble(resources);
        Marbles marbles = new WhiteMarble();

        marbles.addtoWarehouse(player, 0);
        assertEquals(1, player.getWarehouse().getNumResources(resources));
    }

    @Test
    void addToWarehouseEmpty(){
        Player player = new Player("Filomena");
        Marbles marbles = new WhiteMarble();

        marbles.addtoWarehouse(player, 0);
        List<Resources> resourcesList = new ArrayList<>();
        resourcesList.add(new Coins());
        resourcesList.add(new Servants());
        resourcesList.add(new Shields());
        resourcesList.add(new Stones());
        for(Resources resources : resourcesList){
            assertEquals(0, player.getWarehouse().getNumResources(resources));
        }
    }
}