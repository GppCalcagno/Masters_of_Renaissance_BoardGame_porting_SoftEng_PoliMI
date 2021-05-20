package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.producible.Shields;
import it.polimi.ingsw.model.producible.Stones;

public class GreyMarble extends Marbles {
    /**
     * This method converts a grey marble into a stone
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) {
        return p.getWarehouse().checkInsertion(i,new Stones());
    }

    /**
     * This method adds the converted Resource from the caught Marble to the Extra Chest, if the relative Leader Card is activated
     * @param p reference to the player's Extra Chest
     * @return true if the add is successful
     */
    @Override
    public boolean addToExtraChest(Player p) {
        Resources grey = new Stones();
        for (int i = 0; i < p.getWarehouse().getLeaderCardEffect().size(); i++) {
            if (p.getWarehouse().getLeaderCardEffect().get(i).getResources().getClass().equals(grey.getClass())) {
                try {
                    p.getWarehouse().getLeaderCardEffect().get(i).updateResources(1);
                    return true;
                } catch (OverflowQuantityExcepions overflowQuantityExcepions) {
                    return false;
                } catch (NegativeQuantityExceptions ignored) {}
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Stones";
    }
}
