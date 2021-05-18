package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageEndProduction extends Message{

    private static final long serialVersionUID = -2193109517171387273L;

    public MessageEndProduction(String nickname) {
        super(nickname, MessageType.ENDPRODUCTION);
    }

    @Override
    public void action(GameController gameController) {

    }
}
