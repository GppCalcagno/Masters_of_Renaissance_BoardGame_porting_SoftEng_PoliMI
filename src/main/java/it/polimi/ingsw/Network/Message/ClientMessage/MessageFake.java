package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageFake extends Message {
    public MessageFake(String nickname) {
        super(nickname, MessageType.BUYDEVCARD);
    }

    @Override
    public void action(GameController gameController) {
        gameController.fakeTaxi();
    }
}
