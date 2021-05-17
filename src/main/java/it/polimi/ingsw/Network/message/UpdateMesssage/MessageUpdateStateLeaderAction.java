package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

public class MessageUpdateStateLeaderAction extends Message {
    private static final long serialVersionUID = 2309901068199675655L;

    private String ID;
    private boolean activated;

    public MessageUpdateStateLeaderAction(String nickname, String ID, boolean activated) {
        super(nickname, MessageType.UPDATESTATELEADERCARDS);
        this.ID = ID;
        this.activated = activated;
    }

    public String getID() {
        return ID;
    }

    public boolean isActivated() {
        return activated;
    }
}
