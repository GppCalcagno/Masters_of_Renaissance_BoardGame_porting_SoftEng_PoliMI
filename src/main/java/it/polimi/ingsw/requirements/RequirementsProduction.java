package it.polimi.ingsw.requirements;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Servants;
import it.polimi.ingsw.producible.Shields;
import it.polimi.ingsw.producible.Stones;

import java.util.HashMap;
import java.util.Map;


public class RequirementsProduction implements Requirements{

    /**
     * this attribute is the Map of the required resources
     */
    private Map<Integer, Integer> reqMap;

    /**
     * this is the costructor of the class
     * @param numCoins is the required Coins for the card
     * @param numServants is the required Servants for the card
     * @param numShields is the required Shield for the card
     * @param numStones is the required Stone for the card
     */
    public RequirementsProduction(int numCoins, int numServants, int numShields, int numStones) {
        reqMap = new HashMap<Integer, Integer>();
        reqMap.put(new Coins().hashCode(),numCoins);
        reqMap.put(new Servants().hashCode(),numServants);
        reqMap.put(new Shields().hashCode(),numShields);
        reqMap.put(new Stones().hashCode(),numStones);
    }

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
