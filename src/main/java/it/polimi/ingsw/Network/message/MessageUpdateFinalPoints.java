package it.polimi.ingsw.Network.message;

import java.util.Map;

public class MessageUpdateFinalPoints extends Message{
    private static final long serialVersionUID = -8002331487438650730L;

    private Map<String, Integer> playersPoints;

    public MessageUpdateFinalPoints(String nickname, Map<String, Integer> playersPoints) {
        super(nickname, MessageType.UPDATEFINALPOINTS);
        this.playersPoints = playersPoints;
    }

    public Map<String, Integer> getPlayersPoints() {
        return playersPoints;
    }
}
