package it.polimi.ingsw.player;

import it.polimi.ingsw.producible.Resources;

import java.util.HashMap;
import java.util.Map;

public class Strongbox {

    /** a map where are stored the resources whit the integer that describes the amount
     */
    private Map<Resources, Integer> chest;

    public Strongbox() {
        chest = new HashMap<Resources, Integer>(); // da rivedere
    }

    public int getNumResources(Resources res) {
        return chest.get(res);
    }

    /** this  method update the resources in the strongbox, it can add o delete depending which other method
     * call this.
     * @param res the type of the resources that amount have to change
     * @param quantity how many resources you want to add (int can be negative for the delete function)
     */
    public void updateResources(Resources res, int quantity) {
        Integer v = 0;
        for (Map.Entry<Resources, Integer> entry : chest.entrySet()) {
            if(res == entry.getKey()) v = entry.getValue();
        }
        chest.put(res, quantity+v);
    }
}