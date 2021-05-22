package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.LeaderAction;

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
    public boolean doSpecialAbility(Player player) {
        if (getActivated())
            return false;
        else {
            if (!getCost().checkResources(player))
                return false;
            else {
                player.addleaderCardEffectWhiteMarble(getResources());
                setActivated();
                return true;
            }
        }
    }

    @Override
    public void showCli() {
        System.out.println("TRANSFORM WITHE MARBLE "+getID()+"\n" +
                "This card allows you to transform  white marble into a "+getResources()+" Resources marble");
        //cost
        System.out.print("Requirement : ");
        this.getCost().showReq();
        //victory points
        System.out.println("Victory points : "+getVictoryPoints());


    }
}
