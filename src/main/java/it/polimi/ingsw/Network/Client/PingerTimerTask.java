package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.message.MessagePing;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TimerTask;
import java.util.logging.Logger;

public class PingerTimerTask extends TimerTask {
    private MessagePing pingMessage;
    ObjectOutputStream sendToServer;
    private static Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());
    private Object LockSending;

    public PingerTimerTask(ObjectOutputStream sendToServer, Object LockSending) {
        this.pingMessage = new MessagePing();
        this.sendToServer = sendToServer;
        this.LockSending = LockSending;
    }


    @Override
    public void run() {
        synchronized (LockSending) {
            try {
                sendToServer.writeObject(pingMessage);
            } catch (IOException e) {
                LOGGER.severe("ERROR: CAN'T PING SERVER");
                System.exit(-1);
            }

        }
    }
}
