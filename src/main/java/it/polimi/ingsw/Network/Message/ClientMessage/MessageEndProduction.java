package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageEndProduction extends Message {
    private static final long serialVersionUID = -2193109517171387273L;

    public MessageEndProduction(String nickname) {
        super(nickname);
    }

    @Override
    public void action(GameController gameController) {
        gameController.endProduction();
    }
}
