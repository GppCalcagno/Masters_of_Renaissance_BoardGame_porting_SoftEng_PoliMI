package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class UpdateWarehouse extends Update {
    private static final long serialVersionUID = 3757146638417054874L;

    private String[][] warehouse;
    private Map<String , Integer> extraChest;
    private boolean removeMarblefromBuffer;

    public UpdateWarehouse(String nickname, String[][] warehouse, Map<String , Integer> extraChest, boolean removeMarblefromBuffer) {
        super(nickname);
        this.warehouse = warehouse;
        this.extraChest = extraChest;
        this.removeMarblefromBuffer=removeMarblefromBuffer;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setWarehouse(warehouse,extraChest);
        view.getPlayerBoard().removemarblefromBuffer(removeMarblefromBuffer);


        view.onUpdateWarehouse();

    }

}
