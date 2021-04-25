package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.player.Player;

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
}
