package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

import java.util.List;

public class MessageChooseResourcesFirstTurn extends Message{
    private static final long serialVersionUID = 8520080685328238725L;

    private List<String> resourcesList;

    public MessageChooseResourcesFirstTurn(String nickname, List<String> resourcesList) {
        super(nickname, MessageType.CHOOSERESOURCESFIRSTTURN);
        this.resourcesList = resourcesList;
    }

    public List<String> getResourcesList() {
        return resourcesList;
    }

    @Override
    public void action(GameController gameController) {

    }
}
