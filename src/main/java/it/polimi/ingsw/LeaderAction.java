package it.polimi.ingsw;

public class LeaderAction extends Card {
    /**
     * This attribute indicates if this card is activated
     */
    private boolean activated;

    /**
     * This attribute indicates the resources that the Leader Card discounts to buy a Development Card, gives in an extra depot, gives for a white Marble or gives for an additional production power
     */
    private Resources resources;

    /**
     * This is the constructor method
     * @param victoryPoints number of victory points
     */
    public LeaderAction(int victoryPoints) {
        super(victoryPoints);
        // to implement activated and resources
    }

    /**
     * This method returns the "activated" attribute
     * @return true if the Leader Card is activated
     */
    public boolean getActivated(){
        return activated;
    }

    /**
     * This method sets the attribute true or false
     * @param activated true if the Leader Card is activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * This method activates the Leader Card's special ability
     * @param player that uses the Leader Card
     */
    public void doSpecialAbility (Player player) {
        //to implement
    }
}
