package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateRequestDisconnect extends Update {


    public UpdateRequestDisconnect(String nickname) { super(nickname);}

    @Override
    public void update(ViewInterface view) {
        view.onDisconnect();
    }
}
