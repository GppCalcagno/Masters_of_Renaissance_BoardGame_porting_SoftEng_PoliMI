package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageWaitingForOtherPlayer extends Message {
    private static final long serialVersionUID = 1022749417453669552L;

    public MessageWaitingForOtherPlayer() {
        super("server", MessageType.WAITINGOTHERPLAYERS);
    }

    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        view.showMessage("Waiting for Other Player");
    }


}
