package it.polimi.ingsw.network.message;

public class MessageNumPlayers extends Message {
    private int numPlayers;

    public MessageNumPlayers(String nickname, MessageType messageType, int numPlayers) {
        super(nickname, messageType);
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
