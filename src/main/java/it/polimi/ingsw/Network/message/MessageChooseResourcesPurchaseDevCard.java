package it.polimi.ingsw.Network.message;

import java.util.Map;

public class MessageChooseResourcesPurchaseDevCard extends Message{
    private static final long serialVersionUID = 1049595434920452908L;

    private Map<String,Integer> WarehouseRes;
    private Map<String,Integer> StrongboxRes;
    private Map<String,Integer> ExtrachestMap;

    public MessageChooseResourcesPurchaseDevCard(String nickname, Map<String, Integer> warehouseRes, Map<String, Integer> strongboxRes, Map<String, Integer> extrachestMap) {
        super(nickname, MessageType.CHOOSERESOURCESPURCHASEDEVCARD);
        WarehouseRes = warehouseRes;
        StrongboxRes = strongboxRes;
        ExtrachestMap = extrachestMap;
    }

    public Map<String, Integer> getWarehouseRes() {
        return WarehouseRes;
    }

    public Map<String, Integer> getStrongboxRes() {
        return StrongboxRes;
    }

    public Map<String, Integer> getExtrachestMap() {
        return ExtrachestMap;
    }
}
