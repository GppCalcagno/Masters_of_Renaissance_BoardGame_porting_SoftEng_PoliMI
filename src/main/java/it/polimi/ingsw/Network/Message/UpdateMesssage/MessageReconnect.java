package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageReconnect extends Message {


    public MessageReconnect(String nickname) {
        super(nickname, MessageType.RECONNECT);
    }
}
