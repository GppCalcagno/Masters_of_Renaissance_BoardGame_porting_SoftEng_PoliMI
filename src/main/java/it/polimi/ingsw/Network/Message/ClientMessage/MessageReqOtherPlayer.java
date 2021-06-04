package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageReqOtherPlayer extends Message {
    public String otherPlayer;

    public MessageReqOtherPlayer(String nickname, String otherPlayer) {
        super(nickname);
        this.otherPlayer = otherPlayer;
    }


    public void action(GameController gameController) {
        gameController.onReqOtherPlayer(otherPlayer);
    }
}