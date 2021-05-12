package it.polimi.ingsw.Network.message;

import java.util.Map;

public class MessageUpdateFaithMarker extends Message{
    private static final long serialVersionUID = -9068599452862309716L;

    private int faithPoints;

    private Map<String, boolean[]> playersPopFavoriteTile;

    public MessageUpdateFaithMarker(String nickname, int faithPoints, Map<String, boolean[]> playersPopFavoriteTile) {
        super(nickname, MessageType.UPDATEFAITHPOINTS);
        this.faithPoints = faithPoints;
        this.playersPopFavoriteTile = playersPopFavoriteTile;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public Map<String, boolean[]> getPlayersPopFavoriteTile() {
        return playersPopFavoriteTile;
    }
}
