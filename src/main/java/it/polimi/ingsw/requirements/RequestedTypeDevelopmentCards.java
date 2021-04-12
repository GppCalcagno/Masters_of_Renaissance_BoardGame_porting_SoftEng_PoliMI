package it.polimi.ingsw.requirements;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.card.DevelopmentCard;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.requirements.RequirementsLeader;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class RequestedTypeDevelopmentCards extends RequirementsLeader {

    /**
     *  this attribute is the type of the required Cards.
     *  It contains the Color and the number of the required Cards
     */
    private EnumMap<ColorCard, Integer> reqDevelopmentCards;

    /**
     * this is the costructor of requestedTypeDevelopmentCards
     */
    public RequestedTypeDevelopmentCards() {
        reqDevelopmentCards = new EnumMap<ColorCard, Integer>(ColorCard.class);
    }

    /**
     * check if the card requirements are met
     * @param player the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    @Override
    public  boolean checkResources(Player player){
        DevelopmentCard card;
        EnumMap<ColorCard, Integer> checkMap= reqDevelopmentCards.clone();

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                //scorro la mappa e sottraggo le carte che ho
                try {
                    card=player.getSlotDevCards().getDevCards(i,j);

                    if(checkMap.containsKey(card.getColorCard())){
                        if(checkMap.get(card.getColorCard())>0)
                            checkMap.put(card.getColorCard(),checkMap.get(card.getColorCard())-1);
                    }

                } catch (NullPointerException e) {}
            }
        }
        //se le richieste sono state soddisfatte tutta la mappa è vuota.
        for (ColorCard C: checkMap.keySet()){
            if(!checkMap.get(C).equals(0))
                return false;
        }
        return true;
    }


    /**
     * this methos adds a request to the card characteristics
     * @param color type of card requested
     * @param num num of card requested
     */
    public void addRequirements(ColorCard color,int num){
        reqDevelopmentCards.put(color,num);
    }
}
