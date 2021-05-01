package it.polimi.ingsw.Network.message;

public class MessageSelectDevCard extends Message{
    private static final long serialVersionUID = -634200199040721658L;

    private int rowDevCardDeck;
    private int columnDevCardDeck;

    public MessageSelectDevCard(String nickname, MessageType messageType, int rowDevCardDeck, int columnDevCardDeck){
        super(nickname, messageType);
        this.rowDevCardDeck = rowDevCardDeck;
        this.columnDevCardDeck = columnDevCardDeck;
    }

    public int getRowDevCardDeck() {
        return rowDevCardDeck;
    }

    public int getColumnDevCardDeck() {
        return columnDevCardDeck;
    }
}
