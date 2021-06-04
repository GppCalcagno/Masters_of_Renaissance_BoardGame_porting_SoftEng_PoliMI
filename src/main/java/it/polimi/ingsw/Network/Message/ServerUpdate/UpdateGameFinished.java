package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateGameFinished extends Update {

    public UpdateGameFinished() {
        super("info");
    }


    @Override
    public void update(ViewInterface view) {
        view.showMessage("the game will end on the next turn");
    }
}
