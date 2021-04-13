package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.requirements.RequirementsLeader;

public class ProductionLeader extends LeaderAction {
    /**
     * This is the constructor method
     */
    public ProductionLeader() {
    }

    /**
     * This method activates the Special Ability that trades a Resource, which is indicated by the card, with a Resource of player's choosing and 1 Faith Point
     * @param player that uses the Leader Card
     */
    @Override
    public boolean doSpecialAbility(Player player) {
        if (getActivated())
            return false;
        else {
            if (!getCost().checkResources(player))
                return false;
            else {
                player.getSlotDevCards().addLeaderCardEffect(getResources());
                setActivated();
                return true;
            }
        }
    }
}
