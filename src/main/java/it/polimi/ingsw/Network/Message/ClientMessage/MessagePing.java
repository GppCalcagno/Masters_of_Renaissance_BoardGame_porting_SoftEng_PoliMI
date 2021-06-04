package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessagePing extends Message {
    private static final long serialVersionUID = 968670148510735231L;

    public MessagePing() {
        super("ping", MessageType.PING);
    }
}
