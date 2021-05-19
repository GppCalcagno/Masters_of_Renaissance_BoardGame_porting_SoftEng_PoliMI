package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageError extends Message {
    private static final long serialVersionUID = 84642072058199835L;

    String errorType;


    public MessageError(String nickname, String errorType) {
        super(nickname, MessageType.ERROR);
        this.errorType=errorType;
    }

    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view){
        view.onUpdateError(errorType);
    }
}
