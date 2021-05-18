package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateInfo extends Message {
    private static final long serialVersionUID = 8241870936095822512L;

    private String info;

    public MessageUpdateInfo(String nickname, String info) {
        super(nickname, MessageType.UPDATEINFO);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void update(PlayerBoard playerBoard) {
        super.update(playerBoard);
    }
}
