package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.requirements.Requirements;

public abstract class LeaderAction extends Card {
    /**
     * This attribute indicates if this card is activated
     */
    private boolean activated;

    /**
     * This attribute indicates the resources that the Leader Card discounts to buy a Development Card, gives in an extra depot, gives for a white Marble or gives for an additional production power
     */
    private Resources resources;

    /**
     * This attribute indicates the Card's cost
     */
    private Requirements cost;

    /**
     * This is the constructor method
     */
    public LeaderAction() {
    }

    /**
     * This method returns the "activated" attribute
     * @return true if the Leader Card is activated
     */
    public boolean getActivated(){
        return this.activated;
    }

    /**
     * This method sets the activated attribute to true
     */
    public void setActivated() {
        this.activated = true;
    }

    /**
     * This method returns the resources attribute
     * @return a Resources object
     */
    public Resources getResources(){
        return resources;
    }

    /**
     * This method returns the cost attribute
     * @return a RequiremententsLeader object
     */
    public Requirements getCost (){
        return cost;
    }

    /**
     * This method activates the Leader Card's special ability
     * @param player that uses the Leader Card
     */
    public abstract boolean doSpecialAbility (Player player);



    public abstract void showCli();
}
