package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

import java.util.List;

public class MessageUpdatePlayerList extends Message {
    private static final long serialVersionUID = 4946426206965951088L;

    private List<String> playerList;

    public MessageUpdatePlayerList(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }

    public List<String> getPlayerList() {
        return playerList;
    }

}
