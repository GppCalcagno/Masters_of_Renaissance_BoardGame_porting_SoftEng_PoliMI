package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageDisconnect extends Message {
    public MessageDisconnect(String nickname) {
        super(nickname, MessageType.DISCONNECT);
    }

    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        view.onDisconnect();
    }
}
