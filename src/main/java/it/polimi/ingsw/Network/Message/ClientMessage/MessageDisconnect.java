package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageDisconnect extends Message {
    private static final long serialVersionUID = -2514778425230529656L;

    public MessageDisconnect(String nickname) {
        super(nickname);
    }

    @Override
    public void action(GameController gameController) {
        gameController.disconnect(super.getNickname());
    }
}
