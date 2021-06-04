package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateCurrPlayer extends Update {
    private static final long serialVersionUID = -4542117400168457410L;

    public UpdateCurrPlayer(String nickname) {
        super(nickname);
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setCurrentPlayer(getNickname());
        view.onUpdateCurrPlayer();
    }
}
