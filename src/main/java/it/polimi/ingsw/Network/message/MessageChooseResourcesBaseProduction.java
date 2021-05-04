package it.polimi.ingsw.Network.message;

import java.util.Map;

public class MessageChooseResourcesBaseProduction extends Message{
    private static final long serialVersionUID = 7072915502239427238L;

    private Map<String,Integer> WarehouseRes;
    private Map<String,Integer> StrongboxRes;
    private Map<String,Integer> ExtrachestMap;

    public MessageChooseResourcesBaseProduction(String nickname, MessageType messageType, Map<String, Integer> warehouseRes, Map<String, Integer> strongboxRes, Map<String, Integer> extrachestMap) {
        super(nickname, messageType);
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
