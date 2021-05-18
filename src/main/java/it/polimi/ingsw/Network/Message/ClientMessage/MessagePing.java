package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessagePing extends Message {
    private static final long serialVersionUID= -8052982982220258094L;

    public MessagePing() {
        super(null, MessageType.PING) ;
    }

}
