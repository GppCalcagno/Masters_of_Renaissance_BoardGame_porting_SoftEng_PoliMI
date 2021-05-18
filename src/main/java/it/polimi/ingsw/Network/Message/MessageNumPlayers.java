package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageNumPlayers extends Message {
    private static final long serialVersionUID = -81224732564833427L;

    private int numPlayers;

    public MessageNumPlayers(String nickname, int numPlayers) {
        super(nickname, MessageType.NUMPLAYERS);
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public void action(GameController gameController) {

    }
}
