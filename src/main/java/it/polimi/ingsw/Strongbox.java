package it.polimi.ingsw;

import java.util.HashMap;
import java.util.Map;

public class Strongbox {

    /**
     * a map where are stored the resources whit the integer that describes the amount
     */
    private Map<Resources, Integer> chest;

    public Strongbox() {
        chest = new HashMap<Resources, Integer>(); // da rivedere
    }

    public int getNumResources(Resources res) {
        return 0;
    }

    /**
     * this  method update the resources in the strongbox, it can add o delete depending which other method
     * call this.
     *
     * @param res      the type of the resources that amount have to change
     * @param quantity how many resources you want to add (int can be negative for the delete function)
     */
    public void updateResources(Resources res, int quantity) {

    }
}
