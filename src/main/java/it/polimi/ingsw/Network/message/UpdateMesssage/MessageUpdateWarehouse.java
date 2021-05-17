package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

import java.util.Map;

public class MessageUpdateWarehouse extends Message {
    private static final long serialVersionUID = 3757146638417054874L;

    private String[][] warehouse;
    private Map<String , Integer> extraChest;

    public MessageUpdateWarehouse(String nickname, String[][] warehouse, Map<String , Integer> extraChest) {
        super(nickname, MessageType.UPDATEWAREHOUSE);
        this.warehouse = warehouse;
        this.extraChest = extraChest;
    }

    public String[][] getWarehouse() {
        return warehouse;
    }

    public Map<String, Integer> getExtraChest() {
        return extraChest;
    }
}
