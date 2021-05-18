package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageRequestNumPlayers extends Message {
    private static final long serialVersionUID = 5203402987810310308L;

    public MessageRequestNumPlayers(String nickname) {
        super(nickname, MessageType.REQUESTNUMPLAYERS);
    }

    //todo wip
}
