package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateRequestNumPlayers extends Update {
    private static final long serialVersionUID = 5203402987810310308L;

    public UpdateRequestNumPlayers() {
        super("server");
    }

    @Override
    public void update(ViewInterface view) {
        view.askNumPlayer();
    }

}
