package it.polimi.ingsw.Network.message;

public class MessageSelectDevCard extends Message{
    private static final long serialVersionUID = -634200199040721658L;

    private String cardID;

    public MessageSelectDevCard(String nickname, String cardID){
        super(nickname, MessageType.SELECTDEVCARD);
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }
}
