package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdateInfo extends Message {
    private static final long serialVersionUID = 8241870936095822512L;

    private String info;

    public MessageUpdateInfo(String nickname, String info) {
        super(nickname, MessageType.UPDATEINFO);
        this.info = info;
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        //show
    }
}
