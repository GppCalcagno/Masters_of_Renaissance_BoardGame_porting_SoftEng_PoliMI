package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

public class MessageLogin extends Message{
    private static final long serialVersionUID = -898685728584201368L;

    public MessageLogin(String nickname) {
        super(nickname, MessageType.LOGIN);
    }

    @Override
    public void action(GameController gameController) {

    }
}
