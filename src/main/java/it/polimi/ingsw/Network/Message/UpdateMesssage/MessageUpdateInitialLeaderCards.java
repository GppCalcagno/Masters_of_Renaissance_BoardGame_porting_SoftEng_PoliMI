package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

import java.util.List;

public class MessageUpdateInitialLeaderCards extends Message {
    private static final long serialVersionUID = -8636343470594316197L;

    private List<String> leaderCardsID;

    public MessageUpdateInitialLeaderCards(String nickname, List<String> leaderCardsID) {
        super(nickname, MessageType.UPDATEINITIALLEADERCARDS);
        this.leaderCardsID = leaderCardsID;
    }

    public List<String> getLeaderCardsID() {
        return leaderCardsID;
    }
}
