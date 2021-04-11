package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.requirements.RequirementsLeader;

public class TrasformationMarbleLeader extends LeaderAction {
    /**
     * This is the constructor method
     */
    public TrasformationMarbleLeader() {
    }

    /**
     * This method activates the Special Effect that, when the player takes Resources from the Market, for each white Marble in the chosen column/row gives to him the indicated Resource
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
                player.getSlotDevCards().addleaderCardEffect(getResources());
                setActivated();
            }
        }
    }
}
