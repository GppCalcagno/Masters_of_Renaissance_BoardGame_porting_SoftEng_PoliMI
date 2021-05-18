package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.model.exceptions.EndGameException;

public class MessageEndTurn extends Message {
    private static final long serialVersionUID = 387025866355619924L;

    public MessageEndTurn(String nickname) {
        super(nickname, MessageType.ENDTURN);
    }

    @Override
    public void action(GameController gameController){
        gameController.endTurn();
    }
}
