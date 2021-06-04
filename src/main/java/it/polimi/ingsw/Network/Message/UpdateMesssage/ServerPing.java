package it.polimi.ingsw.Network.Message.UpdateMesssage;

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
