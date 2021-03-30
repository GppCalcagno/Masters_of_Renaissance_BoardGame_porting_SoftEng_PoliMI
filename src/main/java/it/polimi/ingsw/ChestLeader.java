package it.polimi.ingsw;

public class ChestLeader extends LeaderAction{
    /**
     * This is the constructor method
     *
     * @param victoryPoints number of victory points
     */
    public ChestLeader(int victoryPoints) {
        super(victoryPoints);
    }

    /**
     * This method activates the Special Ability that adds an extra special 2-slot depot. This special depot can only store the indicated Resources.
     * @param player that uses the Leader Card
     */
    @Override
    public void doSpecialAbility(Player player) {
        //to implement
    }
}
