package it.polimi.ingsw.requirements;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.requirements.RequirementsLeader;

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
     * @param P is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    @Override
    public  boolean checkResources(Player P){
        return true; //momentaneo
    }

    /**
     * This method returns the Resources type
     * @return a Resources
     */
    public Resources getReqResources() {
        return this.reqResources;
    }

    /**
     * This method returns the Requested Resources' number
     * @return an integer
     */
    public int getReqnumResources() {
        return this.reqnumResources;
    }
}
