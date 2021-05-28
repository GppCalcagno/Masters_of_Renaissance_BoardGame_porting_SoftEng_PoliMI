package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdatePlayerDisconnected extends Message {
    private static final long serialVersionUID = 4946426206965951088L;

    private boolean isConnected;

    public MessageUpdatePlayerDisconnected(String nickname) {
        super(nickname, MessageType.UPDATEPLAYERSTATE);
    }

    @Override
    public void update(ViewInterface view) {
        view.onPlayerDisconnect(getNickname());
    }
}
