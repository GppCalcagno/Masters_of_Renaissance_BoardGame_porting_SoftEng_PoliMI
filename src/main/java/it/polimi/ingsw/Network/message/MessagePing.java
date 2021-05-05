package it.polimi.ingsw.Network.message;

public class MessagePing extends Message{
    private static final long serialVersionUID= -8052982982220258094L;

    public MessagePing() {
        super(null,MessageType.PING) ;
    }
}
