package it.polimi.ingsw.Network.message;

public class MessageUpdateStateLeaderAction extends Message{
    private static final long serialVersionUID = 9087042341178319311L;

    private int pos;

    //0 per scartarla, 1 per attivarla
    private boolean choice;

    public MessageUpdateStateLeaderAction(String nickname, MessageType messageType, int pos) {
        super(nickname, messageType);
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
