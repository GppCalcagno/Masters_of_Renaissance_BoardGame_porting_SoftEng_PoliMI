package it.polimi.ingsw.Network.message;

public class MessageUpdateCurrPlayer extends Message{
    String player;

    public MessageUpdateCurrPlayer(String nickname) {
        super(nickname, MessageType.UPDATECURRENTPLAYER);
    }
}
