package it.polimi.ingsw.model.producible;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.player.Player;

public class Shields extends Resources {

    /**
     * this method activates the effect of the resource: add shields to player's StrongBox
     * @param p is the player who uses the resource
     */
    public void effect(Player p) {
        try {
            p.getStrongbox().updateResources(this, 1);
        } catch (NegativeQuantityExceptions ignored) {}
    }

    /**
     * this method is used to  get the key of the maps
     * @return the key used in game's map
     */
    public String toString(){
        return "Shields";
    }
}
