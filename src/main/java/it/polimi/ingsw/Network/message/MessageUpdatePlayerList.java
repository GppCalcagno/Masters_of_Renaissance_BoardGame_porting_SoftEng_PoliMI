package it.polimi.ingsw.Network.message;

import java.util.List;

public class MessageUpdatePlayerList extends Message{
    List<String> playerList;

    public MessageUpdatePlayerList(String nickname, MessageType messageType) {
        super(nickname, messageType);

    }
}
