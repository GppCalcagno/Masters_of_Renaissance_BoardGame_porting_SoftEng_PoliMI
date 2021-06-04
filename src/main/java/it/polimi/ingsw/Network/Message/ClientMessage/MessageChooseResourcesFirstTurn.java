package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

import java.util.List;

public class MessageChooseResourcesFirstTurn extends Message {
    private static final long serialVersionUID = 8520080685328238725L;

    private List<String> resourcesList;

    public MessageChooseResourcesFirstTurn(String nickname, List<String> resourcesList) {
        super(nickname);
        this.resourcesList = resourcesList;
    }

    @Override
    public void action(GameController gameController) {
        gameController.chooseInitialResources(resourcesList);
    }
}
