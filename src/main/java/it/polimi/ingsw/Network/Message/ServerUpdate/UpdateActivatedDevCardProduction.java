package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateActivatedDevCardProduction extends Update {
    private static final long serialVersionUID = 5954266390601357447L;

    private String ID;

    public UpdateActivatedDevCardProduction(String nickname, String ID) {
        super(nickname);
        this.ID = ID;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setActivedDevCardProd(ID);
        view.onUpdateActivatedDevCardProduction(ID);
    }
}
