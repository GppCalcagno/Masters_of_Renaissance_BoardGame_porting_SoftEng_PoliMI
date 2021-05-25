package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageRequestNumPlayers extends Message {
    private static final long serialVersionUID = 5203402987810310308L;

    public MessageRequestNumPlayers() {
        super("server", MessageType.REQUESTNUMPLAYERS);
    }

    @Override
    public void update(ViewInterface view) {
        view.askNumPlayer();
    }

}
