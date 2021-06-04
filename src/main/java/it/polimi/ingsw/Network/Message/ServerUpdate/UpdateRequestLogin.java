package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateRequestLogin extends Update {
    public UpdateRequestLogin() {
        super("server");
    }

    @Override
    public  void update(ViewInterface view){
        view.askLogin();
    }
}
