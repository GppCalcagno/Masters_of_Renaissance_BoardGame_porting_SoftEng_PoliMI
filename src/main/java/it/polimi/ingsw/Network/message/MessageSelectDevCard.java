package it.polimi.ingsw.Network.message;

public class MessageSelectDevCard extends Message{

    private int x;
    private int y;

    public MessageSelectDevCard(String nickname, MessageType messageType, int x, int y){
        super(nickname, messageType);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
