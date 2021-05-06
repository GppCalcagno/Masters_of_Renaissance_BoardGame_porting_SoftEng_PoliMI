package it.polimi.ingsw.Network.message;

public class MessageEndTurn extends Message{
    public MessageEndTurn(String nickname) {
        super(nickname, MessageType.ENDTURN);
    }
}
