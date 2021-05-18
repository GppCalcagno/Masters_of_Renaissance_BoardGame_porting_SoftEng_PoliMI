package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageActiveLeaderCardProduction extends Message {
    private static final long serialVersionUID = 8021603830645282467L;

    private String ID;

    private char structure;

    private String resource;

    public MessageActiveLeaderCardProduction(String nickname, String ID, char structure, String resource) {
        super(nickname, MessageType.ACTIVELEADERCARDPRODUCTION);
        this.ID = ID;
        this.structure = structure;
        this.resource = resource;
    }

    public String getID() {
        return ID;
    }

    public char getStructure() {
        return structure;
    }

    public String getResource() {
        return resource;
    }


    @Override
    public void action(GameController gameController) {
    //todo tofix

    }
}
