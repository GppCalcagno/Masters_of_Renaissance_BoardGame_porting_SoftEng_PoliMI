package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateSinglePlayerGame extends Message {
    private static final long serialVersionUID = -2807318014979699841L;

    private String[] tokensHeap;

    private int blackCrossToken;

    /**
     * This attribute indicates the extracted token's ID
     */
    private String ID;

    public MessageUpdateSinglePlayerGame(String[] tokensHeap ,int blackCrossToken, String id) {
        super("Lollo", MessageType.UPDATESINGLEPLAYER);
        this.blackCrossToken = blackCrossToken;
        this.ID = id;
        this.tokensHeap=tokensHeap;
    }



    public int getBlackCrossToken() {
        return blackCrossToken;
    }

    public String getID() {
        return ID;
    }


    @Override
    public void update(PlayerBoard playerBoard) {
        super.update(playerBoard);
    }
}
