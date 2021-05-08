package it.polimi.ingsw.Network.message;

public class MessageGeneric extends Message{
    private static final long serialVersionUID = 4564132178552808674L;

    public MessageGeneric(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
}
