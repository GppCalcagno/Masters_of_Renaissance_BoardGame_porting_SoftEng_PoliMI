package it.polimi.ingsw.Network.message;

import it.polimi.ingsw.model.producible.Resources;

import java.util.List;

public class MessageChooseResources extends Message{
    private static final long serialVersionUID = 8520080685328238725L;

    private List<Resources> resourcesList;

    public MessageChooseResources(String nickname, MessageType messageType, List<Resources> resourcesList) {
        super(nickname, messageType);
        this.resourcesList = resourcesList;
    }

    public List<Resources> getResourcesList() {
        return resourcesList;
    }
}
