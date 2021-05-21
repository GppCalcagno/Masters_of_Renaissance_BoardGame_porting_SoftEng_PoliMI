package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdateSinglePlayerGame extends Message {
    private static final long serialVersionUID = -2807318014979699841L;


    private int blackCrossToken;
    private String [][][] devCardDeck;

    /**
     * This attribute indicates the extracted token's ID
     */
    private String ID;

    private String tokenColor;

    public MessageUpdateSinglePlayerGame(int blackCrossToken, String id, String[][][] devCardDeck, String tokenColor) {
        super("Lollo", MessageType.UPDATESINGLEPLAYER);
        this.blackCrossToken = blackCrossToken;
        this.ID = id;
        this.devCardDeck=devCardDeck;
        this.tokenColor = tokenColor;
    }

    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.singlePlayerUpdate(devCardDeck,blackCrossToken,ID, tokenColor);
        view.onUpdateSinglePlayerGame();
    }
}
