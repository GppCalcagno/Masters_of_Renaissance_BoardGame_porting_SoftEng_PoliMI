package it.polimi.ingsw.Network.message;

public class MessageDiscardLeaderAction extends Message{
    private static final long serialVersionUID = 1725783539179043066L;

    private int pos;

    public MessageDiscardLeaderAction(String nickname, MessageType messageType, int pos) {
        super(nickname, messageType);
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
