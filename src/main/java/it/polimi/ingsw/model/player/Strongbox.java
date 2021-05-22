package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.producible.*;

import java.util.HashMap;
import java.util.Map;

public class Strongbox {

    /**
     * this attirbute is a map where are stored the resources whit the integer that describes the amount
     */
    private  Map<String, Integer> chest;

    /**
     * this is the costructor of Strongbox
     */
    public Strongbox() {
        chest = new HashMap<String, Integer>();
        chest.put(new Coins().toString(),0);
        chest.put(new Servants().toString(),0);
        chest.put(new Shields().toString(),0);
        chest.put(new Stones().toString(),0);
    }

    /**
     * this method is a getter of the number of a type of resources in Strongbox
     * @param res is the selected Resources
     * @return the number of the seleceted Resources
     */
    public int getNumResources(Resources res){
        return chest.get(res.toString());
    }

    /**
     * this method is a getter of the number of a type of resources in Strongbox
     * @param res is the selected Resources
     * @return the number of the seleceted Resources
     */
    public int getNumResources(String res){
        return chest.get(res);
    }

    /** this  method update the resources in the strongbox, it can add o delete depending which other method
     * call this.
     * throws an QuantityExceptions if negative quantity
     * @param res the type of the resources that amount have to change
     * @param quantity how many resources you want to add (int can be negative for the delete function).
     */
    public void updateResources(Resources res, int quantity) throws NegativeQuantityExceptions {
        int oldQuantity= chest.get(res.toString());
        if(oldQuantity+quantity<0) throw new NegativeQuantityExceptions();
        chest.put(res.toString(),oldQuantity+quantity);

    }

    /** this  method update the resources in the strongbox, it can add o delete depending which other method
     * call this.
     * throws an QuantityExceptions if negative quantity
     * @param res the type of the resources that amount have to change
     * @param quantity how many resources you want to add (int can be negative for the delete function).
     */
    public void updateResources(String res, int quantity) throws NegativeQuantityExceptions {
        int oldQuantity= chest.get(res);
        if(oldQuantity+quantity<0) throw new NegativeQuantityExceptions();
        chest.put(res,oldQuantity+quantity);

    }

    public Map<String, Integer> getChest() {
        return chest;
    }
}