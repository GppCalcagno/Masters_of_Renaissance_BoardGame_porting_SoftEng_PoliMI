package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.LeaderAction;

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

    @Override
    public void showCli() {

        System.out.println("DISCOUNT LEADER");
        //costo
        System.out.println("Requirement : ");
        this.getCost().showReq();
        //victory points
        System.out.println("Victory points : ");
        System.out.println(getVictoryPoints());
        //risorsa che disconta
        System.out.println("Discount what : ");
        System.out.println(getResources());

    }
}
