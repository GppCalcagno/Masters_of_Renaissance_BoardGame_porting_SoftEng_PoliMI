package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.View.Cli.Color;
import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.player.Player;

import java.awt.*;

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
        String color = Color.ANSI_BRIGHTWHITE.escape();
        switch(reqColor){
            case BLUE: color = Color.ANSI_BRIGHTBLUE.escape();break;
            case GREEN: color = Color.ANSI_GREEN.escape(); break;
            case PURPLE: color = Color.ANSI_BRIGHTPURPLE.escape(); break;
            case YELLOW: color = Color.ANSI_YELLOW.escape(); break;
        }

        System.out.println("To activate the card you need a "+ color + reqColor + Color.ANSI_BRIGHTWHITE.escape() + " devCard level: "+  reqLevel);
    }
}
