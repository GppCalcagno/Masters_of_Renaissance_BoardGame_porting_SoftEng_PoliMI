package it.polimi.ingsw.Network.message;

import java.util.Map;

public class MessageUpdateWinnerFinalPoints extends Message{
    private static final long serialVersionUID = 2509383771998500986L;

    private int playerWinner;

    private Map<String, Integer> playersPoints;

    public MessageUpdateWinnerFinalPoints(String nickname, int playerWinner, Map<String, Integer> playersPoints) {
        super(nickname, MessageType.UPDATEWINNER);
        this.playerWinner = playerWinner;
        this.playersPoints = playersPoints;
    }

    public int getPlayerWinner() {
        return playerWinner;
    }

    public Map<String, Integer> getPlayersPoints() {
        return playersPoints;
    }
}
