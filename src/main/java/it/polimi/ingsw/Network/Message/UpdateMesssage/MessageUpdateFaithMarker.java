package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

import java.util.Map;

public class MessageUpdateFaithMarker extends Message {
    private static final long serialVersionUID = -9068599452862309716L;

    private Map<String, Integer> playersPosition;

    private Map<String, boolean[]> playersPopFavoriteTile;

    public MessageUpdateFaithMarker( Map<String, Integer> playersPosition, Map<String,boolean[]> playersPopFavoriteTile) {
        super("server", MessageType.UPDATEFAITHPOINTS);
        this.playersPosition=playersPosition;
        this.playersPopFavoriteTile = playersPopFavoriteTile;
    }

    public Map<String, Integer> getPlayersPosition() {
        return playersPosition;
    }

    public Map<String, boolean[]> getPlayersPopFavoriteTile() {
        return playersPopFavoriteTile;
    }


    @Override
    public void update(PlayerBoard playerBoard) {
        super.update(playerBoard);
    }
}
