package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.LeaderAction;

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
                setActivated();
                return true;
            }
        }
    }

    @Override
    public void showCli() {
        System.out.println("PRODUCTION LEADER\n" +
                "This card activates a production that at the cost of 1 "+getResources()+" gives you 1 choice resource + 1 FaithMarker");

        System.out.print("Requirement : ");
        this.getCost().showReq();
        System.out.println("Victory points : "+getVictoryPoints());


    }
}
