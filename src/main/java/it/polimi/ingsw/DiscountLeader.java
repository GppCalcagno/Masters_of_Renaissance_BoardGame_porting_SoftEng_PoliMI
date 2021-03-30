package it.polimi.ingsw;

public class DiscountLeader extends LeaderAction{
    /**
     * This is the constructor method
     * @param victoryPoints number of victory points
     */
    public DiscountLeader(int victoryPoints) {
        super(victoryPoints);
    }

    /**
     * This method activates the special ability that discounts a resource, indicated in the "resources" attribute, from the cost of a Development Card
     * @param player that uses the Leader Card
     */
    @Override
    public void doSpecialAbility(Player player) {
        //to implement
    }
}
