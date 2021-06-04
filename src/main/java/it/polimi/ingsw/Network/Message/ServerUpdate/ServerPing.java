package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class ServerPing extends Update {


    public ServerPing() {
        super("server");
    }

    @Override
    public void update(ViewInterface view) {
        //nothing to do
    }
}
