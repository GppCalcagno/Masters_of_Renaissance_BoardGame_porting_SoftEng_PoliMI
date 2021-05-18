package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageActiveProductionDevCard extends Message {
    private static final long serialVersionUID = -4418737464195662873L;

    private int column;

    public MessageActiveProductionDevCard(String nickname, int column) {
        super(nickname, MessageType.ACTIVEPRODUCTIONDEVCARD);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public void action(GameController gameController){
        gameController.activeDevCardProduction(column);
    }
}
