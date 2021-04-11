package it.polimi.ingsw.requirements;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;

import java.util.Map;


public class RequirementsProduction implements Requirements{
    /**
     * this attribute is the Map of the required resources
     */
    private Map<String, Integer> reqMap;

    /**
     * check if the card requirements are met,
     * it also takes away the resources from the player
     * @param P is the player who uses the Card
     * @return 1 if the resources requirements are met, 0 otherwise
     */
    @Override
    public boolean checkResources(Player P) {
        return false;
    }
}
