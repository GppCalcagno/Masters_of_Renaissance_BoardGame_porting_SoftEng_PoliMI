package it.polimi.ingsw.Network.Client;


import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Network.Message.UpdateMesssage.MessageGeneric;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TimerTask;
import java.util.logging.Logger;

public class PingerTimerTask extends TimerTask {
    private MessageGeneric pingMessage;
    ClientSocket socket;
    ObjectOutputStream sendToServer;
    private static Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());
    private Object LockSending;

    public PingerTimerTask(ClientSocket socket) {
        this.pingMessage = new MessageGeneric("client",MessageType.PING);
        this.socket=socket;
        this.sendToServer= socket.getSendMessage();
        this.LockSending =socket.getLockSending();
    }


    @Override
    public void run() {
        synchronized (LockSending) {
            try {
                sendToServer.writeObject(pingMessage);
            } catch (IOException e) {
                LOGGER.severe("ERROR: CAN'T PING SERVER");
                socket.notifyAllObserver(new MessageGeneric("server", MessageType.DISCONNECT));
            }

        }
    }
}
