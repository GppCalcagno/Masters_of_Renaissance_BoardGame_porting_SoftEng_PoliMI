package it.polimi.ingsw.Network.message;

public class MessageUpdateSinglePlayerGame extends Message{
    private static final long serialVersionUID = -2807318014979699841L;

    private String[][][] devCardDeck;

    private int blackCrossToken;

    private String ID;

    public MessageUpdateSinglePlayerGame(String nickname, String[][][] devCardDeck, int blackCrossToken, String id) {
        super(nickname, MessageType.UPDATEDEVCARDDECK);
        this.devCardDeck = devCardDeck;
        this.blackCrossToken = blackCrossToken;
        ID = id;
    }

    public String[][][] getDevCardDeck() {
        return devCardDeck;
    }

    public int getBlackCrossToken() {
        return blackCrossToken;
    }

    public String getID() {
        return ID;
    }
}
