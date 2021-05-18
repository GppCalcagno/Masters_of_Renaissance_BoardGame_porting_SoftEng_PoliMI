package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

public class MessageActiveLeaderCardProduction extends Message{
    private static final long serialVersionUID = 8021603830645282467L;

    private int indexExtraProduction;

    /**
     * This attribute indicates from where the player want to take the resource:
     * 0 Warehouse
     * 1 Strongbox
     * 2 ExtraChest
     */
    private int WareStrongChest;

    private String resource;

    public MessageActiveLeaderCardProduction(String nickname, int indexExtraProduction, int wareStrongChest, String resource) {
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

    public String getResource() {
        return resource;
    }


    @Override
    public void action(GameController gameController) {

    }
}
