package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageConnect extends Message {
    private static final long serialVersionUID = -1278945139114596789L;

    private String serverAddress;
    private int serverPort;

    public MessageConnect(String serverAddress, int serverPort) {
        super("undefined", MessageType.CONNECT);
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;
    }

    //dovrebbe essere un messaggio filtrato dal server
}
