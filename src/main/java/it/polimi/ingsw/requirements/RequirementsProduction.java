package it.polimi.ingsw.requirements;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.*;

import java.util.HashMap;
import java.util.Map;


public class RequirementsProduction implements Requirements {
    /**
     * this attribute is the Map of the required resources
     */
    private Map<String, Integer> reqMap = new HashMap<>();

    /**
     * check if the card requirements are met,
     * it also takes away the resources from the player
     * @param player is the player who uses the Card
     * @return 1 if the resources requirements are met, 0 otherwise
     */
    @Override
    public boolean checkResources(Player player) {
        int currentRes;
        //qui ho hardcodato le risorse (nel caso fosse possibile cambiare)
        Resources[] type = new Resources[4];
        type[0] = new Coins();
        type[1] = new Servants();
        type[2] = new Servants();
        type[3] = new Shields();

        for (Resources res : type) {
            currentRes = 0;
            if (reqMap.containsKey(res.toString())) {
                currentRes = player.getStrongbox().getNumResources(res) + player.getWarehouse().getNumResources(res);

                if (currentRes < reqMap.get(res.toString()))
                    return false;
            }
        }
        return true;
    }

    /**
     * this method is a getter of the RequirementsProduction Map of the card
     * @return the RequirementsProductionMap
     */
    public Map<String, Integer> getReqMap() {
        return reqMap;
    }

    /**
     * this methos adds a request to the card characteristics
     * @param rec type of resource requested
     * @param num num of resource requested
     */
    public void addRequirementsProduction(Resources rec, int num){
        reqMap.put(rec.toString(),num);
    }
}
