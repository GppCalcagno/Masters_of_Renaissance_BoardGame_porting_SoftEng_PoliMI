package it.polimi.ingsw.Network.message;

public class MessageSelectTransformationWhiteMarble extends Message{
    private static final long serialVersionUID = -634200199040721658L;

    private int i;

    public MessageSelectTransformationWhiteMarble(String nickname, int i) {
        super(nickname, MessageType.SELECTTRANSFORMATIONWHITEMARBLE);
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
