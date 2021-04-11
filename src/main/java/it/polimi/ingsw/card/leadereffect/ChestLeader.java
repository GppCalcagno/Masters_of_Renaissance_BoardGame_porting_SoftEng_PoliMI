package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.requirements.RequirementsLeader;

public class ChestLeader extends LeaderAction {
    /**
     * This is the constructor method
     */
    public ChestLeader(){
    }

    /**
     * This method activates the Special Ability that adds an extra special 2-slot depot. This special depot can only store the indicated Resources.
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
                player.getWarehouse().addleaderCardEffect(getResources());
                setActivated();
            }
        }
    }
}
