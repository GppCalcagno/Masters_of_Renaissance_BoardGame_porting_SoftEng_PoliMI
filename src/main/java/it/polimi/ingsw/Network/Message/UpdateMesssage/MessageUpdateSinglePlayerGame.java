package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateSinglePlayerGame extends Message {
    private static final long serialVersionUID = -2807318014979699841L;


    private int blackCrossToken;
    private String [][][] devCardDeck;

    /**
     * This attribute indicates the extracted token's ID
     */
    private String ID;

    public MessageUpdateSinglePlayerGame(int blackCrossToken, String id, String [][][] devCardDeck) {
        super("Lollo", MessageType.UPDATESINGLEPLAYER);
        this.blackCrossToken = blackCrossToken;
        this.ID = id;
        this.devCardDeck=devCardDeck;
    }

    @Override
    public void update(PlayerBoard playerBoard) {
        playerBoard.singlePlayerUpdate(devCardDeck,blackCrossToken,ID);
    }
}
