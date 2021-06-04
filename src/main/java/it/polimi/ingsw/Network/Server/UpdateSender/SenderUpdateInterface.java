package it.polimi.ingsw.Network.Server.UpdateSender;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;

public interface SenderUpdateInterface {

    void sendtoPlayer(String name, Update message);

    void sendBroadcastMessage(Update message);

}
