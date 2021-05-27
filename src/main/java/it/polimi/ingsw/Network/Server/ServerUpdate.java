package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.Message;

public class ServerUpdate implements SenderUpdateInterface {
    Server server;

    public ServerUpdate(Server server) {
        this.server = server;
    }

    @Override
    public void sendtoPlayer(String name, Message message) {
        server.sendtoPlayer(name,message);
    }

    @Override
    public void sendBroadcastMessage(Message message) {
        server.sendBroadcastMessage(message);
    }
}
