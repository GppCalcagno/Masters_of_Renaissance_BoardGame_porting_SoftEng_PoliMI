package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

public class MessageConnect extends Message{
    private static final long serialVersionUID = -1278945139114596789L;

    private String serverAddress;
    private int serverPort;

    public MessageConnect(String serverAddress, int serverPort) {
        super("undefined", MessageType.CONNECT);
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    @Override
    public void action(GameController gameController) {

    }
}
