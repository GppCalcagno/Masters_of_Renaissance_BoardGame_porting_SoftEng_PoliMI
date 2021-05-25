package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdatePlayerState extends Message {
    private static final long serialVersionUID = 4946426206965951088L;

    private boolean isConnected;

    public MessageUpdatePlayerState(String nickname,boolean isConnected ) {
        super(nickname, MessageType.UPDATEPLAYERSTATE);
        this.isConnected =isConnected;

    }

    @Override
    public void update(ViewInterface view) {
        view.onUpdatePlayerState(getNickname(),isConnected);
    }
}
