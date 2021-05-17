package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.player.Player;

public class RequestedLevelDevelopmentCards extends RequirementsLeader {
    /**
     *  this attribute is the type of the required card
     */
    private ColorCard reqColor;

    /**
     * this attribute is the level of the required card
     */
    private int reqLevel;

    /**
     * this is the costructor of RequestedResources
     * @param reqColor is the color of the required Card
     * @param reqlevel is the level of the required Card
     */
    public RequestedLevelDevelopmentCards(ColorCard reqColor, int reqlevel) {
        this.reqColor = reqColor;
        this.reqLevel = reqlevel;
    }


    /**
     * check if the card requirements are met
     * @param player is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    @Override
    public  boolean checkResources(Player player){
        DevelopmentCard card;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                //scorro la mappa e verifico se la carta è presente
                card=player.getSlotDevCards().getDevCards(i,j);
                if(card != null && card.getColorCard().equals(reqColor) && card.getLevel()==(reqLevel)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void showReq() {
        System.out.println("Color : " + reqColor + " " + "Level : " + reqLevel);
    }
}
