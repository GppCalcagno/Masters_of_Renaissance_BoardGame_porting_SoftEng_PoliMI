package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageActiveProductionDevCard extends Message{
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
    public void action(GameController gameController) {

    }
}
