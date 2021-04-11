package it.polimi.ingsw.producible;

import it.polimi.ingsw.player.Player;

public class Coins extends Resources {
    /**
     * this method activates the effect of the resource: add coins to player's StrongBox
     * @param p is the player who uses the resource
     */
    @Override
    public void effect(Player p) {
        p.getStrongbox().updateResources(this, 1);
    }

    public String tostring(){
        return "Coins";
    }



}