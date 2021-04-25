package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.producible.Resources;

public class ExtraChest {

    /**
     * the resources stored in the extrachest
     */
    private Resources resources;

    /**
     * the number of resources stored (maximum 2)
     */
    private int num;

    /**
     * this is the costuctor of the class
     * @param resources type of resource stored in ExtraChest
     */
    public ExtraChest(Resources resources) {
        this.resources = resources;
        num=0;
    }

    /**
     * this is the constructor the initialized the number of resources inside the chestbox = 0
     */
    public  ExtraChest(){
        num = 0;
    }

    /**
     * this method update the amount of resources inside the chest
     * @param n the new amount of resources
     */
    public void updateResources(int n) throws OverflowQuantityExcepions, NegativeQuantityExceptions {

        if((num+ n)>2) throw new OverflowQuantityExcepions();
        if((num+ n)<0) throw new NegativeQuantityExceptions();

        num+= n;
    }

    /**
     * this method return the type of resources inside the chests
     * @return a type of resources
     */
    public Resources getResources(){
        return resources;
    }

    /**
     * this method return the number of Resources inside the chest (0,1,2)
     * @return the amount of resources
     */
    public int getnum(){
        return num;
    }
}
