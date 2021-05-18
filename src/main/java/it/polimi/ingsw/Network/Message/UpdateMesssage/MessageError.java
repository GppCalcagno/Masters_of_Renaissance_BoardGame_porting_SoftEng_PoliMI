package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageError extends Message {
    String errorType;


    public MessageError(String nickname, MessageType messageType,String errorType) {
        super(nickname, messageType.ERROR);
        this.errorType=errorType;
    }

    @Override
    public void update(PlayerBoard playerBoard){
        //show
    }
}
