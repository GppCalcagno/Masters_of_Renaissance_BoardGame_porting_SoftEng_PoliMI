package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class MessageUpdateFaithMarker extends Message {
    private static final long serialVersionUID = -9068599452862309716L;

    private Map<String, Integer> playersPosition;

    private Map<String, boolean[]> playersPopFavoriteTile;

    private int blackcrosstoken;

    private boolean removeMarblefromBuffer;

    public MessageUpdateFaithMarker(String player, Map<String, Integer> playersPosition, Map<String,boolean[]> playersPopFavoriteTile, boolean removeMarblefromBuffer, int blackcrosstoken) {
        super(player, MessageType.UPDATEFAITHPOINTS);
        this.playersPosition=playersPosition;
        this.playersPopFavoriteTile = playersPopFavoriteTile;
        this.removeMarblefromBuffer=removeMarblefromBuffer;
        this.blackcrosstoken=blackcrosstoken;
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.setFaithMarker(playersPosition,playersPopFavoriteTile, blackcrosstoken);
        playerBoard.removemarblefromBuffer(removeMarblefromBuffer);

        view.onUpdateFaithMarker();
    }
}
