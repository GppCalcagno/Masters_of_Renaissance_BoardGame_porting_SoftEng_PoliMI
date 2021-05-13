package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.LeaderAction;

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
    public boolean doSpecialAbility(Player player) {
        if (getActivated())
            return false;
        else {
            if (!getCost().checkResources(player))
                return false;
            else {
                player.getWarehouse().addleaderCardEffect(getResources());
                setActivated();
                return true;
            }
        }
    }

    @Override
    public void showCli() {

    }
}
