package it.polimi.ingsw.Network.message;

public class MessageUpdateFaithMarker extends Message{
    private static final long serialVersionUID = -9068599452862309716L;

    private int faithPoints;

    public MessageUpdateFaithMarker(String nickname, int faithPoints) {
        super(nickname, MessageType.UPDATEFAITHPOINTS);
        this.faithPoints = faithPoints;
    }
}
