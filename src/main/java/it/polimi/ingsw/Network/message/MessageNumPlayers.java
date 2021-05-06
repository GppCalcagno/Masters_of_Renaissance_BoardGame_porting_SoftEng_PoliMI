package it.polimi.ingsw.Network.message;

public class MessageNumPlayers extends Message {
    private static final long serialVersionUID = -81224732564833427L;

    private int numPlayers;

    public MessageNumPlayers(String nickname, int numPlayers) {
        super(nickname, MessageType.NUMPLAYERS);
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
