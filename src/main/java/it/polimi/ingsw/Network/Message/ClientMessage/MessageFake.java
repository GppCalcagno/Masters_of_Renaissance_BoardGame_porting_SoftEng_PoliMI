package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageFake extends Message {
    public MessageFake(String nickname) {
        super(nickname);
    }

    @Override
    public void action(GameController gameController) {
        gameController.cheat();
    }
}
