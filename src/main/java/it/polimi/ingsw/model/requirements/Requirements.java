package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.player.Player;

public interface Requirements {
    /**
     * check if the card requirements are met
     * @param player is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    public boolean checkResources(Player player);

    public void showReq();
}
