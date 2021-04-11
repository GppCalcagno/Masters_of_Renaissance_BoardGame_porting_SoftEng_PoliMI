package it.polimi.ingsw.producible;

import it.polimi.ingsw.player.Player;

public class Stones extends Resources {
    /**
     * this method activates the effect of the resource: add Stones to player's StrongBox
     * @param p is the player who uses the resource
     */
    @Override
    public void effect(Player p) {
        p.getStrongbox().updateResources(this, 1);
    }

    /**
     *this method is used to get the  Resources Map's Key in StrongBox
     * "ok perfetto grazie!!!" -cit
     * @return the key linked to the resources
     */

    public String tostring(){
        return "Stones";
    }
}
