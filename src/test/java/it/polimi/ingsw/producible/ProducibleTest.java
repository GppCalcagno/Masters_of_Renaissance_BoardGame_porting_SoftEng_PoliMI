package it.polimi.ingsw.producible;

import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProducibleTest {

    Player player= new Player("ciccio!");


    @Test
    public void FaithMarker(){
        Producible f= new FaithMarker();
        int old= player.getFaithMarker();

        f.effect(player);

        assertEquals(old+1,player.getFaithMarker());
    }

    @Test
    public void CoinTest(){
        Resources c= new Coins();

        int old= player.getStrongbox().getNumResources(c);
        assertEquals(0,old);
        c.effect(player);
        assertEquals(old+1,player.getStrongbox().getNumResources(c));
    }

    @Test
    public void ServantsTest(){
        Resources c= new Servants();

        int old= player.getStrongbox().getNumResources(c);
        assertEquals(0,old);
        c.effect(player);
        assertEquals(old+1,player.getStrongbox().getNumResources(c));
    }

    @Test
    public void ShielsdTest(){
        Resources c= new Shields();

        int old= player.getStrongbox().getNumResources(c);
        assertEquals(0,old);
        c.effect(player);
        c.effect(player);
        assertEquals(old+2,player.getStrongbox().getNumResources(c));    }

    @Test
    public void StonesTest(){
        Resources c= new Stones();

        int old= player.getStrongbox().getNumResources(c);
        assertEquals(0,old);
        c.effect(player);
        c.effect(player);
        c.effect(player);

        assertEquals(old+3,player.getStrongbox().getNumResources(c));
    }


}