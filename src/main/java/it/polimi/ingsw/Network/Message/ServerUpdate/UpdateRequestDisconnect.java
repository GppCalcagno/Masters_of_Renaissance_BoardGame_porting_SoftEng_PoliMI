package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateRequestDisconnect extends Update {


    public UpdateRequestDisconnect(String nickname) { super(nickname);}

    @Override
    public void update(ViewInterface view) {
        view.onDisconnect();
    }
}
