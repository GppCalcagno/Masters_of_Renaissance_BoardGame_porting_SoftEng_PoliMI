package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.card.LeaderAction;

public class DiscountLeader extends LeaderAction {
    /**
     * This is the constructor method
     */
    public DiscountLeader(){
    }

    /**
     * This method activates the special ability that discounts a resource, indicated in the "resources" attribute, from the cost of a Development Card
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
                player.addleaderCardEffectDiscount(getResources());
                setActivated();
                return true;
            }
        }
    }
}
