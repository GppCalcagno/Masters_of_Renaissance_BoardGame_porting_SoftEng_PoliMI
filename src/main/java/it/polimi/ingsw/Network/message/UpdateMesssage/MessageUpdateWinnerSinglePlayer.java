package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

public class MessageUpdateWinnerSinglePlayer extends Message {
    private static final long serialVersionUID = 229329624772076140L;

    private String winner;

    public MessageUpdateWinnerSinglePlayer(String nickname, String winner) {
        super(nickname, MessageType.UPDATEWINNERSINGLEPLAYER);
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
