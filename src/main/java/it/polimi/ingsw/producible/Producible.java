package it.polimi.ingsw.producible;

import it.polimi.ingsw.player.Player;

public interface Producible {

    /**
     * this method activates the effect of the Producible
     * @param p is the player who uses the Producible
     */
    public void effect(Player p);

    public String tostring();
}

