package it.polimi.ingsw.Network.Client.ClientSender;

import it.polimi.ingsw.Network.Message.Message;

public interface ClientSender {

    void sendMessage(Message message);
}
