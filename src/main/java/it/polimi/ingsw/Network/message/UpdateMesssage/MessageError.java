package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

public class MessageError extends Message {
    String errorType;


    public MessageError(String nickname, MessageType messageType,String errorType) {
        super(nickname, messageType.ERROR);
        this.errorType=errorType;
    }


}
