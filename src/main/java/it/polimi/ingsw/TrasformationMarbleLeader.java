package it.polimi.ingsw;

public class TrasformationMarbleLeader extends LeaderAction{
    /**
     * This is the constructor method
     *
     * @param victoryPoints number of victory points
     */
    public TrasformationMarbleLeader(int victoryPoints) {
        super(victoryPoints);
    }

    /**
     * This method activates the Special Effect that, when the player takes Resources from the Market, for each white Marble in the chosen column/row gives to him the indicated Resource
     * @param player that uses the Leader Card
     */
    @Override
    public void doSpecialAbility(Player player) {
        // to implement
    }
}
