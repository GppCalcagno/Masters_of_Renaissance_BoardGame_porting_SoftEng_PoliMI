package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageLogin extends Message {
    private static final long serialVersionUID = -898685728584201368L;

    public MessageLogin(String nickname) {
        super(nickname);
    }

    @Override
    public void action(GameController gameController) {
        gameController.onLoginPhase(super.getNickname());
    }
}
