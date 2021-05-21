package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Resources;

import java.util.Locale;

public class RequestedResources extends RequirementsLeader {
    /**
     *  this attribute is the type of the required resource
     */
    private Resources reqResources;

    /**
     * this attribute is the number of the required resource
     */
    private int reqnumResources;


    /**
     * this is the costructor of RequestedResources
     * @param reqResources is the type of the resources
     * @param reqnumResources is the number of the resouces
     */
    public RequestedResources(Resources reqResources, int reqnumResources) {
        this.reqResources = reqResources;
        this.reqnumResources = reqnumResources;
    }

    /**
     * check if the card requirements are met
     * @param player is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    @Override
    public  boolean checkResources(Player player){
        int currentRes;
        currentRes = player.getStrongbox().getNumResources(reqResources) + player.getWarehouse().getNumResources(reqResources);
        return currentRes>=reqnumResources;
    }

    @Override
    public void showReq() {
        System.out.println("To activate this card you must have "+ reqnumResources +" units of "+ reqResources.toString());
    }


}
