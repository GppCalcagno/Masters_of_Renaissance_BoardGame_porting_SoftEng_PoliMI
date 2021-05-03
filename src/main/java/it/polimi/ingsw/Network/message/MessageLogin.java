package it.polimi.ingsw.Network.message;

public class MessageLogin extends Message{
    private static final long serialVersionUID = 8275432588471932985L;

    public MessageLogin(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
}
