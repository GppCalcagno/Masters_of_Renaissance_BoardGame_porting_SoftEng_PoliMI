package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateDevCardDeck extends Message {
    private static final long serialVersionUID = -1087115289891404959L;

    private String cardID;

    public MessageUpdateDevCardDeck(String nickname,String cardID) {
        super(nickname, MessageType.UPDATEDEVCARDDECK);
        this.cardID=cardID;
    }

    @Override
    public void update(PlayerBoard playerBoard) {
        playerBoard.removeCardfromDevCardDeck(cardID);
    }
}
