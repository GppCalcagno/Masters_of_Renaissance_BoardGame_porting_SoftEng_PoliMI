package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageGameFinished extends Message {

    public MessageGameFinished() {
        super("info", MessageType.FINISHEDGAME);
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        view.showMessage("the game will end on the next turn");
    }
}
