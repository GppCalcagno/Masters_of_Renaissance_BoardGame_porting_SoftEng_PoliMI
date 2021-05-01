package it.polimi.ingsw.Network.message;

public class MessageNumPlayers extends Message {
    private static final long serialVersionUID = -81224732564833427L;

    private int numPlayers;

    public MessageNumPlayers(String nickname, MessageType messageType, int numPlayers) {
        super(nickname, messageType);
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
