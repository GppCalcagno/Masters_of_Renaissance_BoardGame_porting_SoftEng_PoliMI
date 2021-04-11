package it.polimi.ingsw.producible;

import it.polimi.ingsw.player.Player;

public abstract class Resources implements Producible {
        /**
         * this method activates the effect of the resource: add Servants to player's StrongBox
         * @param p is the player who uses the resource
         */
        @Override
        public void effect(Player p) {
                p.getStrongbox().updateResources(this, 1);
        }

        public String tostring(){
                return "Resources";
        }
}
