package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateDevCardDeck extends Message {

    private String cardID;

    public MessageUpdateDevCardDeck(String nickname,String cardID) {
        super(nickname, MessageType.UPDATEDEVCARDDECK);
        this.cardID=cardID;
    }

    public String getCardID() {
        return cardID;
    }


    @Override
    public void update(PlayerBoard playerBoard) {
        super.update(playerBoard);
    }
}
