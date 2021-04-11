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
    public void doSpecialAbility(Player player) {
        if (getActivated())
            System.out.println("This Leader Card has been already activated");
        else {
            if (!getCost().checkResources(player))
                System.out.println("You don't have the requirements to buy this Leader Card");
            else {
                player.addleaderCardEffectDiscount(getResources());
                setActivated();
            }
        }
    }
}
