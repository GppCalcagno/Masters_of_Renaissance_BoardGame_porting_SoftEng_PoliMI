package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageDisconnect extends Message{

    public MessageDisconnect(String nickname) {
        super(nickname, MessageType.DISCONNECT);
    }

    @Override
    public void action(GameController gameController) {

    }
}
