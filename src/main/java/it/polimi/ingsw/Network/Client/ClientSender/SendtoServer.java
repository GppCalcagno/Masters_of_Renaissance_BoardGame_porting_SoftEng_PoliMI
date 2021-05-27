package it.polimi.ingsw.Network.Client.ClientSender;

import it.polimi.ingsw.Network.Client.ClientSender.ClientSender;
import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.Message.Message;

public class SendtoServer implements ClientSender {
    ClientSocket clientSocket;

    public SendtoServer(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void sendMessage(Message message) {
        clientSocket.sendMessage(message);
    }
}
