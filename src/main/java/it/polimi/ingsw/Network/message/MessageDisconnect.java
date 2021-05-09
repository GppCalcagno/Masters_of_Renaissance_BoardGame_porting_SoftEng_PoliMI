package it.polimi.ingsw.Network.message;

public class MessageDisconnect extends Message{

    public MessageDisconnect(String nickname) {
        super(nickname, MessageType.DISCONNECT);
    }
}
