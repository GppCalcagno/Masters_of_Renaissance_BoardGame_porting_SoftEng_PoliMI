package it.polimi.ingsw.producible;

import it.polimi.ingsw.player.Player;
import org.jetbrains.annotations.NotNull;

public abstract class Resources implements Producible {
        /**
         * this method activates the effect of the resource: add Servants to player's StrongBox
         * @param p is the player who uses the resource
         */
        public void effect(Player p) {
                p.getStrongbox().updateResources(this, 1);
        }

        /**
         * this method is used to  get the key of the maps
         * @return the key used in game's map
         */
        public String toString(){
                return "Resources";
        }
}
