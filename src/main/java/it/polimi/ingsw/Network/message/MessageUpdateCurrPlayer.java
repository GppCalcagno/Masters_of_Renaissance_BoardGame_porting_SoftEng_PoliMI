package it.polimi.ingsw.Network.message;

public class MessageUpdateCurrPlayer extends Message{
    private static final long serialVersionUID = -4542117400168457410L;

    public MessageUpdateCurrPlayer(String nickname) {
        super(nickname, MessageType.UPDATECURRENTPLAYER);
    }
}
