package it.polimi.ingsw.requirements;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;

public interface Requirements {
    /**
     * check if the card requirements are met
     * @param player is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    public boolean checkResources(Player player);
}
