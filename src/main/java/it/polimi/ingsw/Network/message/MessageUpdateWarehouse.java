package it.polimi.ingsw.Network.message;

public class MessageUpdateWarehouse extends Message{
    private static final long serialVersionUID = 3757146638417054874L;

    private String[][] warehouse;

    public MessageUpdateWarehouse(String nickname, String[][] warehouse) {
        super(nickname, MessageType.UPDATEWAREHOUSE);
        this.warehouse = warehouse;
    }

    public String[][] getWarehouse() {
        return warehouse;
    }
}
