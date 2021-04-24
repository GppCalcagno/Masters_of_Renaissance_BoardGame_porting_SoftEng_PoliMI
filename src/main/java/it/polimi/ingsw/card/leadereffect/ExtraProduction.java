package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;

public class ExtraProduction {
    /**
     * This attribute indicates the requested resource that activate the extra Production of the relative Leader Card
     */
    private Resources resources;

    /**
     * this is the costructor of the method
     * @param resources is the type of the effect
     */
    public ExtraProduction(Resources resources){
        this.resources=resources;
    }

    /**
     * this method active the extra production
     * @param player the player that call this method to add one faith point
     * @param reqResources the resources that the player wants to return and add it to the buffer's player
     */
    public void activeExtraProduction(Player player, Resources reqResources) throws ActiveVaticanReportException {
        //non tolgo la risorsa qui perchè non so quale togliere(da Warehouse o Stronbox), controllo risorsa ed
        //eliminazione prima di chiamare metodo
        player.increasefaithMarker();
        try {
            player.getStrongbox().updateResources(reqResources,1);
        } catch (NegativeQuantityExceptions I) {}

    }

    /**
     * this method specify the type of resource requested
     * @return the type of resource requested
     */
    public Resources getResources(){
        return resources;
    }

}
