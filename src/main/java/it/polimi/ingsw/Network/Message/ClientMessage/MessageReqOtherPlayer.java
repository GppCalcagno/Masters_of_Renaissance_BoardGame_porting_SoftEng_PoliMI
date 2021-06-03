package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageReqOtherPlayer extends Message {
    public String otherPlayer;

    public MessageReqOtherPlayer(String nickname, String otherPlayer) {
        super(nickname, MessageType.UPDATEPLAYERSTATE);
        this.otherPlayer = otherPlayer;
    }


    public void action(GameController gameController) {
        gameController.onReqOtherPlayer(otherPlayer);
    }
}