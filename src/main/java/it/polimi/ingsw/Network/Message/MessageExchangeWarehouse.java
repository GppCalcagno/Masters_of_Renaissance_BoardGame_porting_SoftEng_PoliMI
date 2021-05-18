package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageExchangeWarehouse extends Message {
    private static final long serialVersionUID = -6846180864378519974L;

    private int row1;

    private int row2;

    public MessageExchangeWarehouse(String nickname, int row1, int row2) {
        super(nickname, MessageType.EXCHANGEWAREHOUSE);
        this.row1 = row1;
        this.row2 = row2;
    }

    public int getRow1() {
        return row1;
    }

    public int getRow2() {
        return row2;
    }

    @Override
    public void action(GameController gameController) {

    }
}
