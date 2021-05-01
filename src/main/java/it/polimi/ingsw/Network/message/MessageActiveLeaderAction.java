package it.polimi.ingsw.Network.message;

public class MessageActiveLeaderAction extends Message{
    private static final long serialVersionUID = 9087042341178319311L;

    private int pos;

    public MessageActiveLeaderAction(String nickname, MessageType messageType, int pos) {
        super(nickname, messageType);
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
