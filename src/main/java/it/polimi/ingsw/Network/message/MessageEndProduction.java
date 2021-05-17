package it.polimi.ingsw.Network.message;

public class MessageEndProduction extends Message{

    private static final long serialVersionUID = -2193109517171387273L;

    public MessageEndProduction(String nickname) {
        super(nickname, MessageType.ENDPRODUCTION);
    }
}
