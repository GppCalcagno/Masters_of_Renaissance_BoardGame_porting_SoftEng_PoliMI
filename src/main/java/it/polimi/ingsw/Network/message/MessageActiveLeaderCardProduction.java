package it.polimi.ingsw.Network.message;

import it.polimi.ingsw.model.producible.Resources;

public class MessageActiveLeaderCardProduction extends Message{
    private static final long serialVersionUID = 8021603830645282467L;

    private int indexExtraProduction;

    private int WareStrongChest;

    private Resources resource;

    public MessageActiveLeaderCardProduction(String nickname, int indexExtraProduction, int wareStrongChest, Resources resource) {
        super(nickname, MessageType.ACTIVELEADERCARDPRODUCTION);
        this.indexExtraProduction = indexExtraProduction;
        WareStrongChest = wareStrongChest;
        this.resource = resource;
    }

    public int getIndexExtraProduction() {
        return indexExtraProduction;
    }

    public int getWareStrongChest() {
        return WareStrongChest;
    }

    public Resources getResource() {
        return resource;
    }
}
