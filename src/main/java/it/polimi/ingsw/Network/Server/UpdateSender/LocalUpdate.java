package it.polimi.ingsw.Network.Server.UpdateSender;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;

public class LocalUpdate implements SenderUpdateInterface {
   ClientController controller;

    public LocalUpdate(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void sendtoPlayer(String name, Update message) {
        controller.update(message);
    }

    @Override
    public void sendBroadcastMessage(Update message ) {
        controller.update(message);
    }
}
