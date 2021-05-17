package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

public class MessageUpdateSinglePlayerGame extends Message {
    private static final long serialVersionUID = -2807318014979699841L;

    private String[][][] devCardDeck;

    private int blackCrossToken;

    /**
     * This attribute indicates the extracted token's ID
     */
    private String ID;

    public MessageUpdateSinglePlayerGame(String nickname, String[][][] devCardDeck, int blackCrossToken, String id) {
        super(nickname, MessageType.UPDATESINGLEPLAYER);
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
