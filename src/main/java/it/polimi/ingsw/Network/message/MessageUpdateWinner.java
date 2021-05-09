package it.polimi.ingsw.Network.message;

public class MessageUpdateWinner extends Message{
    private static final long serialVersionUID = 2509383771998500986L;

    private int playerWinner;

    public MessageUpdateWinner(String nickname, int playerWinner) {
        super(nickname, MessageType.UPDATEWINNER);
        this.playerWinner = playerWinner;
    }

    public int getPlayerWinner() {
        return playerWinner;
    }
}
