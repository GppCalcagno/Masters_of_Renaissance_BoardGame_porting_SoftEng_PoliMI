package it.polimi.ingsw.Network.Server.UpdateSender;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.Server.UpdateSender.SenderUpdateInterface;

public class ServerUpdate implements SenderUpdateInterface {
    Server server;

    public ServerUpdate(Server server) {
        this.server = server;
    }

    @Override
    public void sendtoPlayer(String name, Update message) {
        server.sendtoPlayer(name,message);
    }

    @Override
    public void sendBroadcastMessage(Update message) {
        server.sendBroadcastMessage(message);
    }
}
