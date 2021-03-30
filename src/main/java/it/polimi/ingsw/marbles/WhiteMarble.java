package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WhiteMarble extends Marbles {
    /**
     * This parameter indicates if it is activated a Leader Card with the special ability that converts the white Marble into a resources
     */
    private List<Optional<Marbles>> leaderCardEffect; //da controllare

    /**
     * This is the constructor
     */
    public WhiteMarble() {
        leaderCardEffect = new ArrayList<>();
    }

    /**
     * This method converts a white Marble into nothing, if it is taken from the market, or into a resources chosen by the player if it is activated the relative Leader Card
     * @param p reference to the player's Warehouse depot
     */
    @Override
    public void addtoWarehouse(Player p) {
        //to implement
    }

    /**
     * This method is empty for the white marble
     */
    @Override
    public void discard() {
        //to implement
    }
}
