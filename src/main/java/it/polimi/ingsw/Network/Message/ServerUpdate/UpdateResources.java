package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class UpdateResources extends Update {
    private static final long serialVersionUID = 8191601085287964506L;

    private String[][] warehouse;
    private Map<String, Integer> extraChest;
    private Map<String,Integer> strongbox;

    public UpdateResources(String nickname, String[][] warehouse, Map<String, Integer> extraChest, Map<String, Integer> strongbox) {
        super(nickname);
        this.warehouse = warehouse;
        this.extraChest = extraChest;
        this.strongbox = strongbox;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().updateresoruces(warehouse,extraChest,strongbox);
        view.onUpdateUpdateResources();
    }
}
