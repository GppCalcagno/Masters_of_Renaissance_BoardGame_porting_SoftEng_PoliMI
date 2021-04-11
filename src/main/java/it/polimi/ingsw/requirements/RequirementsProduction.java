package it.polimi.ingsw.requirements;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.*;

import java.util.HashMap;
import java.util.Map;


public class RequirementsProduction implements Requirements{
    /**
     * this attribute is the Map of the required resources
     */
    private Map<String, Integer> reqMap = new HashMap<>();

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

    /**
     * this method is a getter of the RequirementsProduction Map of the card
     * @return the RequirementsProductionMap
     */
    public Map<String, Integer> getReqMap() {
        return reqMap;
    }

    public void addRequirementsProduction(Resources rec, int num){
        if(reqMap.containsKey(rec.toString()));

    }
}
