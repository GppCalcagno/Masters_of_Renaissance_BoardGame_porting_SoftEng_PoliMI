package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageGeneric extends Message{
    private static final long serialVersionUID = 4564132178552808674L;

    public MessageGeneric(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }

    @Override
    public void action(GameController gameController) {

    }
}
