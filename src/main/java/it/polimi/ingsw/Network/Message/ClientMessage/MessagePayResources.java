package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessagePayResources extends Message {

    private static final long serialVersionUID = -4554480045749065772L;

    private char c;
    private String resources;
    private int numOf;

    public MessagePayResources(String nickname, char c, String resources, int numOf) {
        super(nickname, MessageType.PAYRESOURCES);
        this.c = c;
        this.resources = resources;
        this.numOf = numOf;
    }


    @Override
    public void action(GameController gameController) {

    }
}

