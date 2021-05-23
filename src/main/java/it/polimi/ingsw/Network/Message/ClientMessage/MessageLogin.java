package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageLogin extends Message {
    private static final long serialVersionUID = -898685728584201368L;

    public MessageLogin(String nickname) {
        super(nickname, MessageType.LOGIN);
    }

    @Override
    public void action(GameController gameController) {
        gameController.onLoginPhase(super.getNickname());
    }
}
