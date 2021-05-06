package it.polimi.ingsw.Network.message;

import java.util.Map;

public class MessageChooseResourcesDevCardProduction extends Message {
    private static final long serialVersionUID = -5189394367313862363L;

    private Map<String,Integer> WarehouseRes;
    private Map<String,Integer> StrongboxRes;
    private Map<String,Integer> ExtrachestMap;

    public MessageChooseResourcesDevCardProduction(String nickname, Map<String, Integer> warehouseRes, Map<String, Integer> strongboxRes, Map<String, Integer> extrachestMap) {
        super(nickname, MessageType.ACTIVEPRODUCTIONDEVCARD);
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
