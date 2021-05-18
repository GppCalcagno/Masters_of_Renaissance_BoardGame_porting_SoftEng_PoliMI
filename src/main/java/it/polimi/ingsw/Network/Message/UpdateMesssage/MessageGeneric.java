package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageGeneric extends Message {
    private static final long serialVersionUID = 4564132178552808674L;

    //?????

    public MessageGeneric(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
}
