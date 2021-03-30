package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.card.LeaderAction;

public class ProductionLeader extends LeaderAction {
    /**
     * This is the constructor method
     *
     * @param victoryPoints number of victory points
     */
    public ProductionLeader(int victoryPoints) {
        super(victoryPoints);
    }

    /**
     * This method activates the Special Ability that trades a Resource, which is indicated by the card, with a Resource of player's choosing and 1 Faith Point
     * @param player that uses the Leader Card
     */
    @Override
    public void doSpecialAbility(Player player) {
        //to implement
    }
}
