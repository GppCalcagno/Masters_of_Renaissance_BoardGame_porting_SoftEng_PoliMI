package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageActiveProductionDevCard extends Message {
    private static final long serialVersionUID = -4418737464195662873L;

    private int column;

    public MessageActiveProductionDevCard(String nickname, int column) {
        super(nickname);
        this.column = column;
    }

    @Override
    public void action(GameController gameController){
        gameController.activeDevCardProduction(column);
    }
}
