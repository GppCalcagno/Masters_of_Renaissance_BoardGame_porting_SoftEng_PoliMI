package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageActiveLeaderCardProduction extends Message {
    private static final long serialVersionUID = 8021603830645282467L;

    private String ID;

    private char structure;

    private String resource;

    public MessageActiveLeaderCardProduction(String nickname, String ID, char structure, String resource) {
        super(nickname);
        this.ID = ID;
        this.structure = structure;
        this.resource = resource;
    }

    @Override
    public void action(GameController gameController) {

        gameController.activeLeaderCardProduction(ID,structure,resource);
    }
}
