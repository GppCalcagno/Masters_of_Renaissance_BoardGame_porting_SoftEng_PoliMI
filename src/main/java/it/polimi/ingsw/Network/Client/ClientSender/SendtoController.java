package it.polimi.ingsw.Network.Client.ClientSender;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Client.ClientSender.ClientSender;
import it.polimi.ingsw.Network.Message.Message;

public class SendtoController implements ClientSender {
    GameController gameController;

    public SendtoController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void sendMessage(Message message) {
        gameController.onRecivedMessage(message);
    }
}
