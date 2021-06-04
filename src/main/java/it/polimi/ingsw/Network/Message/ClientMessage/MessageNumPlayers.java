package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageNumPlayers extends Message {
    private static final long serialVersionUID = -81224732564833427L;

    private int numPlayers;

    public MessageNumPlayers(String nickname, int numPlayers) {
        super(nickname);
        this.numPlayers = numPlayers;
    }

    @Override
    public void action(GameController gameController) {
        gameController.onNumPlayer(getNickname(),numPlayers);
    }
}
