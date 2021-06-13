package it.polimi.ingsw.Network.Message.ServerUpdate;


import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateWaitingForOtherPlayer extends Update {
    private static final long serialVersionUID = 1022749417453669552L;

    public UpdateWaitingForOtherPlayer() {
        super("server");
    }

    @Override
    public void update(ViewInterface view) {
        view.waitforOtherPlayers();
    }


}
