package it.polimi.ingsw;

public class RequestedLevelDevelopmentCards extends RequirementsLeader{
    /**
     *  this attribute is the type of the required card
     */
    private ColorCard reqColor;

    /**
     * this attribute is the level of the required card
     */
    private int relevel;

    /**
     * this is the costructor of RequestedResources
     * @param reqColor is the color of the required Card
     * @param relevel is the level of the required Card
     */
    public RequestedLevelDevelopmentCards(ColorCard reqColor, int relevel) {
        this.reqColor = reqColor;
        this.relevel = relevel;
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
