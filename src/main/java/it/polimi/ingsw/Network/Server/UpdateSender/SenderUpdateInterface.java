package it.polimi.ingsw.Network.Server.UpdateSender;

import it.polimi.ingsw.Network.Message.Message;

public interface SenderUpdateInterface {

    void sendtoPlayer(String name, Message message);

    void sendBroadcastMessage(Message message);

}
