package it.polimi.ingsw.Network.message;

public class MessageInsertCard extends  Message{

    private int y;

    public MessageInsertCard(String nickname, MessageType messageType, int y) {
        super(nickname, messageType);
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
