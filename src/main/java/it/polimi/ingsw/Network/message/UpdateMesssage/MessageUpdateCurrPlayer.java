package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

public class MessageUpdateCurrPlayer extends Message {
    private static final long serialVersionUID = -4542117400168457410L;

    public MessageUpdateCurrPlayer(String nickname) {
        super(nickname, MessageType.UPDATECURRENTPLAYER);
    }
}
