package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateCurrPlayer extends Message {
    private static final long serialVersionUID = -4542117400168457410L;

    public MessageUpdateCurrPlayer(String nickname) {
        super(nickname, MessageType.UPDATECURRENTPLAYER);
    }


    @Override
    public void update(PlayerBoard playerBoard) {
        playerBoard.setCurrentPlayer(getNickname());
    }
}
