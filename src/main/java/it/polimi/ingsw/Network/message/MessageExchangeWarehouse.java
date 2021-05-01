package it.polimi.ingsw.Network.message;

public class MessageExchangeWarehouse extends Message {
    private static final long serialVersionUID = -6846180864378519974L;

    private int row1;

    private int row2;

    public MessageExchangeWarehouse(String nickname, MessageType messageType, int row1, int row2) {
        super(nickname, messageType);
        this.row1 = row1;
        this.row2 = row2;
    }

    public int getRow1() {
        return row1;
    }

    public int getRow2() {
        return row2;
    }
}
