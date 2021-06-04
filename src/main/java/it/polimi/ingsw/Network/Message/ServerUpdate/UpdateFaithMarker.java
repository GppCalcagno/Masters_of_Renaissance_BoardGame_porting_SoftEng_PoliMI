package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class UpdateFaithMarker extends Update {
    private static final long serialVersionUID = -9068599452862309716L;

    private Map<String, Integer> playersPosition;

    private Map<String, boolean[]> playersPopFavoriteTile;

    private int blackcrosstoken;

    private boolean removeMarblefromBuffer;

    public UpdateFaithMarker(String player, Map<String, Integer> playersPosition, Map<String,boolean[]> playersPopFavoriteTile, boolean removeMarblefromBuffer, int blackcrosstoken) {
        super(player);
        this.playersPosition=playersPosition;
        this.playersPopFavoriteTile = playersPopFavoriteTile;
        this.removeMarblefromBuffer=removeMarblefromBuffer;
        this.blackcrosstoken=blackcrosstoken;
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setFaithMarker(playersPosition,playersPopFavoriteTile, blackcrosstoken);
        view.getPlayerBoard().removemarblefromBuffer(removeMarblefromBuffer);
        view.onUpdateFaithMarker();
    }
}
