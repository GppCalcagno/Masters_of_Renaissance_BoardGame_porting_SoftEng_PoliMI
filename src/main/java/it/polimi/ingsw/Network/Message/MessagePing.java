package it.polimi.ingsw.Network.Message;

public class MessagePing extends Message{
    private static final long serialVersionUID = 968670148510735231L;

    public MessagePing() {
        super("ping", MessageType.PING);
    }
}
