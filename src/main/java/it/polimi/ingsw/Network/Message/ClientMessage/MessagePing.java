package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessagePing extends Message {
    private static final long serialVersionUID = 968670148510735231L;

    public MessagePing() {
        super("ping");
    }

    public void action(GameController gameController) {
        //nothing to do
    }
}
