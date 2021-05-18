package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

public class MessageEndTurn extends Message{
    private static final long serialVersionUID = 387025866355619924L;

    public MessageEndTurn(String nickname) {
        super(nickname, MessageType.ENDTURN);
    }

    @Override
    public void action(GameController gameController) {

    }
}
