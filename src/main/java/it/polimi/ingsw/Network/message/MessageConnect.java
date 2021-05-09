package it.polimi.ingsw.Network.message;

public class MessageConnect extends Message{
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
}
