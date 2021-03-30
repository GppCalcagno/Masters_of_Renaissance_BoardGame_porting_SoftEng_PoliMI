package it.polimi.ingsw;

import java.util.HashMap;
import java.util.Map;

public class requestedTypeDevelopmentCards extends RequirementsLeader{

    /**
     *  this attribute is the type of the required Cards.
     *  It contains the Color and the number of the required Cards
     */
    private Map<ColorCard, Integer> reqDevelopmentCards;

    /**
     * this is the costructor of requestedTypeDevelopmentCards
     */
    public requestedTypeDevelopmentCards() {
        reqDevelopmentCards = new HashMap<ColorCard, Integer>();
    }

    /**
     * check if the card requirements are met
     * @param P is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    @Override
    public  boolean checkResources(Player P){
        return true; //momentaneo
    }
}
