package it.polimi.ingsw.Network.message;

public class MessageDeleteDevCard extends Message{
    private static final long serialVersionUID = -4194502437311999138L;

    private String devCardID;

    public MessageDeleteDevCard(String nickname, String devCardID) {
        super(nickname, MessageType.DELETEDEVCARD);
        this.devCardID = devCardID;
    }

    public String getDevCardID() {
        return devCardID;
    }
}
