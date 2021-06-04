package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdatePlayerDisconnected extends Update {
    private static final long serialVersionUID = 4946426206965951088L;

    public UpdatePlayerDisconnected(String nickname) {
        super(nickname);
    }

    @Override
    public void update(ViewInterface view) {
        view.onPlayerDisconnect(getNickname());
    }
}
