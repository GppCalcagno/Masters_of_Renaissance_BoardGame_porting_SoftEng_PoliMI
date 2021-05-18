package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

import java.util.Map;

public class MessageActiveBaseProduction extends Message{
    private static final long serialVersionUID = 7072915502239427238L;

    private Map<String,Integer> WarehouseRes;
    private Map<String,Integer> StrongboxRes;
    private Map<String,Integer> ExtrachestMap;

    public MessageActiveBaseProduction(String nickname, Map<String, Integer> warehouseRes, Map<String, Integer> strongboxRes, Map<String, Integer> extrachestMap) {
        super(nickname, MessageType.ACTIVESBASEPRODUCTION);
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

    @Override
    public void action(GameController gameController) {

    }
}
