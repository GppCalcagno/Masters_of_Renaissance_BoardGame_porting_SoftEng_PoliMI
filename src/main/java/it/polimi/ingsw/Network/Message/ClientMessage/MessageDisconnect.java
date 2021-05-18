package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageDisconnect extends Message {

    public MessageDisconnect(String nickname) {
        super(nickname, MessageType.DISCONNECT);
    }

    @Override
    public void action(GameController gameController) {
        //todomethod

    }
}
