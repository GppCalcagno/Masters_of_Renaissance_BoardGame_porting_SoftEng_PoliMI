package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

import java.util.Map;

public class MessageUpdateStrongbox extends Message {
    private static final long serialVersionUID = 1138583085556221628L;

    private Map<String,Integer> strongbox;

    public MessageUpdateStrongbox(String nickname, Map<String, Integer> strongbox) {
        super(nickname, MessageType.UPDATESTRONGBOX);
        this.strongbox = strongbox;
    }

    public Map<String, Integer> getStrongbox() {
        return strongbox;
    }
}
