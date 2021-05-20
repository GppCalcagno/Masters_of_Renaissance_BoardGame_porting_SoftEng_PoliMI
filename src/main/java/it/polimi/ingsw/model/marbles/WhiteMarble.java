package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.producible.Shields;

public class WhiteMarble extends Marbles {
    /**
     * This method converts a white Marble into nothing, if it is taken from the market, or into a resources chosen by the player if it is activated the relative Leader Card
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) {
        if(!p.getLeaderCardEffectWhiteMarble().isEmpty())
            // Controlla se è possibile inserire la prima risorsa di leaderCardEffectWhiteMarble (da inserire solo nel magazzino)
            return p.getWarehouse().checkInsertion(i , p.getLeaderCardEffectWhiteMarble().get(0));
        else return true;
    }

    /**
     * This method adds the converted Resource from the caught Marble to the Extra Chest, if the relative Leader Card is activated
     * @param p reference to the player's Extra Chest
     * @return true if the add is successful
     */
    @Override
    public boolean addToExtraChest(Player p) {
        if(!p.getLeaderCardEffectWhiteMarble().isEmpty()){
            Resources white = p.getLeaderCardEffectWhiteMarble().get(0);
            for (int i = 0; i < p.getWarehouse().getLeaderCardEffect().size(); i++) {
                if (p.getWarehouse().getLeaderCardEffect().get(i).getResources().getClass().equals(white.getClass())) {
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
        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
