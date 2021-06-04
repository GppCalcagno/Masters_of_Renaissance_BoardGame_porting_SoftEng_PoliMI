package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageExchangeWarehouse extends Message {
    private static final long serialVersionUID = -6846180864378519974L;

    private int row1;

    private int row2;

    public MessageExchangeWarehouse(String nickname, int row1, int row2) {
        super(nickname);
        this.row1 = row1;
        this.row2 = row2;
    }

    @Override
    public void action(GameController gameController) {
        gameController.exchangeWarehouse(row1,row2);
    }
}
