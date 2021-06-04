package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class UpdateStrongbox extends Update {
    private static final long serialVersionUID = 1138583085556221628L;

    private Map<String,Integer> strongbox;

    public UpdateStrongbox(String nickname, Map<String, Integer> strongbox) {
        super(nickname);
        this.strongbox = strongbox;
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setStrongbox(strongbox);
        view.onUpdateStrongbox();
    }
}
