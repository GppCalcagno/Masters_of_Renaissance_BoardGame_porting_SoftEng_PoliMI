package it.polimi.ingsw.Network.message;

import it.polimi.ingsw.model.producible.Resources;

public class MessageChosenResourceBaseProduction extends Message{
    private static final long serialVersionUID = -5229293564538828139L;

    private String resource;

    public MessageChosenResourceBaseProduction(String nickname, String resources) {
        super(nickname, MessageType.CHOOSERESOURCESBASEPRODUCTION);
        this.resource = resources;
    }

    public String getResource() {
        return resource;
    }
}
